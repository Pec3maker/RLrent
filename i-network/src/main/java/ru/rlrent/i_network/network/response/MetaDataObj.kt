package ru.rlrent.i_network.network.response

import com.google.gson.annotations.SerializedName
import ru.rlrent.i_network.network.Transformable
import ru.rlrent.domain.meta.MetaData

/**
 * Маппинг-модель ответа сервера для метаданных для пагинации
 */
data class MetaDataObj(
    @SerializedName("offset") val offset: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("count") val count: Int
) : Transformable<MetaData> {

    override fun transform() = MetaData(offset, limit, count)
}
