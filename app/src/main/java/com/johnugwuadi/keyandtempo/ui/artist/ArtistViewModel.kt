package com.johnugwuadi.keyandtempo.ui.artist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.models.ArtistResponse
import com.johnugwuadi.keyandtempo.data.repositories.artists.ArtistRepository
import kotlinx.coroutines.launch

class ArtistViewModel @ViewModelInject constructor(
    private val artistRepository: ArtistRepository
) : ViewModel() {
    private val _artistData = MutableLiveData<ArtistResponse>()
    val artistData: LiveData<ArtistResponse> = _artistData

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var loaded = false

    fun load(id: String) {
        if (!loaded) {
            viewModelScope.launch {
                when (val result = artistRepository.getArtist(id)) {
                    is Result.Success -> {
                        _artistData.value = result.data
                        loaded = true
                    }
                    is Result.Error -> _error.value = result.exception.message
                }
            }
        }
    }
}