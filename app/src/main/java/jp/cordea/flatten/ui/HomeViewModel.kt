package jp.cordea.flatten.ui

import androidx.lifecycle.ViewModel
import jp.cordea.flatten.repository.UserRepository

class HomeViewModel(
    private val repository: UserRepository
) : ViewModel() {
}
