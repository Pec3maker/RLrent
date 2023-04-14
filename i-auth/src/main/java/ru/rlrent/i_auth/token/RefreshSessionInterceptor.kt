package ru.rlrent.i_auth.token

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import ru.rlrent.i_auth.dto.RefreshTokenResponse
import ru.rlrent.i_network.error.exception.AlreadyLogoutException
import ru.rlrent.i_network.error.exception.SessionExpiredException
import ru.rlrent.i_network.generated.urls.AuthUrls.USER_REFRESH_TOKEN_PATH
import ru.rlrent.i_network.network.BaseUrl
import ru.rlrent.i_network.network.InvalidSessionListener
import ru.rlrent.i_network.network.error.HttpCodes
import ru.rlrent.i_token.TokenStorage
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.android.logger.Logger
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import java.net.HttpURLConnection
import javax.inject.Inject

private const val AUTHORIZATION_HEADER = "Authorization"
private const val ACCESS_TOKEN_TYPE = "Bearer"
private const val REFRESH_TOKEN_FIELD_NAME = "refreshToken"
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

        val previousToken = tokenStorage.accessToken
        return refreshSession(chain, previousToken)
    }

    @Synchronized
    private fun refreshSession(chain: Interceptor.Chain, previousToken: String): Response {

        /**
         * Если у нас нет токена, значит один из потоков уже получил 401 и вызвал разлогин
         * Ничего запрашивать заново не надо, запрос завершаем
         */
        if (tokenStorage.accessToken.isEmpty()) {
            throw AlreadyLogoutException()
        }
        /**
         * Если значение этой переменной не равно тому, что хранится в сторадж, значит один из потоков
         * получил успешный ответ и сохранил новый токен. Повторяем запрос.
         */
        if (previousToken != tokenStorage.accessToken) {
            return requestWithToken(chain)
        }
        return tryToRefreshSession(chain)
    }

    private fun tryToRefreshSession(chain: Interceptor.Chain): Response {
        with(requestNewTokens(chain)) {
            if (code == HttpURLConnection.HTTP_OK) {
                onNewTokensReceived(body)
                return requestWithToken(chain)
            } else {
                invalidSessionListener.onSessionInvalid()
                throw SessionExpiredException()
            }
        }
    }

    private fun onNewTokensReceived(body: ResponseBody?) {
        try {
            val loginResponse = gson.fromJson(body?.string(), RefreshTokenResponse::class.java)
                .transform()

            tokenStorage.accessToken = loginResponse.accessToken
            tokenStorage.refreshToken = loginResponse.refreshToken ?: EMPTY_STRING
        } catch (e: Throwable) {
            Logger.d(e, "onNewTokensReceived error. Body:\n ${body?.string()}")
        }
    }

    private fun requestNewTokens(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .url(baseUrl.toString() + USER_REFRESH_TOKEN_PATH)
            .post(getRefreshTokenBody())
            .build()
        return chain.proceed(request)
    }

    private fun requestWithToken(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .apply {
                val accessToken = tokenStorage.accessToken
                if (accessToken.isNotEmpty()) {
                    Logger.d("$ACCESS_TOKEN_TYPE $accessToken")
                    header(AUTHORIZATION_HEADER, "$ACCESS_TOKEN_TYPE $accessToken")
                }
            }
            .build()
        return chain.proceed(request)
    }

    private fun getRefreshTokenBody(): RequestBody {
        val refreshToken = tokenStorage.refreshToken
        val requestContent = "{ $REFRESH_TOKEN_FIELD_NAME : $refreshToken }"
        return requestContent.toRequestBody(jsonMimeType)
    }
}
