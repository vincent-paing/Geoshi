package com.aungkyawpaing.geoshi.validation

sealed class Validation(open val error : String?){
    data class Error(override val error: String?) : Validation(error)
    data class Valid(override val error: String? = null) : Validation(error)
}