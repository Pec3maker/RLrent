package ru.rlrent.ui.navigation.routes

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

class SendMailRoute(
    private val mail: String
) : ActivityRoute() {

    override fun createIntent(context: Context): Intent {
        return Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$mail"))
    }
}
