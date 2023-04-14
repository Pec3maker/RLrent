package ru.rlrent.ui.error

import android.text.TextUtils
import ru.android.rlrent.base_feature.R
import ru.rlrent.i_network.error.HttpProtocolException
import ru.rlrent.i_network.error.NetworkErrorHandler
import ru.rlrent.i_network.error.NonAuthorizedException
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.ui.navigation.activity.navigator.GlobalNavigator
import ru.surfstudio.android.dagger.scope.PerScreen
import ru.surfstudio.android.logger.Logger
import javax.inject.Inject

/**
 * Стандартный обработчик ошибок, возникающих при работе с сервером
 */
@PerScreen
open class StandardErrorHandler @Inject constructor(
    private val messageController: IconMessageController,
    private val globalNavigator: GlobalNavigator
) : NetworkErrorHandler() {

    override fun handleHttpProtocolException(e: HttpProtocolException) {
        if (e is NonAuthorizedException) {
            //TODO
            return
        }

        if (e.httpCode >= ru.rlrent.i_network.network.error.HttpCodes.CODE_500) {
            messageController.show(R.string.server_error_message)
        } else if (e.httpCode == ru.rlrent.i_network.network.error.HttpCodes.CODE_403) {
            messageController.show(R.string.forbidden_error_error_message)
        } else if (!TextUtils.isEmpty(e.httpMessage)) {
            Logger.e(e.httpMessage)
        } else if (e.httpCode == ru.rlrent.i_network.network.error.HttpCodes.CODE_404) {
            messageController.show(R.string.server_error_not_found)
        } else {
            messageController.show(R.string.default_http_error_message)
        }
    }

    override fun handleNoInternetError(e: ru.rlrent.i_network.network.error.NoInternetException) {
        messageController.show(R.string.no_internet_connection_error_message)
    }

    override fun handleConversionError(e: ru.rlrent.i_network.network.error.ConversionException) {
        messageController.show(R.string.bad_response_error_message)
    }

    override fun handleOtherError(e: Throwable) {
        messageController.show(R.string.unexpected_error_error_message)
        Logger.e(e, "Unexpected error")
    }
}