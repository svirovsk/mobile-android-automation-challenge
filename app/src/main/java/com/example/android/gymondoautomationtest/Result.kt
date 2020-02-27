package com.example.android.gymondoautomationtest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("id")
    @Expose
    var id: Int? = null,
    @SerializedName("license_author")
    @Expose
    var licenseAuthor: String? = null,
    @SerializedName("status")
    @Expose
    var status: String? = null,
    @SerializedName("description")
    @Expose
    var description: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("name_original")
    @Expose
    var nameOriginal: String? = null,
    @SerializedName("creation_date")
    @Expose
    var creationDate: String? = null,
    @SerializedName("uuid")
    @Expose
    var uuid: String? = null,
    @SerializedName("license")
    @Expose
    var license: Int? = null,
    @SerializedName("category")
    @Expose
    var category: Int? = null,
    @SerializedName("language")
    @Expose
    var language: Int? = null,
    @SerializedName("muscles")
    @Expose
    var muscles: List<Int>? = null,
    @SerializedName("muscles_secondary")
    @Expose
    var musclesSecondary: List<Int>? = null,
    @SerializedName("equipment")
    @Expose
    var equipment: List<Int>? = null
)