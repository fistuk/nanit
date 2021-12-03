package com.sample.nanit.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.sample.nanit.R
import com.sample.nanit.databinding.AgeViewBinding
import com.sample.nanit.extensions.Utils

class AgeView(context: Context, attributes: AttributeSet) :
    ConstraintLayout(context, attributes) {

    private var binding: AgeViewBinding =
        AgeViewBinding.inflate(LayoutInflater.from(context), this)

    fun setName(name: String) {
        binding.title.text = resources.getString(R.string.birthday_title, name)
    }

    fun setAge(timestamp: Long) {

        val months = Utils.monthsFrom(timestamp)
        val years = Utils.yearsFrom(timestamp)

        // Handle text
        val ageText = when {
            months == 1 -> resources.getString(R.string.month)
            months < 12 -> resources.getString(R.string.months)
            months in 12..23 -> resources.getString(R.string.year)
            else -> resources.getString(R.string.years)
        }
        binding.ageText.text = resources.getString(R.string.age, ageText).uppercase()

        // TODO currently a primitive logic to get a proper age drawable.
        //  Should make more generic and robust to support all ages

        // Handle age image
        val period = if (years > 0) years else months
        val ageImage = when (period % 12) {
            0 -> ContextCompat.getDrawable(context, R.drawable.zero)
            1 -> ContextCompat.getDrawable(context, R.drawable.one)
            2 -> ContextCompat.getDrawable(context, R.drawable.two)
            3 -> ContextCompat.getDrawable(context, R.drawable.three)
            4 -> ContextCompat.getDrawable(context, R.drawable.four)
            5 -> ContextCompat.getDrawable(context, R.drawable.five)
            6 -> ContextCompat.getDrawable(context, R.drawable.six)
            7 -> ContextCompat.getDrawable(context, R.drawable.seven)
            8 -> ContextCompat.getDrawable(context, R.drawable.eighth)
            9 -> ContextCompat.getDrawable(context, R.drawable.nine)
            10 -> ContextCompat.getDrawable(context, R.drawable.ten)
            11 -> ContextCompat.getDrawable(context, R.drawable.eleven)
            else -> ContextCompat.getDrawable(context, R.drawable.zero)
        }

        binding.age.setImageDrawable(ageImage)

    }
}