package br.com.hillan.ioasysempresas.utils

sealed class ResultCall<T>(
    var data: T? = null,
    var error: String = ""
) {
    class Success<T>(data: T) : ResultCall<T>(data)
    class Error<T>(data: T? = null, error: String = "") : ResultCall<T>(data, error)
    class Loading<T> : ResultCall<T>()
}