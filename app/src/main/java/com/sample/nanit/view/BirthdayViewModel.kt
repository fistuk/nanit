package com.sample.nanit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.nanit.R
import com.sample.nanit.model.PreviewType
import com.sample.nanit.model.UserPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject


class BirthdayViewModel @Inject constructor(private val userPreferences: UserPreferences) : ViewModel() {

    private val _babyName = MutableLiveData<String>()
    val babyName: LiveData<String>
        get() = _babyName

    private val _babyBirthday = MutableLiveData<Long>()
    val babyBirthday: LiveData<Long>
        get() = _babyBirthday

    private val _previewType = MutableLiveData<PreviewType>()
    val previewType: LiveData<PreviewType>
        get() = _previewType

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int>
        get() = _backgroundColor

    private val _backgroundImage = MutableLiveData<Int>()
    val backgroundImage: LiveData<Int>
        get() = _backgroundImage

    init {

        // Get random preview type
        val previewType = PreviewType.values().random()

        _previewType.value = previewType

        _backgroundColor.value = when(previewType) {
            PreviewType.YELLOW -> R.color.nanit_yellow_bg
            PreviewType.GREEN -> R.color.nanit_green_bg
            PreviewType.BLUE -> R.color.nanit_blue_bg
        }

        _backgroundImage.value = when(previewType) {
            PreviewType.YELLOW -> R.drawable.i_os_bg_elephant
            PreviewType.GREEN -> R.drawable.i_os_bg_fox
            PreviewType.BLUE -> R.drawable.i_os_bg_pelican_2
        }

        viewModelScope.launch {
            val storedName = userPreferences.getName()
            if (storedName != null) {
                _babyName.value = storedName.uppercase()
            }

            val storedBirthday = userPreferences.getBirthData()
            if (storedBirthday != null) {
                _babyBirthday.value = storedBirthday
            }
        }
    }
}