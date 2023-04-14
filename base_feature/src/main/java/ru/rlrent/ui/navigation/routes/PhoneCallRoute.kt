package ru.rlrent.ui.navigation.routes

import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.surfstudio.android.navigation.route.activity.ActivityRoute

class PhoneCallRoute(
    private val phone: String
) : ActivityRoute() {

    override fun createIntent(context: Context): Intent {
        return Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    }
}
