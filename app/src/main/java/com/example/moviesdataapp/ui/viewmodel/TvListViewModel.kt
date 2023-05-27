package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesdataapp.data.repository.TvApiRepository
import javax.inject.Inject

class TvListViewModel @Inject constructor(val repository: TvApiRepository) : ViewModel() {

    val tvTopRatedList = repository.topRatedTvData

    val tvPopularList = repository.popularTvData

    val tvAiringTodayList = repository.airingTodayTvData

}