package com.johnugwuadi.keyandtempo.ui.album

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.remote.models.TrackInAlbumResponse
import com.johnugwuadi.keyandtempo.data.repositories.album.AlbumRepository
import kotlinx.coroutines.launch

class AlbumViewModel @ViewModelInject constructor(
    private val albumRepository: AlbumRepository
) : ViewModel() {
    private val _tracks = MutableLiveData<List<TrackInAlbumResponse>>()
    val tracks: LiveData<List<TrackInAlbumResponse>> = _tracks

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private var tracksLoaded = false

    fun loadTracks(id: String) {
        if (!tracksLoaded) {
            viewModelScope.launch {
                when (val result = albumRepository.getAlbumTracks(id)) {
                    is Result.Success -> {
                        _tracks.value = result.data
                        tracksLoaded = true
                    }
                    is Result.Error -> _error.value = result.exception.message
                }
            }
        }
    }
}