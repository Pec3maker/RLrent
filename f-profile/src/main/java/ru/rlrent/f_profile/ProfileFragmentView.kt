package ru.rlrent.f_profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.WindowInsetsCompat.Type.navigationBars
import androidx.core.view.WindowInsetsCompat.Type.statusBars
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.jakewharton.rxbinding2.view.clicks
import ru.android.rlrent.f_profile.R
import ru.android.rlrent.f_profile.databinding.FragmentProfileBinding
import ru.rlrent.f_profile.ProfileEvent.Input
import ru.rlrent.f_profile.di.ProfileScreenConfigurator
import ru.rlrent.ui.mvi.view.BaseMviFragmentView
import ru.rlrent.ui.util.addDefaultOnBackPressedCallback
import ru.rlrent.ui.util.convertToCustomDateFormat
import ru.rlrent.ui.util.paddingTo
import ru.rlrent.v_message_controller_top.IconMessageController
import ru.surfstudio.android.core.mvi.impls.event.hub.ScreenEventHub
import ru.surfstudio.android.core.ui.navigation.feature.route.feature.CrossFeatureFragment
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import javax.inject.Inject


/**
 * Вью профиля
 */
internal class ProfileFragmentView :
    BaseMviFragmentView<ProfileState, ProfileEvent>(),
    CrossFeatureFragment {
    @Inject
    override lateinit var hub: ScreenEventHub<ProfileEvent>

    @Inject
    override lateinit var sh: ProfileScreenStateHolder

    @Inject
    lateinit var ch: ProfileCommandHolder

    @Inject
    lateinit var mc: IconMessageController

    private val binding: FragmentProfileBinding by viewBinding(FragmentProfileBinding::bind)

    override fun createConfigurator() = ProfileScreenConfigurator(arguments)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun initInsets() {
        with(binding) {
            profileHeaderContainer paddingTo statusBars()
            root paddingTo navigationBars()
        }
    }

    override fun initViews() {
        addDefaultOnBackPressedCallback { Input.BackClicked.emit() }
        with(binding) {
            leftNavigationArrow.clicks().emit(Input.BackClicked)
            exitBtn.clicks().emit(Input.Logout)
        }
    }

    override fun render(state: ProfileState) {
        with(binding) {
            loadImage(state.user.imageUrl, profileAvatar)
            userName.text = state.user.login
            phoneNumber.text = state.user.phoneNumber
            email.text = state.user.email
            bill.text = state.user.bill.toString()
            tripsCount.text = state.user.tripsCount.toString()
            moneySum.text = state.user.tripsCost.toString()
            registrationDate.text = state.user.registrationDate.convertToCustomDateFormat()
        }
    }

    private fun loadImage(imageUrl: String, iv: ImageView) {
        Glide.with(requireContext())
            .load(imageUrl)
            .transform(
                CenterCrop(),
                RoundedCorners(16)
            )
            .placeholder(R.drawable.user_placeholder)
            .into(iv)
    }
}
