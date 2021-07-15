package br.com.hillan.appempresas.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.hillan.appempresas.data.EnterprisesRepository
import br.com.hillan.appempresas.model.*
import br.com.hillan.appempresas.utils.ERROR_CONNECTION_PT_BR
import br.com.hillan.appempresas.utils.ERROR_LOGIN_PT_BR
import br.com.hillan.appempresas.utils.ResultCall
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var repository: EnterprisesRepository
    lateinit var headersAccess: HeadersAccess

    private val _enterprises = MutableLiveData<List<Enterprise>>()
    val enterprises: LiveData<List<Enterprise>> = _enterprises

    private val _tryingToLogin = MutableLiveData<ResultCall<Auth?>>()
    val tryingToLogin: LiveData<ResultCall<Auth?>> = _tryingToLogin



    fun login(email: String, password: String) {
        _tryingToLogin.value = ResultCall.Loading()
        repository.loginAccess(LoginInfo(email, password)).enqueue(
            object : Callback<Auth> {
                override fun onFailure(call: Call<Auth>, t: Throwable) {
                    _tryingToLogin.value = ResultCall.Error(error = ERROR_CONNECTION_PT_BR)
                }
                override fun onResponse(call: Call<Auth>, response: Response<Auth>) {
                    if (response.body()?.success == true) {
                        headersAccess = HeadersAccess(
                            response.headers().get("access-token")!!,
                            response.headers().get("client")!!,
                            response.headers().get("uid")!!
                        )
                        _tryingToLogin.value = ResultCall.Success(response.body())
                    }else{
                        _tryingToLogin.value = ResultCall.Error(error = ERROR_LOGIN_PT_BR)
                    }
                }
            }
        )
    }

    fun loadEnterprisesList(){
        repository.getEnterprises(headersAccess).enqueue(
            object : Callback<Enterprises> {
                override fun onFailure(call: Call<Enterprises>, t: Throwable) {
                    throw Exception(t)
                }
                override fun onResponse(call: Call<Enterprises>, response: Response<Enterprises>) {
                   _enterprises.value = response.body()?.enterprises
                }
            }
        )
    }

//    val idInput = MutableLiveData<Long>()
//    val issueById: LiveData<Issue> = Transformations.switchMap(idInput) { it ->
//        issueRepository.observeIssue(it).switchMap { it ->
//
//            val result = MutableLiveData<Issue>()
//            if (it.isSuccess) {
//                result.value = it.getOrNull()
//            } else {
//                result.value = Issue(0L, "Error", Date(), "#Error", "", "", User(""))
//            }
//            result
//        }
//    }


}