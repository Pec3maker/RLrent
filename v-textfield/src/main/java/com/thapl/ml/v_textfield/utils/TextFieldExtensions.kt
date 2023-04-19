package com.thapl.ml.v_textfield.utils

import android.text.InputFilter
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import com.thapl.ml.v_textfield.TextInputField
import com.thapl.ml.v_textfield.data.TextInputFieldState
import ru.surfstudio.android.utilktx.ktx.ui.view.hideSoftKeyboard

/**
 * Устаналивает ограничение на количество вводимых символов
 */
fun EditText.setMaxLength(length: Int) {
    val inputTextFilter = InputFilter.LengthFilter(length)

    filters = filters
        .filterNot { it is InputFilter.LengthFilter }
        .toTypedArray()
        .plus(inputTextFilter)
}

/**
 * Свойство производит самостоятельный Diff'ing текста и применяет его только тогда,
 * когда установленный в [TextView] текст и новый текст не совпадают.
 *
 * Таким образом мы уменьшаем количество перерисовок и инвалидаций [TextView],
 * а также избегаем потенциального бесконечного loop'a установки текста при неправильном использовании RxJava
 * (забыли навесить оператор distinctUntilChanged на textChangesObservable).
 * */
var TextInputField.distinctText: CharSequence
    get() = text
    set(value) {
        setTextDistinct(value)
    }

/**
 * Метод производит самостоятельный Diff'ing текста и применяет его только тогда,
 * когда установленный в [TextView] текст и [newText] не совпадают.
 *
 * Таким образом мы уменьшаем количество перерисовок и инвалидаций [TextView],
 * а также избегаем потенциального бесконечного loop'a установки текста при неправильном использовании RxJava
 * (забыли навесить оператор distinctUntilChanged на textChangesObservable).
 *
 * @return был ли изменен текст [TextView].
 * */
fun TextInputField.setTextDistinct(newText: CharSequence): Boolean {
    if (TextUtils.equals(newText, text)) return false
    text = newText.toString()
    return true
}

/**
 * Ручное обновление статуса поля ввода.
 *
 * @param errorMessage ошибка валидации поля.
 */
fun TextInputField.updateValidationStatus(errorMessage: Int?) {
    setErrorText(errorMessage)
    state = if (errorMessage == null) {
        TextInputFieldState.Normal
    } else {
        TextInputFieldState.Error
    }
}

/**
 * Расширение для [EditText].
 *
 * Устанавливает `OnEditorActionListener`, который реагирует на действие `imeDone` закрывая клавиатуру и убирает фокус
 *
 * И выполняет код из [block] если мы его передали
 *
 * **Очищает все ранее установленные `OnEditorActionListener`'ы!**
 */
fun EditText.setOnDoneAction(block: (() -> Unit)? = null) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener when (actionId) {
            EditorInfo.IME_ACTION_DONE -> {
                block?.invoke()
                clearFocus()
                hideSoftKeyboard()
                true
            }
            else -> false
        }
    }
}

/**
 * Расширение для [EditText].
 *
 * Устанавливает `OnEditorActionListener`, который реагирует на действие `imeNext` убирая фокус
 *
 * И выполняет код из [block] если мы его передали
 *
 * **Очищает все ранее установленные `OnEditorActionListener`'ы!**
 */
fun EditText.setOnNextAction(block: (() -> Unit)? = null) {
    setOnEditorActionListener { _, actionId, _ ->
        return@setOnEditorActionListener when (actionId) {
            EditorInfo.IME_ACTION_NEXT -> {
                block?.invoke()
                true
            }
            else -> false
        }
    }
}
