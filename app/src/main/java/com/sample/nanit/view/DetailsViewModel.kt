package com.sample.nanit.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {

        viewModelScope.launch {
            val storedName = userPreferences.getName()
            if (storedName != null) {
                _babyName.value = storedName
            }

            val storedBirthday = userPreferences.getBirthData()
            if (storedBirthday != null) {
                _babyBirthday.value = getPrettydate(storedBirthday)
            }
        }
    }

    fun setBabyBirthday(timestamp: Long) = viewModelScope.launch {
        _babyBirthday.value = getPrettydate(timestamp)
        userPreferences.setBirthdate(timestamp)
    }

    fun setBabyName(name: String) = viewModelScope.launch {
        userPreferences.setName(name)
    }

    private fun getPrettydate(timestamp: Long): String {
        return "date"
    }

}