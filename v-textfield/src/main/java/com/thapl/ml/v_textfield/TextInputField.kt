package com.thapl.ml.v_textfield

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Parcelable
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import com.thapl.ml.f_textfield.R
import com.thapl.ml.v_textfield.anim.hint.HintAnimator
import com.thapl.ml.v_textfield.anim.hint.HintRaisingAnimator
import com.thapl.ml.v_textfield.anim.underline.BaseUnderlineTextAnimator
import com.thapl.ml.v_textfield.anim.underline.UnderlineTextAnimator
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributes
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributes.Companion.TEXT_INPUT_FIELD_ERROR
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributes.Companion.TEXT_INPUT_FIELD_FOCUSED
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributesReader
import com.thapl.ml.v_textfield.data.HintAnimationParams
import com.thapl.ml.v_textfield.data.TextInputFieldSavedState
import com.thapl.ml.v_textfield.data.TextInputFieldState
import com.thapl.ml.v_textfield.error.EditTextAlreadySetException
import com.thapl.ml.v_textfield.masks.MaskSupportDelegate
import com.thapl.ml.v_textfield.masks.MaskedTextField
import com.thapl.ml.v_textfield.styling.FilledBackground
import com.thapl.ml.v_textfield.utils.afterMeasured
import com.thapl.ml.v_textfield.utils.setOnDoneAction
import com.thapl.ml.v_textfield.utils.setOnNextAction
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING
import ru.surfstudio.android.utilktx.ktx.ui.view.showKeyboard

typealias OnFocusChangedListener = (isFocused: Boolean) -> Unit
typealias OnTextChangedListener = (newText: String) -> Unit
typealias RawAndMaskedTextChangedListener = (maskFilled: Boolean, extractedValue: String, formattedValue: String) -> Unit
typealias OnEndImageButtonClickListener = () -> Unit

/**
 * Текстовое поле, заменяющее собой Material Text Field
 */
