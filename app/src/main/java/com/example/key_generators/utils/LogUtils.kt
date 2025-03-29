package com.example.key_generators.utils

import timber.log.Timber


object LogUtils {
    fun logError(tag: String, message: String, throwable: Throwable? = null) {
        Timber.tag(tag).e(throwable, message)
    }

    fun logDebug(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }
}