package br.com.hillan.appempresas.data.remote

import br.com.hillan.appempresas.model.Auth
import br.com.hillan.appempresas.model.Enterprises
import br.com.hillan.appempresas.model.LoginInfo
import retrofit2.Call
import retrofit2.http.*

const val API_VERSION = "v1/"
const val SING_IN = "${API_VERSION}users/auth/sign_in"
const val ENTERPRISES = "${API_VERSION}enterprises"

interface EnterprisesApiService {

    @POST(SING_IN)
    fun getAuth(@Body loginInfo: LoginInfo): Call<Auth>

    @GET(ENTERPRISES)
    fun getEnterprises(
        @Header("access-token") accessToken: String,
        @Header("client") client: String,
        @Header("uid") uid: String,
    ): Call<Enterprises>
}

