package com.aungkyawpaing.geoshi.model

data class MultiPolygon(
  val coordinates: List<List<List<Position>>>
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.MULTI_POLYGON

}
