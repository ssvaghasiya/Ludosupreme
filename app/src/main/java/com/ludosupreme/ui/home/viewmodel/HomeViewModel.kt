package com.ludosupreme.ui.home.viewmodel

import com.ludosupreme.data.pojo.ApiRequestData
import com.ludosupreme.data.repository.UserRepository
import com.ludosupreme.ui.base.APILiveData
import com.ludosupreme.ui.base.BaseViewModel
import com.ludosupreme.ui.home.model.*
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val userRepository: UserRepository) :
    BaseViewModel() {

    val getNotificationLiveData = APILiveData<NotificationData>()
    val bannerListLiveData = APILiveData<List<PosterData>>()

    fun getNotification(data: ApiRequestData) {
        userRepository.getNotification(data)
            .subscribe(withLiveData(getNotificationLiveData))
    }

    fun bannerList() {
        userRepository.bannerList()
            .subscribe(withLiveData(bannerListLiveData))
    }

}