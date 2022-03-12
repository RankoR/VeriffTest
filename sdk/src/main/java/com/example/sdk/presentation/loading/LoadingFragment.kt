package com.example.sdk.presentation.loading

import com.example.core_ui.presentation.BaseFragment
import com.example.core_ui.presentation.BaseViewModel
import com.example.sdk.databinding.FragmentLoadingBinding

/**
 * The only task of this fragment is displaying the loading state.
 * In the current state it's OK to use a single fragment for both activities.
 */
internal class LoadingFragment : BaseFragment<FragmentLoadingBinding>(FragmentLoadingBinding::inflate) {

    override val viewModel: BaseViewModel? = null

    companion object {

        fun newInstance(): LoadingFragment {
            return LoadingFragment()
        }
    }
}
