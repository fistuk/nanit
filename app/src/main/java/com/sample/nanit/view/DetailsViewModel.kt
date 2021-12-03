package com.sample.nanit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.nanit.extensions.Utils
import com.sample.nanit.model.UserPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailsViewModel @Inject constructor(private val userPreferences: UserPreferences) : ViewModel() {

    private val _babyName = MutableLiveData<String>()
    val babyName: LiveData<String>
        get() = _babyName

    private val _babyBirthday = MutableLiveData<String>()
    val babyBirthday: LiveData<String>
        get() = _babyBirthday

    private val _enableBirthdayButton = MutableLiveData<Boolean>()
    val enableBirthdayButton: LiveData<Boolean>
        get() = _enableBirthdayButton

    init {

        viewModelScope.launch {
            val storedName = userPreferences.getName()
            if (storedName != null) {
                _babyName.value = storedName
            }

            val storedBirthday = userPreferences.getBirthData()
            if (storedBirthday != null) {
                _babyBirthday.value = getPrettyDate(storedBirthday)
            }

            validateBirthdayButton()
        }
    }

    fun setBabyBirthday(timestamp: Long) = viewModelScope.launch {
        _babyBirthday.value = getPrettyDate(timestamp)
        userPreferences.setBirthdate(timestamp)
        validateBirthdayButton()
    }

    fun setBabyName(name: String) = viewModelScope.launch {
        userPreferences.setName(name)
        validateBirthdayButton()
    }

    private suspend fun validateBirthdayButton() {
        val storedName = userPreferences.getName()
        val storedBirthday = userPreferences.getBirthData()
        _enableBirthdayButton.value = storedBirthday != null && storedName != null
    }

    private fun getPrettyDate(timestamp: Long): String {
        return Utils.getPrettyDate(timestamp)
    }

}