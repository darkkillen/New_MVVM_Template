package com.darkkillen.archmvvm.utils

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> warning(msg: String, data: T?): Resource<T>? {
            return Resource(Status.WARNING, data, msg)
        }

        fun <T> network(msg: String, data: T?): Resource<T>? {
            return Resource(Status.NETWORK, data, msg)
        }

        fun <T> unauthorized(msg: String, data: T?): Resource<T>? {
            return Resource(Status.UNAUTHORIZED, data, msg)
        }

    }
}