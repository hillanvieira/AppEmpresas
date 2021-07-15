package br.com.hillan.appempresas.model

data class HeadersAccess(
    val accessToken: String,
    val client: String,
    val uid: String
){
    override fun toString(): String {
        return "HeadersAccess(accessToken='$accessToken', client='$client', uid='$uid')"
    }
}

