package com.app.dnadetec.responses


class ApiListResponse<T>(
    var responses: List<T>? = null,
    var error: Throwable? = null
) {

    constructor(responses: List<T>?) : this() {
        this.responses = responses
    }

    constructor(error: Throwable) : this() {
        this.error = error
    }

}