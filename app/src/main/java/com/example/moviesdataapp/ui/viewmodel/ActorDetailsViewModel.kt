package com.example.moviesdataapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesdataapp.data.api.Result
import com.example.moviesdataapp.data.model.ActorDetailsResponse
import com.example.moviesdataapp.data.model.ActorMovies
import com.example.moviesdataapp.data.model.BackdropImages
import com.example.moviesdataapp.data.model.ExternalIDs
import com.example.moviesdataapp.data.repository.MovieApiRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActorDetailsViewModel @Inject constructor(
    val repository: MovieApiRepository,
) : ViewModel() {

    lateinit var actorDetails: LiveData<Result<ActorDetailsResponse>>
    lateinit var actorMovies: LiveData<Result<List<ActorMovies>>>

    lateinit var imagesList: LiveData<Result<List<BackdropImages>>>
    lateinit var externalIDs: LiveData<Result<ExternalIDs>>

    fun getData(actor_id: Int) {
        viewModelScope.launch {
            repository.actorDetailsDelete()
            repository.actorMoviesDelete()
            repository.actorImagesDelete()
            repository.externalIDDelete()

            actorDetails = repository.getActorDetails(actor_id)
            actorMovies = repository.getActorMovies(actor_id)
            imagesList = repository.getActorImages(actor_id)
            externalIDs = repository.getExternalID(actor_id)
        }
    }

}