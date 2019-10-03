package com.aungkyawpaing.geoshi.model

data class Point(
  val coordinates: Position
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.POINT

}