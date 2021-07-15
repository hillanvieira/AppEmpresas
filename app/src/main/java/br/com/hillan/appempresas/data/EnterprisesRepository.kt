package br.com.hillan.appempresas.data

import br.com.hillan.appempresas.data.remote.EnterprisesApiService
import br.com.hillan.appempresas.model.Auth
import br.com.hillan.appempresas.model.LoginInfo
import br.com.hillan.appempresas.model.Enterprises
import br.com.hillan.appempresas.model.HeadersAccess

import retrofit2.Call
import javax.inject.Inject

class EnterprisesRepository @Inject constructor(private val enterprisesApiService: EnterprisesApiService) {

    fun loginAccess(loginInfo: LoginInfo): Call<Auth> = enterprisesApiService.getAuth(loginInfo)

    fun getEnterprises(headersAccess: HeadersAccess): Call<Enterprises> {
        return enterprisesApiService.getEnterprises(
            headersAccess.accessToken,
            headersAccess.client,
            headersAccess.uid
        )
    }

}