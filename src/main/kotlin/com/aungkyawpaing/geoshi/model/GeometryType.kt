package com.aungkyawpaing.geoshi.model

/**
 * GeoJSON supports the following geometry types (case sensitive strings):
 * Point
 * LineString
 * Polygon
 * MultiPoint
 * MultiLineString,
 * MultiPolygon
 * GeometryCollection.
 * Feature in GeoJSON contain aGeometry object and additional properties,
 * FeatureCollection contains a list of Features.
 */
enum class GeometryType() {
  POINT,
  LINESTRING,
  POLYGON,
  MULIT_POINT,
  MULTI_LINE_STRING,
  MULTI_POLYGON,
  GEOMETRY_COLLECTION,
  FEATURE,
  FEATURE_COLLECTION;

  /**
   * Convert to case sensitive strings
   */
  fun convertToString(): String {
    return when (this) {
      POINT -> "Point"
      LINESTRING -> "LineString"
      POLYGON -> "Polygon"
      MULIT_POINT -> "MultiPoint"
      MULTI_LINE_STRING -> "MultiLineString"
      MULTI_POLYGON -> "MultiPolygon"
      GEOMETRY_COLLECTION -> "GeometryCollection"
      FEATURE -> "Feature"
      FEATURE_COLLECTION -> "FeatureCollection"
    }

  }

  companion object {

    @JvmStatic
    fun convertFromString(stringValue: String): GeometryType {
      return when (stringValue) {
        "Point" -> GeometryType.POINT
        "LineString" -> GeometryType.LINESTRING
        "Polygon" -> GeometryType.POLYGON
        "MultiPoint" -> GeometryType.MULIT_POINT
        "MultiLineString" -> GeometryType.MULTI_LINE_STRING
        "MultiPolygon" -> GeometryType.MULTI_POLYGON
        "GeometryCollection" -> GeometryType.GEOMETRY_COLLECTION
        "Feature" -> GeometryType.FEATURE
        "FeatureCollection" -> GeometryType.FEATURE_COLLECTION
        else -> throw IllegalArgumentException("$stringValue is not specified according to geojson spec")
      }
    }

  }

}