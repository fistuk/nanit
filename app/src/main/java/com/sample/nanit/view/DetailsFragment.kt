package com.sample.nanit.view

import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.sample.nanit.NanitApplication
import com.sample.nanit.R
import com.sample.nanit.databinding.DetailsFragmentBinding
import com.sample.nanit.model.PreviewType
import javax.inject.Inject

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailsViewModel> {
        viewModelFactory
    }

    private val photoPickerViewModel by activityViewModels<PhotoPickerViewModel> {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NanitApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoPickerViewModel.babyPhoto.observe(viewLifecycleOwner, { photo ->
            binding.profileImage.setProfileImage(photo)
        })

        viewModel.babyName.observe(viewLifecycleOwner, { name ->
            binding.nameEditText.setText(name)
        })

        viewModel.babyBirthday.observe(viewLifecycleOwner, { date ->
            binding.dateEditText.setText(date)
        })

        viewModel.enableBirthdayButton.observe(viewLifecycleOwner, { enabled ->
            binding.birthdayButton.isEnabled = enabled
        })

        binding.nameEditText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                //Clear focus here from edittext
                viewModel.setBabyName(textView.text.toString())
                textView.clearFocus()
            }

            return@setOnEditorActionListener false
        }

        binding.profileImage.apply {
            setPreviewType(PreviewType.BLUE)
            setOnCameraClickListener(object : ProfileImageView.OnCameraClickListener {
                override fun onCameraClick() {
                    val fragment = PhotoPickerFragment()
                    fragment.show(childFragmentManager, PhotoPickerFragment.TAG)
                }
            })
        }

        binding.dateEditText.apply {
            inputType = InputType.TYPE_NULL
            keyListener = null
            setOnClickListener {
                val datePicker =
                    MaterialDatePicker.Builder.datePicker()
                        .setTitleText(R.string.select_birth_date)
                        .build().apply {
                            addOnPositiveButtonClickListener {
                                viewModel.setBabyBirthday(it)
                            }
                        }

                datePicker.show(childFragmentManager, "TAG")
            }
        }


        binding.birthdayButton.setOnClickListener {
            requireActivity().supportFragmentManager.commit {
                addToBackStack(null)
                add(R.id.contentContainer, BirthdayFragment(), null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}