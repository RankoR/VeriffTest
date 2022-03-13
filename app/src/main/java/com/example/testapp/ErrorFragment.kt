package com.example.testapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.core_ui.util.setOnSingleClickListener
import com.example.testapp.databinding.FragmentErrorBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorFragment : BottomSheetDialogFragment() {

    private var binding: FragmentErrorBinding? = null

    var onRetryClick: () -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentErrorBinding
            .inflate(inflater, container, false)
            .also { binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message = arguments?.getString(ARG_MESSAGE) ?: return

        binding?.messageTv?.text = message
        binding?.retryBtn?.setOnSingleClickListener {
            onRetryClick()
            dismiss()
        }
    }

    override fun onDestroyView() {
        binding = null

        super.onDestroyView()
    }

    companion object {
        const val TAG = "ErrorFragment"

        private const val ARG_MESSAGE = "message"

        fun newInstance(message: String): ErrorFragment {
            return ErrorFragment().apply {
                arguments = bundleOf(ARG_MESSAGE to message)
            }
        }
    }
}
