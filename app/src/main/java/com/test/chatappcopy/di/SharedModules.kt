package com.test.chatappcopy.di

import com.test.chatappcopy.presentation.test.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SharedModules = module {
    viewModel {
        TestViewModel()
    }
}