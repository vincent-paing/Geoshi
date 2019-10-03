package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.Validation

abstract class Geometry {
  abstract fun getType(): GeometryType
  abstract fun validate() : Validation
}