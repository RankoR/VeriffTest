package com.example.sdk.di.component

import com.example.core_ui.di.scope.FragmentScope
import com.example.sdk.di.module.FragmentModule
import com.example.sdk.presentation.camera.CameraFragment
import dagger.Subcomponent

@Subcomponent(
    modules = [
        FragmentModule::class
    ]
)
@FragmentScope
interface FragmentComponent {

    fun inject(fragment: CameraFragment)

    @Subcomponent.Builder
    interface Builder {
        fun fragmentModule(module: FragmentModule): Builder

        fun build(): FragmentComponent
    }
}
