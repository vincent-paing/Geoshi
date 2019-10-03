package com.aungkyawpaing.geoshi.model

/**
 * A Feature object represents a spatially bounded thing.  Every Feature
object is a GeoJSON object no matter where it occurs in a GeoJSON
text.
 */
data class Feature(
  val id: String? = null,
  val geometry: Geometry? = null,
  val properties: Map<String, Any>
) : Geometry() {

  override fun getType(): GeometryType = GeometryType.FEATURE

}
