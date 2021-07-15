package br.com.hillan.appempresas.model

import com.google.gson.annotations.SerializedName

data class Enterprises(
    @SerializedName("enterprises")
    val enterprises: ArrayList<Enterprise>
)

data class Enterprise(
    val photo: String,
    @SerializedName("enterprise_name")
     val enterpriseName:String,
    val description: String,
    val country: String,
    @SerializedName("enterprise_type")
     val enterpriseType: Type
)

data class Type(
    @SerializedName("enterprise_type_name")
    val enterpriseTypeName: String
)