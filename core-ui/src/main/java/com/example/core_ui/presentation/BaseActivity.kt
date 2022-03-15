package com.example.core_ui.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.example.core_ui.util.ActivityInflate

/**
 * Base activity
 *
 * Override [setupView] and [setupViewModel]
 */
abstract class BaseActivity<VB : ViewBinding>(
    private val inflate: ActivityInflate<VB>
) : FragmentActivity() {

    protected lateinit var binding: VB

    protected open val viewModel: BaseViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate
            .invoke(layoutInflater)
            .also { setContentView(it.root) }

        setupView()
        setupViewModel()
    }

    /**
     * Called after binding the view. Use for view initialization
     */
    open fun setupView() {}

    /**
     * Called when ViewModel is ready to use.
     *
     * View is also ready at this moment
     */
    open fun setupViewModel() {}
}
