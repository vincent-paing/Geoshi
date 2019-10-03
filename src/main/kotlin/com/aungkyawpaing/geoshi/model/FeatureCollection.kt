package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.Validation

data class FeatureCollection(
  val features: List<Feature>
) : Geometry() {
  override fun validate(): Validation {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getType(): GeometryType = GeometryType.FEATURE_COLLECTION

}
