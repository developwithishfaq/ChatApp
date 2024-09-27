package com.test.chatappcopy.core.constants

fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return this.matches(emailRegex.toRegex())
}

fun String.isValidText(minLength: Int = 4): Boolean {
    return isNotBlank() && length >= minLength
}

fun isOurCredentialsOk(
    email: String,
    password: String
): Boolean {
    return email.isValidEmail() && password.isValidText()
}
