package com.calmperson.imagefinder.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calmperson.imagefinder.model.Image
import com.calmperson.imagefinder.model.PageState
import com.calmperson.imagefinder.model.networkapi.GoogleSearchApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.concurrent.thread


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: GoogleSearchApiRepository
    ) : ViewModel() {

    companion object {
        private const val MAX_PAGE_NUMBER = 10
    }

    private val _isThereInternetConnection: MutableLiveData<Boolean> = MutableLiveData(true)
    val isThereInternetConnection: MutableLiveData<Boolean>
        get() = _isThereInternetConnection

    private val _pageState: LiveData<PageState> = MutableLiveData(PageState())
    val pageState: LiveData<PageState>
        get() = _pageState

    private val _images: LiveData<MutableList<Image>> = MutableLiveData(mutableStateListOf())
    val images: LiveData<MutableList<Image>>
        get() = _images

    private val googleSearchApiSuccessCallback: (List<Image>) -> Unit = { data ->
        _images.value!!.addAll(data)
        _isThereInternetConnection.postValue(true)
    }

    private val googleSearchApiFailureCallback: (Throwable) -> Unit = { t ->
        _isThereInternetConnection.postValue(false)
    }

    fun loadPage() {
        thread {
            repository.getPage(
                _pageState.value!!,
                googleSearchApiSuccessCallback,
                googleSearchApiFailureCallback
            )
        }
    }

    fun loadNextPage() {
        if (hasNextPage()) {
            pageState.value!!.pageNumber += 1
            loadPage()
        }
    }

    private fun hasNextPage(): Boolean = pageState.value!!.pageNumber < MAX_PAGE_NUMBER

}