package com.nt.intercorp_login.form.core.domain

import com.google.gson.annotations.SerializedName

data class EntryModel(
    @SerializedName("Name") val name: String,
    @SerializedName("LastName") val lastName: String,
    @SerializedName("Age") val age: Int,
    @SerializedName("Date") val date: String
)