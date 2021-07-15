package br.com.hillan.ioasysempresas.model

import com.google.gson.annotations.SerializedName

data class Auth(
      @SerializedName("success") val success: Boolean
)

