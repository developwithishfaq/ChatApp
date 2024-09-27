package com.test.chatappcopy.di

import com.test.chatappcopy.base.OnlineObserver
import com.test.chatappcopy.data.repository.AuthRepositoryImpl
import com.test.chatappcopy.data.repository.ChatsRepositoryImpl
import com.test.chatappcopy.data.repository.MessagesRepositoryImpl
import com.test.chatappcopy.data.repository.UsersRepositoryImpl
import com.test.chatappcopy.data.util.MyPrefs
import com.test.chatappcopy.domain.repository.AuthRepository
import com.test.chatappcopy.domain.repository.ChatsRepository
import com.test.chatappcopy.domain.repository.MessagesRepository
import com.test.chatappcopy.domain.repository.UsersRepository
import com.test.chatappcopy.domain.usecases.CreateChatBox
import com.test.chatappcopy.domain.usecases.GetAllChats
import com.test.chatappcopy.domain.usecases.GetAllMessages
import com.test.chatappcopy.domain.usecases.IsHavingChatsExistsUseCase
import com.test.chatappcopy.domain.usecases.IsUserLoggedIn
import com.test.chatappcopy.domain.usecases.LoginUseCase
import com.test.chatappcopy.domain.usecases.LogoutUseCase
import com.test.chatappcopy.domain.usecases.SignUpUseCase
import com.test.chatappcopy.presentation.auth.AuthViewModel
import com.test.chatappcopy.presentation.chat.ChatViewModel
import com.test.chatappcopy.presentation.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val SharedModules = module {
    viewModel {
        AuthViewModel(get(), get(), get())
    }
    viewModel {
        HomeScreenViewModel(get(), get(), get(), get())
    }
    viewModel {
        ChatViewModel(get(),get(),get(),get())
    }
    single {
        GetAllMessages(get())
    }
    single <MessagesRepository>{ MessagesRepositoryImpl() }
    single {
        GetAllChats(get())
    }
    single {
        LogoutUseCase(get(), get())
    }
    factory<UsersRepository> {
        UsersRepositoryImpl()
    }
    single { OnlineObserver() }
    factory {
        IsUserLoggedIn(get())
    }
    single { MyPrefs(get()) }
    single {
        SignUpUseCase(get())
    }
    single<ChatsRepository> { ChatsRepositoryImpl() }
    factory {
        IsHavingChatsExistsUseCase(get())
    }
    single {
        CreateChatBox(get(), get(), get())
    }
    single {
        LoginUseCase(get(), get())
    }
    single<AuthRepository> {
        AuthRepositoryImpl()
    }

}