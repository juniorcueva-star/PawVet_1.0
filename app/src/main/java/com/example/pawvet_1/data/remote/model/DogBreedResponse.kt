package com.example.pawvet_1.data.remote.model

import com.google.gson.annotations.SerializedName

data class DogBreedResponse(
    @SerializedName("message")
    val breeds: Map<String, List<String>>,
    @SerializedName("status")
    val status: String
)
