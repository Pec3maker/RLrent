package com.thapl.ml.v_textfield.error

/**
 * Исключение возникающее при добавлении нескольких EditText'ов в TextInputField
 */
class EditTextAlreadySetException
    : RuntimeException("TextInputField не может содержать больше одного EditText")