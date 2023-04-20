package ru.rlrent.i_auth.token

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import ru.rlrent.i_network.error.exception.AlreadyLogoutException
import ru.rlrent.i_network.error.exception.SessionExpiredException
import ru.rlrent.i_network.network.BaseUrl
import ru.rlrent.i_network.network.InvalidSessionListener
import ru.rlrent.i_network.network.error.HttpCodes
import ru.rlrent.i_token.TokenStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import javax.inject.Inject

private const val AUTHORIZATION_HEADER = "Authorization"
private const val TOKEN_TYPE = "Bearer"
private val jsonMimeType = "application/json; charset=utf-8".toMediaType()

/**
 * Интерцептор для авторизации при возникновении 401 кода в ответе.
 */
@PerApplication
class RefreshSessionInterceptor @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val baseUrl: BaseUrl,
    private val invalidSessionListener: InvalidSessionListener,
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        // Проверяем содержит ли url по которому происходит запрос базовую часть нашего сервера,
        // так как okHttpClient приложения используется для работы с вебвью, которая при загрузке
        // обращается к сторонним апи. И некоторые из запросов могут завершиться с 401 ответом,
        // что приведет к непредвиденным ошибкам и некорректной работе интерцептора

        if (!chain.request().url.toString()
                .contains(baseUrl.base)
        ) return chain.proceed(chain.request())

        val response = requestWithToken(chain)
        if (response.code != HttpCodes.CODE_401) {
            return response
        }
        response.close()

        invalidSessionListener.onSessionInvalid()
        if (tokenStorage.token.isEmpty()) {
            throw AlreadyLogoutException()
        } else {
            throw SessionExpiredException()
        }
    }

    private fun requestWithToken(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .apply {
                val token = tokenStorage.token
                if (token.isNotEmpty()) {
                    header(AUTHORIZATION_HEADER, "$TOKEN_TYPE $token")
                }
            }
            .build()
        return chain.proceed(request)
    }
}
