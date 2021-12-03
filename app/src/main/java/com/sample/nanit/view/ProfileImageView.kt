package com.sample.nanit.view

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver.OnPreDrawListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import coil.load
import com.sample.nanit.R
import com.sample.nanit.databinding.ProfileImageViewBinding
import com.sample.nanit.model.PreviewType
import java.io.File

class ProfileImageView(context: Context, attributes: AttributeSet) :
    ConstraintLayout(context, attributes) {

    interface OnCameraClickListener {
        fun onCameraClick()
    }

    private var binding: ProfileImageViewBinding =
        ProfileImageViewBinding.inflate(LayoutInflater.from(context), this)

    private var listener: OnCameraClickListener? = null

    init {

        val stroke = resources.getDimension(R.dimen.profile_image_default_stroke).toInt()

        // Obtain the view size for radius calculation
        viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
            override fun onPreDraw(): Boolean {

                // Set radius for proper camera button placement
                val layoutParams =
                    binding.cameraButton.layoutParams as LayoutParams
                layoutParams.circleRadius = (width - stroke) / 2
                binding.cameraButton.layoutParams = layoutParams

                viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })

        binding.cameraButton.setOnClickListener {
            listener?.onCameraClick()
        }
    }

    fun setOnCameraClickListener(listener : OnCameraClickListener) {
        this.listener = listener
    }

    fun setProfileImage(file: File) {
        binding.userImage.load(file)
    }

    fun setPreviewType(previewType: PreviewType) {
        when (previewType) {
            PreviewType.YELLOW -> {
                binding.apply {
                    userImage.setImageResource(R.drawable.default_place_holder_yellow)
                    cameraButton.setImageResource(R.drawable.camera_icon_yellow)
                    userImage.strokeColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            R.color.nanit_yellow
                        )
                    )
                }
            }
            PreviewType.GREEN -> {
                binding.apply {
                    userImage.setImageResource(R.drawable.default_place_holder_green)
                    cameraButton.setImageResource(R.drawable.camera_icon_green)
                    userImage.strokeColor =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.nanit_green))
                }
            }
            PreviewType.BLUE -> {
                binding.apply {
                    userImage.setImageResource(R.drawable.default_place_holder_blue)
                    cameraButton.setImageResource(R.drawable.camera_icon_blue)
                    userImage.strokeColor =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.nanit_blue))
                }
            }
        }
    }
}