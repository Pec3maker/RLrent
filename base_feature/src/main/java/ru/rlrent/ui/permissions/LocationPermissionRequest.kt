package ru.rlrent.ui.permissions

import android.Manifest
import ru.surfstudio.android.core.ui.permission.PermissionRequest

/**
 * Запрос на получение пермишнов для доступа к локации
 *
 * @param settingsRationalString - текст, который будет показан на диалоге, переводящем пользователя
 * в настройки. Нужен, чтобы объяснить, зачем необходимо предоставить пермишн.
 * */
class LocationPermissionRequest(settingsRationalString: String) : PermissionRequest() {

    init {
        showSettingsRational = true
        settingsRationalStr = settingsRationalString
    }

    override val permissions: Array<String>
        get() = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
}