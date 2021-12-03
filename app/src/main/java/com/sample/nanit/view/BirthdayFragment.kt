package com.sample.nanit.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.sample.nanit.NanitApplication
import com.sample.nanit.R
import com.sample.nanit.databinding.BirthdayFragmentBinding
import com.sample.nanit.model.PreviewType
import javax.inject.Inject

class BirthdayFragment : Fragment() {

    private var _binding: BirthdayFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<BirthdayViewModel> {
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

        _binding = BirthdayFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoPickerViewModel.babyPhoto.observe(viewLifecycleOwner, { photo ->
            binding.profileImage.setProfileImage(photo)
        })

        viewModel.previewType.observe(viewLifecycleOwner, { previewType ->
            binding.profileImage.setPreviewType(previewType)
        })

        viewModel.backgroundColor.observe(viewLifecycleOwner, { color ->
            binding.container.setBackgroundResource(color)
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), color)
        })

        viewModel.backgroundImage.observe(viewLifecycleOwner, { image ->
            binding.backgroundImage.setImageResource(image)
        })

        viewModel.babyName.observe(viewLifecycleOwner, { name ->
            binding.ageView.setName(name)
        })

        viewModel.babyBirthday.observe(viewLifecycleOwner, { date ->
            binding.ageView.setAge(date)
        })

        binding.back.setOnClickListener {
            requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.nanit_blue)
            requireActivity().onBackPressed()
        }

        binding.profileImage.apply {
            setOnCameraClickListener(object : ProfileImageView.OnCameraClickListener {
                override fun onCameraClick() {
                    val fragment = PhotoPickerFragment()
                    fragment.show(childFragmentManager, PhotoPickerFragment.TAG)
                }
            })
        }

        binding.share.setOnClickListener {
            Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}