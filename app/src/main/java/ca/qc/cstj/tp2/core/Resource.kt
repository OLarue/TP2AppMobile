package ca.qc.cstj.tp2.core

sealed class Resource<out T>(val data: T?, val message: String?) {
    class Success<out T>(data: T?, message: String? = null) : Resource<T>(data, message) {
        constructor() : this(null, null)
    }
    class Error(val throwable: Throwable, message: String? = null) : Resource<Nothing>(null, message)
}

sealed class LoadingResource<out T>(val data: T?, val message: String?) {
    class Success<out T>(data: T?, message: String? = null) : LoadingResource<T>(data, message) {
        constructor() : this(null, null)
    }
    class Error(val throwable: Throwable, message: String? = null) : LoadingResource<Nothing>(null, message)
    class Loading(message: String? = null) : LoadingResource<Nothing>(null, message)
}


