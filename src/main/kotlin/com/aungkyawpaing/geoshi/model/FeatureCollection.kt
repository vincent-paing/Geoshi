package com.aungkyawpaing.geoshi.model

data class FeatureCollection(
  val features: List<Feature>
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.FEATURE_COLLECTION

}
