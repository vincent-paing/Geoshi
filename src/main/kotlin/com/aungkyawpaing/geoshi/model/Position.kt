package com.aungkyawpaing.geoshi.model

/**
 * A position is an array of numbers.  There MUST be two or more
elements.  The first two elements are longitude and latitude, or
easting and northing, precisely in that order and using decimal
numbers.  Altitude or elevation MAY be included as an optional third
element.
 */
data class Position(
  val longitude: Double,
  val latitude: Double,
  val altitude: Double? = null
)