open class TextInputField @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    private val defStyleAttr: Int = R.attr.textFieldStyle,
    private val defStyleRes: Int = R.style.AppTheme_TextInputField,
    private val maskSupportDelegate: MaskSupportDelegate = MaskSupportDelegate()
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes),
    MaskedTextField by maskSupportDelegate {

    lateinit var editText: EditText
        private set

    private lateinit var editTextContainer: ViewGroup
    private lateinit var underLineTv: TextView
    protected lateinit var endImageButton: ImageButton
    protected lateinit var backgroundView: View
    protected lateinit var hintTv: TextView

    private var hintAnimator: HintAnimator? = null
    private var underlineTextAnimator: UnderlineTextAnimator? = null

    private lateinit var hintAnimationParams: HintAnimationParams

    var onEndImageButtonClickListener: OnEndImageButtonClickListener? = null
    var onTextChangedListener: OnTextChangedListener? = null
    var rawAndMaskedTextChangedListener: RawAndMaskedTextChangedListener? = null
        set(value) {
            field = value
            maskSupportDelegate.setTextChangedListener(value)
        }

    private val onInputFocusChangeListeners = mutableListOf<OnFocusChangedListener>()

    // скрывать ли текст под полем (хелпер или ошибка) или сохранять под него фиксированное место
    private var useFixedSpaceForUnderlineText = false

    protected lateinit var styleAttrs: TextInputFieldAttributes

    var inputType: Int
        set(value) {
            editText.inputType = value
        }
        get() = editText.inputType

    var endImageButtonIcon: Drawable? = null
        set(value) {
            field = value
            value?.let {
                endImageButton.setImageDrawable(it)
            }
            endImageButton.isVisible = endImageButtonIcon != null
        }

    var state: TextInputFieldState = TextInputFieldState.Normal
        set(value) {
            field = value
            renderState(value)
        }

    var text: String
        get() = editText.text.toString()
        set(value) {
            // сохраняет положение курсора
            editText.text.let { it.replace(0, it.length, value) }
        }

    var hintText: String
        get() = hintTv.text.toString()
        set(value) {
            hintTv.text = value
        }

    var helperText: String = EMPTY_STRING
        set(value) {
            field = value
            if (!state.isError) {
                underLineTv.text = value
                underlineTextAnimator?.changeHelperText(value)
            }
        }

    var errorText: String = EMPTY_STRING
        set(value) {
            field = value
            if (state.isError) {
                underLineTv.text = value
            }
        }

    var isUnderlineTextVisible: Boolean = false
        set(value) {
            field = value
            underLineTv.isVisible = value
        }

    val isTextFieldHasFocus: Boolean
        get() = editText.isFocused

    //region @StringRes setters
    fun setText(@StringRes textRes: Int) {
        text = context.resources.getString(textRes)
    }

    fun setHint(@StringRes textRes: Int) {
        hintText = context.resources.getString(textRes)
    }

    fun setErrorText(@StringRes textRes: Int?) {
        errorText = textRes?.let { context.resources.getString(textRes) } ?: EMPTY_STRING
    }

    fun setHelperText(@StringRes textRes: Int) {
        helperText = context.resources.getString(textRes)
    }

    fun setOnDoneButton(onDoneBtn: (() -> Unit)? = null) {
        editText.imeOptions = EditorInfo.IME_ACTION_DONE
        editText.setOnDoneAction {
            onDoneBtn?.invoke()
        }
    }

    fun setOnNextButton(onNextBtn: (() -> Unit)? = null) {
        editText.imeOptions = EditorInfo.IME_ACTION_NEXT
        editText.setOnNextAction {
            onNextBtn?.invoke()
        }
    }
    //endregion

    init {
        obtainAttrs()
        inflateLayout(context)
        findViews()
        applyAttrs()
        initAnimators()
        initEndImageButtonListener()
    }

    //region Initialization
    private fun inflateLayout(context: Context) {
        inflate(context, R.layout.view_text_input_field_filled, this)
    }

    private fun findViews() {
        editTextContainer = findViewById(R.id.tf_input_container)
        replaceEditText(findViewById(R.id.tf_default_input_et))
        hintTv = findViewById(R.id.tf_hint_tv)
        underLineTv = findViewById(R.id.tf_text_under_line_tv)
        backgroundView = findViewById(R.id.tf_background_view)
        endImageButton = findViewById(R.id.tf_end_icon_btn)
    }

    private fun obtainAttrs() {
        context.withStyledAttributes(attrs, R.styleable.TextInputField, defStyleAttr, defStyleRes) {
            styleAttrs = TextInputFieldAttributesReader.read(context, this)
        }
    }

    private fun applyAttrs() {
        text = styleAttrs.text
        hintText = styleAttrs.hintText
        inputType = styleAttrs.inputType
        errorText = styleAttrs.errorText
        helperText = styleAttrs.helperText
        endImageButtonIcon = styleAttrs.endImageButtonIcon
        hintAnimationParams = styleAttrs.hintAnimationParams
        useFixedSpaceForUnderlineText = styleAttrs.useFixedSpaceForUnderlineText
        state = styleAttrs.state

        editText.setTextColor(styleAttrs.editTextTextColor)
        editText.setTextAppearance(styleAttrs.textAppearance)

        hintTv.setTextColor(styleAttrs.hintTextColor)
        underLineTv.setTextColor(styleAttrs.helperTextColor)
        backgroundView.background = createBackgroundDrawable(
            styleAttrs.backgroundLineColor,
            styleAttrs.boxColor,
            styleAttrs.underlineViewHorizontalPadding
        )
    }

    /**
     * в xml-ках, содержащих <selector>, нельзя работать через кастомные атрибуты TextInputField,
     * цвета из таких атрибутов не применятся, т.к. в inflate передается Context,
     * который ничего не знает о наших кастомных атрибутах
     *
     * Поэтому программно создаём фон с поддержкой кастомных состояний
     */
    private fun createBackgroundDrawable(
        backgroundLineColor: ColorStateList,
        boxColor: ColorStateList,
        underlineViewPadding: Float,
    ): Drawable {
        val background = FilledBackground(
            backgroundLineColor,
            boxColor,
            underlineViewPadding = underlineViewPadding
        )
        return background.createBackground()
    }

    private fun initAnimators() {
        setupHintAnimator()
        setupUnderlineTextAnimator()
    }

    private fun setupUnderlineTextAnimator() {
        underLineTv.post {
            underlineTextAnimator = BaseUnderlineTextAnimator(
                underLineTv,
                getUnderlineViewDefaultVisibility()
            )
        }
    }

    /**
     * Возвращает подходящий вариант visibility в зависимости от того
     * должно ли место под ошибку или подсказку фиксироваться, чтобы UI не скакал при их появлении
     */
    private fun getUnderlineViewDefaultVisibility(): Int {
        return if (useFixedSpaceForUnderlineText) {
            View.INVISIBLE
        } else {
            View.GONE
        }
    }

    private fun setupHintAnimator() {
        backgroundView.afterMeasured {
            hintAnimator = HintRaisingAnimator(
                targetView = hintTv,
                boxHeight = measuredHeight.toFloat(),
                hintAnimationParams = hintAnimationParams
            )
            hintAnimator?.setInitialState(isExpanded = editText.isFocused || text.isNotEmpty())
        }
    }

    protected open fun initEndImageButtonListener() {
        endImageButton.setOnClickListener {
            onEndImageButtonClickListener?.invoke()
        }
    }

    private fun initEditTextListeners() {
        editText.setOnFocusChangeListener { _, hasFocus ->
            onInputFocusChangeListeners.forEach { it.invoke(hasFocus) }
            maskSupportDelegate.reactOnFocusChanges(hasFocus)
            reactOnFocusChanges(hasFocus)
        }

        this.setOnClickListener {
            editText.requestFocus()
            editText.showKeyboard()
        }

        editText.addTextChangedListener(TextInputFieldTextWatcher())
    }
    //endregion

    override fun isFocused(): Boolean {
        return editText.isFocused
    }

    override fun onSaveInstanceState(): Parcelable? {
        return TextInputFieldSavedState(state, super.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is TextInputFieldSavedState) {
            super.onRestoreInstanceState(state.superSavedState)
            this.state = state.state
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        return when {
            state.isError -> {
                val customStates = TEXT_INPUT_FIELD_ERROR
                val newStatesArraySize = extraSpace + customStates.count()
                val drawableState = super.onCreateDrawableState(newStatesArraySize)
                mergeDrawableStates(drawableState, customStates)
            }
            /**
             * EditText всегда будет забирать фокус себе, и
             * view state state_focused для родителя будет false.
             * Поэтому в селекторах будем использовать свой стейт app:view_state_focused
             */
            editText.isFocused -> {
                val customStates = TEXT_INPUT_FIELD_FOCUSED
                val newStatesArraySize = extraSpace + customStates.count()
                val drawableState = super.onCreateDrawableState(newStatesArraySize)
                mergeDrawableStates(drawableState, customStates)
            }
            else -> super.onCreateDrawableState(extraSpace)
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        val editTextAlreadySet = ::editText.isInitialized && editText.id != R.id.tf_default_input_et
        when {
            child is EditText && editTextAlreadySet -> throw EditTextAlreadySetException()
            child is EditText -> replaceEditText(child)
            else -> super.addView(child, index, params)
        }
    }

    private fun replaceEditText(editText: EditText) {
        if (this::editText.isInitialized && this.editText == editText) {
            return
        }
        this.editText = editText
        editText.prepare()
        initEditTextListeners()
        editTextContainer.replaceView(editText)
    }

    private fun EditText.prepare() {
        isDuplicateParentStateEnabled = true
        isSaveEnabled = true
        maskSupportDelegate.init(this)
        inputType = this@TextInputField.inputType
        setTextColor(styleAttrs.editTextTextColor)
    }

    private fun ViewGroup.replaceView(view: View) {
        removeAllViews()
        addView(view)
    }

    fun clearText() {
        text = EMPTY_STRING
    }

    fun addInputFilter(filter: InputFilter) {
        editText.filters = arrayOf(*editText.filters, filter)
    }

    fun addFocusChangedListener(listener: OnFocusChangedListener) {
        onInputFocusChangeListeners.add(listener)
    }

    fun clearFocusChangedListeners() {
        onInputFocusChangeListeners.clear()
    }

    @CallSuper
    protected open fun reactOnFocusChanges(hasFocus: Boolean) {
        hintAnimator?.animateFocusChanges(hasFocus, editText.text?.isEmpty() == true)
        refreshDrawableState()
    }

    @CallSuper
    protected open fun reactOnTextChanges(s: CharSequence?, start: Int, before: Int, count: Int) {
        hintAnimator?.animateTextChanged(editText.isFocused, text.isEmpty())
    }

    //region state rendering
    private fun renderState(state: TextInputFieldState) {
        underLineTv.text = errorText
        when (state) {
            TextInputFieldState.Normal -> renderNormalState()
            TextInputFieldState.Disabled -> renderDisabledState()
            TextInputFieldState.Error -> renderErrorState()
        }
        refreshDrawableState()
    }

    private fun renderNormalState() {
        isEnabled = true
        editText.isEnabled = true
    }

    private fun renderDisabledState() {
        isEnabled = false
        editText.isEnabled = false
    }

    private fun renderErrorState() {
        isEnabled = true
        editText.isEnabled = true
        underLineTv.text = ""
        underlineTextAnimator?.changeStateToError(errorText)
    }

    //endregion

    inner class TextInputFieldTextWatcher : TextWatcher {

        override fun afterTextChanged(newText: Editable?) {
            if (newText == null) return
            editText.removeTextChangedListener(this)
            editText.addTextChangedListener(this)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChangedListener?.invoke(s?.toString() ?: EMPTY_STRING)
            reactOnTextChanges(s, start, before, count)
        }
    }
}
