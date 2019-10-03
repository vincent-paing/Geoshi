package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.Validation

/**
 * Each element in the "geometries" array of a GeometryCollection is one of the {GeometryType}:
 */
data class GeometryCollection(
  val geometries: List<Geometry>
) : Geometry() {
  override fun validate(): Validation {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun getType(): GeometryType = GeometryType.GEOMETRY_COLLECTION

}
