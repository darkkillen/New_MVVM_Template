package com.darkkillen.archmvvm.utils

data class NetworkState (val status: Status, val msg: String? = null) {

    companion object {
        public val LOADED = NetworkState(Status.SUCCESS)
        public val LOADING = NetworkState(Status.RUNNING)

        @JvmStatic
        fun error(msg: String?) = NetworkState(Status.FAILED, msg)
    }
}