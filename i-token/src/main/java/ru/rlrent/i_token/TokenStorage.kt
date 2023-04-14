package ru.rlrent.i_token

import android.content.SharedPreferences
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.shared.pref.NO_BACKUP_SHARED_PREF
import ru.surfstudio.android.shared.pref.SettingsUtil
import javax.inject.Inject
import javax.inject.Named

private const val ACCESS_KEY_TOKEN = "ACCESS_TOKEN"
private const val REFRESH_KEY_TOKEN = "REFRESH_TOKEN"

@PerApplication
class TokenStorage @Inject constructor(
    @Named(NO_BACKUP_SHARED_PREF) private val noBackupSharedPref: SharedPreferences
) {

    var refreshToken: String
        get() = SettingsUtil.getString(noBackupSharedPref, REFRESH_KEY_TOKEN)
        set(value) = SettingsUtil.putString(noBackupSharedPref, REFRESH_KEY_TOKEN, value)

    var accessToken: String
        get() = SettingsUtil.getString(noBackupSharedPref, ACCESS_KEY_TOKEN)
        set(value) = SettingsUtil.putString(noBackupSharedPref, ACCESS_KEY_TOKEN, value)

    fun clearTokens() {
        SettingsUtil.putString(
            noBackupSharedPref,
            ACCESS_KEY_TOKEN,
            SettingsUtil.EMPTY_STRING_SETTING
        )
        SettingsUtil.putString(
            noBackupSharedPref,
            REFRESH_KEY_TOKEN,
            SettingsUtil.EMPTY_STRING_SETTING
        )
    }
}
