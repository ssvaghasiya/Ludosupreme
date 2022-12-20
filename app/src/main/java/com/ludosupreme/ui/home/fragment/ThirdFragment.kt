package com.ludosupreme.ui.home.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.WebView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.ludosupreme.R
import com.ludosupreme.databinding.ThirdFragmentBinding
import com.ludosupreme.di.component.FragmentComponent
import com.ludosupreme.ui.authentication.model.TournamentData
import com.ludosupreme.ui.base.BaseFragment
import com.ludosupreme.ui.base.adapters.OnRecycleItemClickWithPosition
import com.ludosupreme.ui.home.adapter.HomeTournamentsAdapter
import com.ludosupreme.utils.Debug
import com.ludosupreme.utils.ENY_HashGenerationUtils
import com.payu.base.models.ErrorResponse
import com.payu.base.models.PayUPaymentParams
import com.payu.base.models.PaymentMode
import com.payu.base.models.PaymentType
import com.payu.checkoutpro.PayUCheckoutPro
import com.payu.checkoutpro.models.PayUCheckoutProConfig
import com.payu.checkoutpro.utils.PayUCheckoutProConstants
import com.payu.ui.model.listeners.PayUCheckoutProListener
import com.payu.ui.model.listeners.PayUHashGenerationListener


class ThirdFragment : BaseFragment(), View.OnClickListener {

    private var _binding: ThirdFragmentBinding? = null
    private val binding: ThirdFragmentBinding
        get() = _binding!!

    lateinit var homeTournamentsAdapter: HomeTournamentsAdapter

    override fun createLayout(): Int = R.layout.third_fragment

