package com.johnugwuadi.keyandtempo.ui.track

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.johnugwuadi.keyandtempo.data.repositories.Result
import com.johnugwuadi.keyandtempo.data.repositories.track.TrackRepository
import kotlinx.coroutines.launch

class TrackViewModel @ViewModelInject constructor(
    private val trackRepository: TrackRepository
) : ViewModel() {

    private val _analysisResponse = MutableLiveData<TrackData>()
    val analysisResponse: LiveData<TrackData> = _analysisResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun search(id: String) {
        viewModelScope.launch {
            val analysisResult = trackRepository.analyzeTrack(id)
            when (analysisResult) {
                is Result.Success -> _analysisResponse.value = TrackData(
                    convertKeyToString(analysisResult.data.key, analysisResult.data.mode),
                    analysisResult.data.tempo
                )
                is Result.Error -> _error.value = analysisResult.exception.message
            }
        }
    }

    private fun convertKeyToString(key: Int, mode: Int): String {
        val keyString = when (key) {
            0 -> "C"
            1 -> "C♯/D♭"
            2 -> "D"
            3 -> "D♯/E♭"
            4 -> "E"
            5 -> "F"
            6 -> "F♯/G♭"
            7 -> "G"
            8 -> "G♯/A♭"
            9 -> "A"
            10 -> "A/B♭"
            11 -> "B"
            else -> throw IllegalArgumentException("Key data not handled properly.")
        }
        val modeKey = when (mode) {
            0 -> "Minor"
            1 -> "Major"
            else -> throw java.lang.IllegalArgumentException("Key mode not handled properly.")
        }

        return "$keyString $modeKey"
    }

    data class TrackData(val key: String, val tempo: Double)
}