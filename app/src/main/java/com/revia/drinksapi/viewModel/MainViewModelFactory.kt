package com.revia.drinks.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revia.drinksapi.model.Repository

class MainViewModelFactory(val app: Application, val repositoy: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(app, repositoy) as T
    }
}