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
import com.payu.base.models.ErrorResponse
import com.payu.base.models.PayUPaymentParams
import com.payu.checkoutpro.PayUCheckoutPro
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

                    }
                })
        recyclerViewTournaments.adapter = homeTournamentsAdapter
        homeTournamentsAdapter.items = ArrayList()
        homeTournamentsAdapter.items?.addAll(TournamentData.readJsonOfTournaments(requireActivity()))
        (recyclerViewTournaments?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false

        /*homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","5000","18000","8000","6000","4000","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","2000","3500","3500","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","1500","5500","2300","1800","1400","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","1000","3700","1500","1300","900","0",120000))

        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","200","750","350","250","150","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("4 player-3 winner","400","1500","650","500","350","0",120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","500","900","900","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","800","1400","1400","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","900","1700","1700","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","1100","2090","2090","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","2500","4800","4800","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","4000","7750","7750","0",null,null,120000))
        homeTournamentsAdapter.items?.add(TournamentsData("2 player- 1 winner","8000","15600","15600","0",null,null,120000))
*/
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

    /*fun payNow() {
        val payUPaymentParams = PayUPaymentParams.Builder()
            .setAmount("1.0")
            .setIsProduction(true)
            .setKey("3TnMpV")
            .setProductInfo("Test")
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
                        val hash: String? = ""
                        if (!TextUtils.isEmpty(hash)) {
                            val dataMap: HashMap<String, String?> = HashMap()
                            dataMap[hashName!!] = hash!!
                            hashGenerationListener.onHashGenerated(dataMap)
                        }
                    }
                }
            })
    }*/

}
