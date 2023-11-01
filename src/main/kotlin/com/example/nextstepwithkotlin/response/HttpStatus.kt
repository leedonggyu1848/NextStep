package com.example.nextstepwithkotlin.response

enum class HttpStatus(val code: Int, val phrase: String) {
    OK(200, "OK"), NOT_FOUND(404, "Not Found")
}