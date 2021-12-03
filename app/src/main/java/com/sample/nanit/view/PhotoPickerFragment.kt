package com.sample.nanit.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sample.nanit.NanitApplication
import com.sample.nanit.databinding.PictureSourcesFragmentBinding
import javax.inject.Inject

class PhotoPickerFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "PictureSourceFragment"
    }

    private var _binding: PictureSourcesFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by activityViewModels<PhotoPickerViewModel> {
        viewModelFactory
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
            if (isSaved) {
                viewModel.cameraPhotoSelected()
                dismiss()
            }
        }

    private val pickPicture =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.galleryPhotoSelected(uri)
            dismiss()
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

        _binding = PictureSourcesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.camera.setOnClickListener {
            val photoUri = viewModel.getUriForCamera()
            takePicture.launch(photoUri)
        }

        binding.gallery.setOnClickListener {
            pickPicture.launch("image/*")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}