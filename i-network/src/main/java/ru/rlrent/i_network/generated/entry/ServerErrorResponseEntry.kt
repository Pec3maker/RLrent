package ru.rlrent.i_network.generated.entry

import com.google.gson.annotations.SerializedName

/**
 * Специфичный ответ сервера в случае 400 ошибки
 *
 */
data class ServerErrorResponseEntry(
    @SerializedName("code") val code: Int,
    @SerializedName("userMessage") val data: String
)
