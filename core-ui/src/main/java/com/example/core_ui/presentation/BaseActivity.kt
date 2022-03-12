package com.example.core_ui.presentation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import com.example.core_ui.util.ActivityInflate

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

    open fun setupView() {}
    open fun setupViewModel() {}
}
