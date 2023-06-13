package com.thapl.ml.v_textfield.attrs

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import com.thapl.ml.f_textfield.R
import com.thapl.ml.v_textfield.TextInputField
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributes.Companion.DEFAULT_BOX_COLOR
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributes.Companion.DEFAULT_INPUT_TYPE
import com.thapl.ml.v_textfield.attrs.TextInputFieldAttributes.Companion.TEXT_INPUT_FIELD_STATES
import com.thapl.ml.v_textfield.data.HintAnimationParams
import com.thapl.ml.v_textfield.data.TextInputFieldState
import ru.rlrent.base.utils.getThemeAttrColor
import ru.rlrent.base.utils.toDp
import ru.rlrent.base.utils.toSp
import ru.surfstudio.android.utilktx.ktx.text.EMPTY_STRING

/**
 * Объект для парсинга атрибутов [TextInputField]
 */
internal object TextInputFieldAttributesReader {

    fun read(context: Context, attrs: TypedArray): TextInputFieldAttributes = with(attrs) {
        TextInputFieldAttributes(
            text = getString(R.styleable.TextInputField_android_text) ?: EMPTY_STRING,
            textAppearance = getResourceId(R.styleable.TextInputField_android_textAppearance, -1),
            hintText = getString(R.styleable.TextInputField_android_hint) ?: EMPTY_STRING,
            inputType = getInt(R.styleable.TextInputField_android_inputType, DEFAULT_INPUT_TYPE),
            errorText = getString(R.styleable.TextInputField_tif_error_text) ?: EMPTY_STRING,
            helperText = getString(R.styleable.TextInputField_tif_helper_text) ?: EMPTY_STRING,
            endImageButtonIcon = getDrawable(R.styleable.TextInputField_tif_end_image_button_icon),
            hintAnimationParams = getHintAnimationParamsFromAttrs(this),
            useFixedSpaceForUnderlineText = getBoolean(
                R.styleable.TextInputField_tif_use_fixed_space_for_underline_text,
                false
            ),
            boxColor = getBoxColor(context, attrs),
            state = TextInputFieldState.fromAttr(
                getInt(
                    R.styleable.TextInputField_tif_state,
                    TextInputFieldState.Normal.id
                )
            ),
            editTextTextColor = getEditTextTextColor(context, attrs),
            hintTextColor = getHintTextColor(context, attrs),
            helperTextColor = getHelperTextColor(context, attrs),
            backgroundLineColor = getLineColorsStateList(context, attrs),
            underlineViewHorizontalPadding = getDimension(
                R.styleable.TextInputField_tif_underlining_padding,
                0f
            )
        )
    }

    private fun getBoxColor(context: Context, attrs: TypedArray): ColorStateList {
        val colors: IntArray = try {
            intArrayOf(
                attrs.getColor(R.styleable.TextInputField_tif_error_box_color, DEFAULT_BOX_COLOR),
                attrs.getColor(R.styleable.TextInputField_tif_focused_box_color, DEFAULT_BOX_COLOR),
                attrs.getColor(
                    R.styleable.TextInputField_tif_disabled_box_color,
                    DEFAULT_BOX_COLOR
                ),
                attrs.getColor(R.styleable.TextInputField_tif_normal_box_color, DEFAULT_BOX_COLOR)
            )
        } catch (e: RuntimeException) {
            intArrayOf()
        }

        return ColorStateList(TEXT_INPUT_FIELD_STATES, colors)
    }

    private fun getLineColorsStateList(context: Context, attrs: TypedArray): ColorStateList {
        val defaultNormalColor = getThemeAttrColor(context, R.attr.colorControlNormal)
        val defaultActivatedColor = getThemeAttrColor(context, R.attr.colorControlActivated)
        val defaultErrorColor = getThemeAttrColor(context, R.attr.colorError)

        val colors: IntArray = try {
            intArrayOf(
                attrs.getColor(R.styleable.TextInputField_tif_error_line_color, defaultErrorColor),
                attrs.getColor(
                    R.styleable.TextInputField_tif_focused_line_color,
                    defaultActivatedColor
                ),
                attrs.getColor(
                    R.styleable.TextInputField_tif_disabled_line_color,
                    defaultNormalColor
                ),
                attrs.getColor(R.styleable.TextInputField_tif_normal_line_color, defaultNormalColor)
            )
        } catch (e: RuntimeException) {
            intArrayOf()
        }

        return ColorStateList(TEXT_INPUT_FIELD_STATES, colors)
    }

    private fun getEditTextTextColor(context: Context, attrs: TypedArray): ColorStateList {
        val defaultTextColor = getThemeAttrColor(context, R.attr.colorOnSurface)
        val defaultErrorColor = getThemeAttrColor(context, R.attr.colorError)

        val colors = intArrayOf(
            attrs.getColor(R.styleable.TextInputField_tif_error_text_color, defaultErrorColor),
            attrs.getColor(R.styleable.TextInputField_tif_focused_text_color, defaultTextColor),
            attrs.getColor(R.styleable.TextInputField_tif_disabled_text_color, defaultTextColor),
            attrs.getColor(R.styleable.TextInputField_tif_normal_text_color, defaultTextColor)
        )
        return ColorStateList(TEXT_INPUT_FIELD_STATES, colors)
    }

    private fun getHintTextColor(context: Context, attrs: TypedArray): ColorStateList {
        val defaultTextColor = getThemeAttrColor(context, R.attr.colorOnSurface)
        val defaultErrorColor = getThemeAttrColor(context, R.attr.colorError)

        val colors = intArrayOf(
            attrs.getColor(R.styleable.TextInputField_tif_error_hint_color, defaultErrorColor),
            attrs.getColor(R.styleable.TextInputField_tif_focused_hint_color, defaultTextColor),
            attrs.getColor(R.styleable.TextInputField_tif_disabled_hint_color, defaultTextColor),
            attrs.getColor(R.styleable.TextInputField_tif_normal_hint_color, defaultTextColor)
        )
        return ColorStateList(TEXT_INPUT_FIELD_STATES, colors)
    }

    private fun getHelperTextColor(context: Context, attrs: TypedArray): ColorStateList {
        val defaultTextColor = getThemeAttrColor(context, R.attr.colorOnSurface)
        val defaultErrorColor = getThemeAttrColor(context, R.attr.colorError)

        val colors = intArrayOf(
            attrs.getColor(R.styleable.TextInputField_tif_error_helper_color, defaultErrorColor),
            attrs.getColor(R.styleable.TextInputField_tif_focused_helper_color, defaultTextColor),
            attrs.getColor(R.styleable.TextInputField_tif_disabled_helper_color, defaultTextColor),
            attrs.getColor(R.styleable.TextInputField_tif_normal_helper_color, defaultTextColor)
        )
        return ColorStateList(TEXT_INPUT_FIELD_STATES, colors)
    }

    private fun getHintAnimationParamsFromAttrs(attrs: TypedArray): HintAnimationParams {
        val marginTop = attrs.getDimensionPixelSize(
            R.styleable.TextInputField_tif_hint_margin_top,
            4.toDp
        ).toFloat()

        val textSizeFocused = attrs.getDimensionPixelSize(
            R.styleable.TextInputField_tif_hint_text_size_focused,
            14.toSp
        ).toFloat()

        val textSizeCollapsed = attrs.getDimensionPixelSize(
            R.styleable.TextInputField_tif_hint_text_size_not_focused,
            18.toSp
        ).toFloat()

        return HintAnimationParams(
            marginTop,
            textSizeFocused,
            textSizeCollapsed
        )
    }
}
