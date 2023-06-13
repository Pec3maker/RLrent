package ru.rlrent.i_storage.theme

import ru.surfstudio.android.filestorage.naming.NamingProcessor
import ru.surfstudio.android.filestorage.processor.FileProcessor
import ru.surfstudio.android.filestorage.storage.BaseJsonFileStorage

private const val KEY_CURRENT_THEME_TYPE = "current_theme"

/**
 * Локальное хранилище текущей темы
 *
 * @param cacheDir директория локального кеша
 * @param namingProcessor параметр, отвечающий за определение имени файла
 */
class DefaultThemeTypeStorage(
    cacheDir: String,
    namingProcessor: NamingProcessor
) : BaseJsonFileStorage<ThemeType>(
    FileProcessor(cacheDir, KEY_CURRENT_THEME_TYPE, 1),
    namingProcessor,
    ThemeType::class.java
),
    ThemeTypeStorage {

    override var currentThemeType: ThemeType?
        get() = get(KEY_CURRENT_THEME_TYPE)
        set(value) {
            value ?: return
            put(KEY_CURRENT_THEME_TYPE, value)
        }
}
