package com.aungkyawpaing.geoshi.model

/**
 * Each element in the "geometries" array of a GeometryCollection is one of the {GeometryType}:
 */
data class GeometryCollection(
  val geometries: List<Geometry>
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.GEOMETRY_COLLECTION

}
