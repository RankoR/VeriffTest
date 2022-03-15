package com.example.core_ui.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.core_ui.util.FragmentInflate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Base fragment for all non-dialog fragments
 *
 * @param inflate [ViewBinding] inflate method
 *
 * @see FragmentInflate
 */
abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: FragmentInflate<VB>
) : Fragment() {

    protected var binding: VB? = null

    protected abstract val viewModel: BaseViewModel?

    protected open val loaderView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflate
            .invoke(inflater, container, false)
            .also { binding = it }
            .let(::requireNotNull)
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setupViewModel()
    }

    /**
     * Called after binding the view. Use for view initialization
     */
    protected open fun setupView() {}

    /**
     * Called when ViewModel is ready to use.
     *
     * Don't forget to call super [setupViewModel] to set up the error and loading flows
     *
     * View is also ready at this moment.
     */
    protected open fun setupViewModel() {
        viewModel?.apply {
            launchRepeatingOn(Lifecycle.State.STARTED) {
                launch { showErrorMessage.collect { this@BaseFragment.showErrorMessage(it) } }
                launch { showLoading.collect(::showLoading) }
            }
        }
    }

    /**
     * Execute [block] when lifecycle goes to [state]
     *
     * @param state [Lifecycle.State] to execute on
     * @param block Suspending block to execute when the lifecycle state changes to [state]
     */
    protected fun launchRepeatingOn(state: Lifecycle.State, block: suspend CoroutineScope.() -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(state, block)
        }
    }

    override fun onDestroyView() {
        binding = null

        super.onDestroyView()
    }

    /**
     * Show or hide loading view.
     *
     * Note that this method only shows or hide the indicator, not touching another views.
     */
    protected open fun showLoading(isLoading: Boolean) {
        loaderView?.isVisible = isLoading
    }

    /**
     * Show the given error message. Also hides the loading indicator
     */
    protected open fun showErrorMessage(message: String) {
        showToast(message) // TODO

        loaderView?.isVisible = false
    }

    protected open fun showToast(message: String) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_LONG).show()
        }
    }
}
