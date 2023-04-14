package ru.rlrent.ui.dialog.simple.bottom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.surfstudio.android.core.ui.view_binding.viewBinding
import ru.surfstudio.android.template.base_feature.R
import ru.surfstudio.android.template.base_feature.databinding.ProfileBottomSheetLayoutBinding
import ru.surfstudio.practice.ui.dialog.base.BaseBottomSheetDialogView
import ru.surfstudio.practice.ui.dialog.base.BaseResult
import ru.surfstudio.practice.ui.navigation.routes.ProfileBottomSheetDialogRoute

/**
 * Класс диалога профиля
 */
class ProfileBottomSheetDialogView : BaseBottomSheetDialogView() {

    override var result: BaseResult = ProfileBottomSheetResult.DISMISS

    override val route: ProfileBottomSheetDialogRoute by lazy {
        ProfileBottomSheetDialogRoute(requireArguments())
    }

    private val binding by viewBinding(ProfileBottomSheetLayoutBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.profile_bottom_sheet_layout, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        phoneNum.text = route.phone
        callBtn.setOnClickListener { closeWithResult(ProfileBottomSheetResult.CALL) }
        mailBtn.setOnClickListener { closeWithResult(ProfileBottomSheetResult.MAIL) }
    }
}
