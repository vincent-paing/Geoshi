package com.aungkyawpaing.geoshi.validation

import com.aungkyawpaing.geoshi.model.Position

object GeoShiValidation {

    private const val minimumLatitude = -90
    private const val maximumLatitude = 90
    private const val minimumLongitude = -180
    private const val maximumLongitude = 180
    private val latitudeBoundaries = minimumLatitude..maximumLatitude
    private val longitudeBoundaries = minimumLongitude..maximumLongitude

    private fun isPosition(coordinate: Position): Validation {
        if (coordinate.longitude !in longitudeBoundaries) {
            return Validation.Error("Longitude '$coordinate.longitude' is out of range -180 to 180")
        }
        if (coordinate.latitude !in latitudeBoundaries) {
            return Validation.Error("Latitude '$coordinate.latitude' is out of range -90 to 90")
        }
        return Validation.Valid()
    }

    fun isPoint(coordinate: Position): Validation = isPosition(coordinate)

    //  For type "MultiPoint", the "coordinates" member is an array of  positions.
    fun isMultiPoint(coordinates: List<Position>): Validation {
        return when {
            coordinates.isEmpty() -> Validation.Error("Coordinates must be array of positions")
            else -> {
                val validations = mutableListOf<Validation>()
                coordinates.forEach {
                    validations.add(isPoint(it))
                }

                validations.getFirstErrorOrValid()
            }
        }

    }

    //For type "LineString", the "coordinates" member is an array of two or more positions.
    fun isLineString(coordinates: List<Position>): Validation {
        val validation = mutableListOf(hasAtLeastTwoPositions(coordinates))

        coordinates.forEach {
            validation.add(isPoint(it))
        }

        return validation.getFirstErrorOrValid()

    }

    //For type "MultiLineString", the "coordinates" member is an array of LineString coordinate arrays.
    fun isMultiLineString(coordinates: List<List<Position>>): Validation {
        return when {
            coordinates.isEmpty() -> Validation.Error("Coordinates must be an array of LineString coordinate arrays!")
            else -> {
                val validation = mutableListOf<Validation>()
                coordinates.forEach {
                    validation.add(isLineString(it))
                }

                validation.getFirstErrorOrValid()
            }
        }
    }

    // To specify a constraint specific to Polygons, it is useful to introduce the concept of a linear ring:
    fun isPolygon(coordinates: List<List<Position>>): Validation {
        return when {
            coordinates.isEmpty() -> Validation.Error("coordinates' must be an array of two or more line strings")
            else -> {
                val validations = mutableListOf<Validation>()
                coordinates.forEach {
                    validations.add(isLinearRing(it))
                }
                validations.getFirstErrorOrValid()
            }
        }
    }

    //  For type "MultiPolygon", the "coordinates" member is an array of  Polygon coordinate arrays.
    fun isMultiPolygon(coordinates: List<List<List<Position>>>): Validation {
        return when {
            coordinates.isEmpty() -> Validation.Error("coordinates must be an array of Polygon coordinate arrays.")
            else -> {
                val validations = mutableListOf<Validation>()
                coordinates.forEach {
                    validations.add(isPolygon(it))
                }
                validations.getFirstErrorOrValid()
            }
        }
    }

    private fun hasAtLeastTwoPositions(coordinates: List<Position>): Validation {
        return when (coordinates.count() < 2) {
            true -> Validation.Error("Coordinates must be an array of two or more positions")
            false -> Validation.Valid()
        }
    }

    // A linear ring is a closed LineString with four or more positions. And The first and last positions are equivalent.
    private fun isLinearRing(coordinates: List<Position>): Validation {
        return with(coordinates) {
            if (count() >= 4 && first() == last())
                isLineString(coordinates)
            else
                Validation.Error("The coordinates do not meet the LinearRing criteria")

        }
    }
}


internal fun List<Validation>.getFirstErrorOrValid(): Validation {
    return with(filterNot { it is Validation.Valid }) {
        if (isEmpty()) Validation.Valid()
        else {
            //remove valid within the validation list
            val validations = filterIsInstance<Validation.Error>()
            validations.first()
        }
    }
}