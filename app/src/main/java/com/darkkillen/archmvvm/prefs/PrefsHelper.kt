package com.darkkillen.archmvvm.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PrefsHelper(context: Context) {

    private val mSharedPreferences: SharedPreferences = context.getSharedPreferences(PrefsHelper::class.java.name, Context.MODE_PRIVATE)

    var theme: String?
        get() = mSharedPreferences.getString(PREF_THEME_KEY, null)
        set(theme) {
            mSharedPreferences.edit{
                putString(PREF_THEME_KEY, theme)
                commit()
            }
        }

    var deviceId: Int
        get() = mSharedPreferences.getInt(PREF_DEVICE_ID_KEY, 0)
        set(data) {
            mSharedPreferences.edit {
                putInt(PREF_DEVICE_ID_KEY, data)
                commit()
            }
        }

    var language: String
        get() = mSharedPreferences.getString(PREF_LANGUAGE_KEY, "en")!!
        set(value) {
            mSharedPreferences.edit {
                putString(PREF_LANGUAGE_KEY, value)
                commit()
            }
        }

    var currentUserId: String
        get() = mSharedPreferences.getString(PREF_CURRENT_USER_ID_KEY, "")!!
        set(value) {
            mSharedPreferences.edit {
                putString(PREF_CURRENT_USER_ID_KEY, value)
                commit()
            }
        }

    var accessToken: String
        get() = mSharedPreferences.getString(PREF_ACCESS_TOKEN_KEY, "")!!
        set(value) {
            mSharedPreferences.edit {
                putString(PREF_ACCESS_TOKEN_KEY, value)
                commit()
            }
        }

    var loginType: String
        get() = mSharedPreferences.getString(PREF_LOGIN_TYPE_KEY, "")!!
        set(value) {
            mSharedPreferences.edit {
                putString(PREF_LOGIN_TYPE_KEY, value)
                commit()
            }
        }

    companion object {
        private const val PREF_ACCESS_TOKEN_KEY = "PREF_ACCESS_TOKEN_KEY"
        private const val PREF_CURRENT_USER_ID_KEY = "PREF_CURRENT_USER_ID_KEY"
        private const val PREF_LANGUAGE_KEY = "PREF_LANGUAGE_KEY"
        private const val PREF_THEME_KEY = "PREF_THEME_KEY"
        private const val PREF_DEVICE_ID_KEY = "PREF_DEVICE_ID_KEY"
        private const val PREF_LOGIN_TYPE_KEY = "PREF_LOGIN_TYPE_KEY"
    }

}