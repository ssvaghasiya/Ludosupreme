package com.ludosupreme.core;

import android.text.TextUtils;
import android.util.Log;


import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;


@Singleton
public class AESCryptoInterceptor implements Interceptor {

    private AES aes;

    @Inject
    public AESCryptoInterceptor(AES aes) {
        this.aes = aes;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request request = chain.request();

        Set<String> plainQueryParameters = request.url().queryParameterNames();
        HttpUrl httpUrl = request.url();


        // Check Query Parameters and encrypt
        if (plainQueryParameters != null && !plainQueryParameters.isEmpty()) {
            HttpUrl.Builder httpUrlBuilder = httpUrl.newBuilder();
            for (int i = 0; i < plainQueryParameters.size(); i++) {
                String name = httpUrl.queryParameterName(i);
                String value = httpUrl.queryParameterValue(i);
                httpUrlBuilder.setQueryParameter(name, aes.encrypt(value));
            }
            httpUrl = httpUrlBuilder.build();
        }

        // Get Header for encryption
        String apiKey = request.headers().get(Session.API_KEY);
        String token = request.headers().get(Session.USER_SESSION);
//        String language = request.headers().get(Session.LANGUAGE);


        Request newRequest;
        Request.Builder requestBuilder = request.newBuilder();

        // Check if any body and encrypt
        RequestBody requestBody = request.body();
        if (requestBody != null && requestBody.contentType() != null) {
            // bypass multipart parameters for encryption
            boolean isMultipart = !requestBody.contentType().type().equalsIgnoreCase("multipart");
            String bodyPlainText = isMultipart ? transformInputStream(bodyToString(requestBody)) : bodyToString(requestBody);

            if (bodyPlainText != null) {
                requestBuilder
                        .post(RequestBody.create(
                                MediaType.parse("text/plain"),
                                bodyPlainText));
            }
        }

        // Build the final request
        if (TextUtils.isEmpty(token)) {
            newRequest = requestBuilder.url(httpUrl)
                    .header(Session.API_KEY, aes.encrypt(apiKey))
//                    .header(Session.LANGUAGE, language)
                    .build();
        } else {
            newRequest = requestBuilder.url(httpUrl)
                    .header(Session.API_KEY, aes.encrypt(apiKey))
                    .header(Session.USER_SESSION, aes.encrypt(token))
//                    .header(Session.LANGUAGE, language)
                    .build();
        }
        // execute the request
        Response proceed = chain.proceed(newRequest);
        // get the response body and decrypt it.
        String cipherBody = proceed.body().string();

        if (cipherBody != null && cipherBody.contains("html"))
            Log.e("::::Cipher Text::::", cipherBody);

        String plainBody = aes.decrypt(cipherBody);

        // create new Response with plaint text body for further process
        return proceed.newBuilder()
                .body(ResponseBody.create(MediaType.parse("text/json"), plainBody.trim()))
                .build();

    }

    private String bodyToString(final RequestBody request) {

        try {
            final Buffer buffer = new Buffer();
            if (request != null)
                request.writeTo(buffer);
            else
                return null;
            return buffer.readUtf8();
        } catch (final IOException e) {
            return null;
        }
    }

    private String transformInputStream(String inputStream) {
        return aes.encrypt(inputStream);
    }
}