package com.example.sdk.presentation.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.example.core_ui.util.setOnSingleClickListener
import com.example.sdk.databinding.FragmentErrorBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Fragment for displaying the error
 *
 * Use [onRetryClick] to get a callback when the «retry» button is clicked
 *
 * Current implementation uses [BottomSheetDialogFragment]
 */
class ErrorFragment : BottomSheetDialogFragment() {

    private var binding: FragmentErrorBinding? = null

    /**
     * Called when the «retry» button is clicked
     */
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

        /**
         * Create [ErrorFragment]. Use fragmentManager.show to display this fragment
         *
         * @param message Message to show
         * @return [ErrorFragment] instance
         */
        fun newInstance(message: String): ErrorFragment {
            return ErrorFragment().apply {
                arguments = bundleOf(ARG_MESSAGE to message)
            }
        }
    }
}
