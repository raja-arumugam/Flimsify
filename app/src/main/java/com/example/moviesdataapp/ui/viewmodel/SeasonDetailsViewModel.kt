package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.data.model.SeasonDetails
import com.example.moviesdataapp.data.repository.TvApiRepository
import com.example.moviesdataapp.data.model.SeriesCast
import com.example.moviesdataapp.data.model.SeriesCrew
import kotlinx.coroutines.launch
import javax.inject.Inject

class SeasonDetailsViewModel @Inject constructor(
    val tvApiRepository: TvApiRepository
) : ViewModel() {

    lateinit var seasonDetails: LiveData<Result<SeasonDetails>>
    lateinit var seasonCastDetails: LiveData<Result<List<SeriesCast>>>
    lateinit var seasonCrewDetails: LiveData<Result<List<SeriesCrew>>>
    lateinit var seasonImages: LiveData<Result<List<BackdropImages>>>
//    lateinit var seasonImages: LiveData<Result<List<SeasonPoster>>>

    fun getData(TvID: Int, seasonID: Int) {
        viewModelScope.launch {
            tvApiRepository.deleteSeason()
            tvApiRepository.deleteSeasonCast()
            tvApiRepository.deleteSeasonCrew()
            tvApiRepository.deleteSeasonImages()

            seasonDetails = tvApiRepository.getSeasonDetails(TvID, seasonID)
            seasonCastDetails = tvApiRepository.getSeasonCastDetails(TvID, seasonID)
            seasonCrewDetails = tvApiRepository.getSeasonCrewDetails(TvID, seasonID)
            seasonImages = tvApiRepository.getSeasonImages(TvID, seasonID)
        }
    }

}