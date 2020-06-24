package com.app.dnadetec.responses

import com.app.dnadetec.models.UserProfile

class ApiSingleResponse<T>(
    var response: T? = null,
    var error: Throwable? = null
) {

    constructor(response: T) : this() {
        this.response = response
    }

    constructor(error: Throwable) : this() {
        this.error = error
    }

}