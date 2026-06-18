package com.example.pawvet_1.data.repository

import com.example.pawvet_1.data.remote.DogApiService

class BreedsRepository(private val dogApiService: DogApiService) {
    suspend fun getBreeds(): List<String> {
        val response = dogApiService.getBreeds()
        return response.breeds.keys.toList()
    }

    suspend fun getServiceImages(): List<String> {
        val response = dogApiService.getRandomImages()
        return response.images
    }
}
