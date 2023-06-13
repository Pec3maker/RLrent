package ru.rlrent.domain.meta

/**
 * Метаданные для пагинации
 *
 * @param offset смещение
 * @param limit количество отдаваемых элементов
 * @param count общее количество элементов
 */
class MetaData(
    val offset: Int = 0,
    val limit: Int = 0,
    val count: Int = 0
)