    override fun bindViewWithViewBinding(view: View) {
        _binding = ThirdFragmentBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun inject(fragmentComponent: FragmentComponent) {
        fragmentComponent.inject(this)
    }

    override fun destroyViewBinding() {
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun bindData() {
        initView()
        setOnClickListener()
    }


    private fun initView() = with(binding) {
        homeTournamentsAdapter =
            HomeTournamentsAdapter(
                object : OnRecycleItemClickWithPosition<TournamentData> {
                    override fun onClick(t: TournamentData?, view: View, position: Int) {
                        when (view.id) {
                            R.id.constraintLayoutPrizePool -> {
                                t?.prizePool?.let {
                                    startPayment(
                                        it,
                                        "subscription",
                                        "subscription"
                                    )
                                }
                            }
                        }
                    }
                })
        recyclerViewTournaments.adapter = homeTournamentsAdapter
        (recyclerViewTournaments?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false

        homeTournamentsAdapter.items = ArrayList()
        homeTournamentsAdapter.items?.addAll(TournamentData.readJsonOfTournaments(requireActivity()))
        homeTournamentsAdapter.notifyDataSetChanged()
    }


    private fun setOnClickListener() = with(binding) {
        imageViewBack.setOnClickListener(this@ThirdFragment)
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imageViewBack -> {
                navigator.goBack()
            }
        }
    }

    /*fun payNow(amount: String?) {
        val payUPaymentParams = PayUPaymentParams.Builder()
            .setAmount("1.0")
            .setIsProduction(true)
            .setKey("3TnMpV")
            .setProductInfo("subscription")
            .setPhone("9999999999")
            .setTransactionId(System.currentTimeMillis().toString())
            .setFirstName("Jhon")
            .setEmail("jhon@yopmail.com")
            .setSurl("https://payuresponse.firebaseapp.com/success")
            .setFurl("https://payuresponse.firebaseapp.com/failure")
            .build()

        PayUCheckoutPro.open(
            requireActivity(), payUPaymentParams,
            object : PayUCheckoutProListener {


                override fun onPaymentSuccess(response: Any) {
                    response as HashMap<*, *>
                    val payUResponse = response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                    val merchantResponse = response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
                }


                override fun onPaymentFailure(response: Any) {
                    response as HashMap<*, *>
                    val payUResponse = response[PayUCheckoutProConstants.CP_PAYU_RESPONSE]
                    val merchantResponse = response[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE]
                }


                override fun onPaymentCancel(isTxnInitiated: Boolean) {
                }


                override fun onError(errorResponse: ErrorResponse) {
                    val errorMessage: String =
                        if (errorResponse?.errorMessage != null && errorResponse.errorMessage!!.isNotEmpty())
                            errorResponse.errorMessage!!
                        else
                            "some_error_occurred"
                }

                override fun setWebViewProperties(webView: WebView?, bank: Any?) {
                    //For setting webview properties, if any. Check Customized Integration section for more details on this
                }

                override fun generateHash(
                    valueMap: HashMap<String, String?>,
                    hashGenerationListener: PayUHashGenerationListener
                ) {
                    if (valueMap.containsKey(PayUCheckoutProConstants.CP_HASH_STRING)
                        && valueMap.containsKey(PayUCheckoutProConstants.CP_HASH_STRING) != null
                        && valueMap.containsKey(PayUCheckoutProConstants.CP_HASH_NAME)
                        && valueMap.containsKey(PayUCheckoutProConstants.CP_HASH_NAME) != null
                    ) {

                        val hashData = valueMap[PayUCheckoutProConstants.CP_HASH_STRING]
                        val hashName = valueMap[PayUCheckoutProConstants.CP_HASH_NAME]

                        //Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.
                        val hash: String? = ENY_HashGenerationUtils.Companion.generateHashFromSDK(
                            hashData!!,
                            prodSalt, null
                        )
                        if (!TextUtils.isEmpty(hash)) {
                            val dataMap: HashMap<String, String?> = HashMap()
                            dataMap[hashName!!] = hash!!
                            hashGenerationListener.onHashGenerated(dataMap)
                        }
                    }
                }
            })
    }*/

    fun startPayment(amount: String, main_title: String?, sub_title: String?) {
        val paymentParams = preparePayUBizParams(amount, main_title, sub_title)
        initUiSdk(paymentParams)
    }

    // Payu
    private var prodKey: String? = "8QU4aGGE"
    private var prodSalt: String? = "P19fJerQfLthw7pxOcFolVVBqqyKsDjQ"
    private val merchantSecretKey = "<Please_add_your_merchant_secret_key>"
    private val surl = "https://payuresponse.firebaseapp.com/success"
    private val furl = "https://payuresponse.firebaseapp.com/failure"
    private var emailId: String? = "miningcrypto199@gmail.com"
    private val productInfo = "Basic Plan"
    private var firstName: String? = "unknown User"
    private var phoneNu: String? = "9625684532"

    private fun preparePayUBizParams(
        amount: String,
        main_title: String?,
        sub_title: String?
    ): PayUPaymentParams {
        val hashData =
            "$prodKey|${PayUCheckoutProConstants.CP_VAS_FOR_MOBILE_SDK}|${PayUCheckoutProConstants.CP_DEFAULT}|"
        val hashData1 =
            "$prodKey|${PayUCheckoutProConstants.CP_PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK}|$emailId|"
        val vasForMobileSdkHash = ENY_HashGenerationUtils.generateHashFromSDK(
            hashData,
            prodSalt, null
        )
        val paymenRelatedDetailsHash = ENY_HashGenerationUtils.generateHashFromSDK(
            hashData1,
            prodSalt, null
        )
        val additionalParams = java.util.HashMap<String, Any?>()
        additionalParams[PayUCheckoutProConstants.CP_UDF1] = "udf1"
        additionalParams[PayUCheckoutProConstants.CP_UDF2] = "udf2"
        additionalParams[PayUCheckoutProConstants.CP_UDF3] = "udf3"
        additionalParams[PayUCheckoutProConstants.CP_UDF4] = "udf4"
        additionalParams[PayUCheckoutProConstants.CP_UDF5] = "udf5"

        //Below params should be passed only when integrating Multi-currency support
        //TODO Please pass your own Merchant Access Key below as provided by your Key Account Manager at PayU.
//        additionalParamsMap[PayUCheckoutProConstants.CP_MERCHANT_ACCESS_KEY] = merchantAccessKey

        //Below hashes are static hashes and can be calculated and passed in additional params
        additionalParams[PayUCheckoutProConstants.CP_VAS_FOR_MOBILE_SDK] = vasForMobileSdkHash
        additionalParams[PayUCheckoutProConstants.CP_PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK] =
            paymenRelatedDetailsHash
        val builder = PayUPaymentParams.Builder()
        return builder.setAmount(amount)
            .setIsProduction(true)
            .setKey(prodKey)
            .setProductInfo(main_title)
            .setPhone(phoneNu)
            .setTransactionId(System.currentTimeMillis().toString())
            .setFirstName(if (firstName == null || firstName!!.isEmpty()) "Unknown User" else firstName)
            .setEmail(if (emailId == null || emailId!!.isEmpty()) "miningcrypto199@gmail.com" else emailId)
            .setSurl(surl)
            .setFurl(furl)
            .setUserCredential(if (emailId == null || emailId!!.isEmpty()) "miningcrypto199@gmail.com" else emailId)
            .setAdditionalParams(additionalParams) //                .setPayUSIParams(siDetails)
            .build()
    }

    private fun initUiSdk(payUPaymentParams: PayUPaymentParams) {
        PayUCheckoutPro.open(
            requireActivity(),
            payUPaymentParams,
            checkoutProConfig,
            object : PayUCheckoutProListener {
                override fun onPaymentSuccess(response: Any) {
                    //Cast response object to HashMap
                    val result = response as java.util.HashMap<String, Any>
                    val payuResponse = result[PayUCheckoutProConstants.CP_PAYU_RESPONSE] as String?
                    Debug.i("TAG", "onPaymentSuccess: $payuResponse")
//                    paymentData!!.response = payuResponse
//                    paymentData!!.paymentResponse = "Success"
//                    paymentData!!.paymentGatWay = "Payu"
//                    addPaymentData(paymentData)
//                    SuccessDialog()
                    showToast("success")
                }

                override fun onPaymentFailure(response: Any) {
                    //Cast response object to HashMap
                    val result = response as java.util.HashMap<String, Any>
                    val payuResponse = result[PayUCheckoutProConstants.CP_PAYU_RESPONSE] as String?
                    val merchantResponse =
                        result[PayUCheckoutProConstants.CP_MERCHANT_RESPONSE] as String?
                    Debug.i("TAG", "onPaymentFailure: $payuResponse")
                    /*paymentData!!.response = payuResponse
                    paymentData!!.paymentResponse = "Fail"
                    paymentData!!.paymentGatWay = "Payu"
                    addPaymentData_fail(paymentData)*/
                }

                override fun onPaymentCancel(isTxnInitiated: Boolean) {
                    Debug.i("TAG", "onPaymentCancel: ")
                    //                        paymentData.setResponse(payuResponse);
                    /*paymentData!!.paymentResponse = "Cancel"
                    paymentData!!.paymentGatWay = "Payu"
                    addPaymentData_fail(paymentData)*/
                }

                override fun onError(errorResponse: ErrorResponse) {
                    val errorMessage = errorResponse.errorMessage
                    Debug.i("TAG", "startPayment: 11 $errorMessage")
                    showToast(errorMessage.toString())
                }

                override fun setWebViewProperties(webView: WebView?, o: Any?) {}
                override fun generateHash(
                    valueMap: java.util.HashMap<String, String?>,
                    hashGenerationListener: PayUHashGenerationListener
                ) {
                    val hashName = valueMap[PayUCheckoutProConstants.CP_HASH_NAME]
                    val hashData = valueMap[PayUCheckoutProConstants.CP_HASH_STRING]
                    Debug.i("TAG", "generateHash: hashName : $hashName")
                    Debug.i("TAG", "generateHash: hashData : $hashData")
                    if (!TextUtils.isEmpty(hashName) && !TextUtils.isEmpty(hashData)) {
                        //Do not generate hash from local, it needs to be calculated from server side only. Here, hashString contains hash created from your server side.
                        var hash: String? = null
                        //Below hash should be calculated only when integrating Multi-currency support. If not integrating MCP
                        // then no need to have this if check.
                        hash = if (hashName.equals(
                                PayUCheckoutProConstants.CP_LOOKUP_API_HASH,
                                ignoreCase = true
                            )
                        ) {

                            //Calculate HmacSHA1 hash using the hashData and merchant secret key
                            ENY_HashGenerationUtils.generateHashFromSDK(
                                hashData!!,
                                prodSalt,
                                merchantSecretKey
                            )
                        } else {
                            //calculate SDH-512 hash using hashData and salt
                            ENY_HashGenerationUtils.generateHashFromSDK(
                                hashData!!,
                                prodSalt, null
                            )
                        }
                        if (!TextUtils.isEmpty(hash)) {
                            val dataMap = java.util.HashMap<String, String?>()
                            dataMap[hashName!!] = hash
                            hashGenerationListener.onHashGenerated(dataMap)
                        }
                    }
                }
            }
        )
    }

    //        checkoutProConfig.offerDetails = getOfferDetailsList()
//        checkoutProConfig.showCbToolbar = !binding.switchHideCbToolBar.isChecked
    //        checkoutProConfig.cartDetails = reviewOrderAdapter ?.getOrderDetailsList()
    //        checkoutProConfig.customNoteDetails = getCustomeNoteDetails()
//        checkoutProConfig.enforcePaymentList = getEnforcePaymentList()
    private val checkoutProConfig: PayUCheckoutProConfig
        private get() {
            Debug.i("TAG", "startPayment: 33")
            val checkoutProConfig = PayUCheckoutProConfig()
            checkoutProConfig.paymentModesOrder = checkoutOrderList
            //        checkoutProConfig.offerDetails = getOfferDetailsList()
//        checkoutProConfig.showCbToolbar = !binding.switchHideCbToolBar.isChecked
            checkoutProConfig.autoSelectOtp = true
            checkoutProConfig.autoApprove = true
            checkoutProConfig.surePayCount = 0
            //        checkoutProConfig.cartDetails = reviewOrderAdapter ?.getOrderDetailsList()
            checkoutProConfig.showExitConfirmationOnPaymentScreen = true
            checkoutProConfig.showExitConfirmationOnCheckoutScreen = true
            checkoutProConfig.merchantName = emailId!!
            checkoutProConfig.merchantLogo = R.drawable.ic_game_icon
            checkoutProConfig.waitingTime = 3000
            checkoutProConfig.merchantResponseTimeout = 3000
            //        checkoutProConfig.customNoteDetails = getCustomeNoteDetails()
//        checkoutProConfig.enforcePaymentList = getEnforcePaymentList()
            return checkoutProConfig
        }
    private val checkoutOrderList: java.util.ArrayList<PaymentMode>
        private get() {
            val checkoutOrderList = java.util.ArrayList<PaymentMode>()
            checkoutOrderList.add(
                PaymentMode(
                    PaymentType.UPI,
                    PayUCheckoutProConstants.CP_GOOGLE_PAY
                )
            )
            checkoutOrderList.add(
                PaymentMode(
                    PaymentType.WALLET,
                    PayUCheckoutProConstants.CP_PHONEPE
                )
            )
            checkoutOrderList.add(
                PaymentMode(
                    PaymentType.WALLET,
                    PayUCheckoutProConstants.CP_PAYTM
                )
            )
            return checkoutOrderList
        }

}
