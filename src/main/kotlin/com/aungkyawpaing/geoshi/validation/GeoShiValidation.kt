package com.aungkyawpaing.geoshi.validation

import com.aungkyawpaing.geoshi.model.*


internal fun List<Validation>.getFirstErrorOrValid(): Validation {
    return with(filterNot { it is Validation.Valid }) {
        if (isEmpty()) Validation.Valid()
        else
            first()

    }
}

internal fun Position.isPosition(): Validation {
    if (longitude !in -180..180) {
        return Validation.Error("Longitude '$longitude' is out of range -180 to 180")
    }
    if (latitude !in -90..90) {
        return Validation.Error("Latitude '$latitude' is out of range -90 to 90")
    }
    return Validation.Valid()
}

internal fun Point.isPoint(): Validation {
    return coordinates.isPosition()
}

//  For type "MultiPoint", the "coordinates" member is an array of  positions.
internal fun MultiPoint.isMultiPoint(): Validation {
    return when {
        coordinates.isEmpty() -> Validation.Error("Coordinates must be array of positions")
        else -> {
            val validations = mutableListOf<Validation>()
            coordinates.forEach {
                validations.add(it.isPosition())
            }

            validations.getFirstErrorOrValid()
        }
    }
}

//For type "LineString", the "coordinates" member is an array of two or more positions.
internal fun LineString.isLineString(): Validation {
    return coordinates.isLineString()
}

//For type "MultiLineString", the "coordinates" member is an array of LineString coordinate arrays.
internal fun MultiLineString.isMultiLineString(): Validation {
    return when {
        coordinates.isEmpty() -> Validation.Error("Coordinates must be an array of LineString coordinate arrays!")
        else -> {
            val validation = mutableListOf<Validation>()
            coordinates.forEach {
                validation.add(it.isLineString())
            }

            validation.getFirstErrorOrValid()
        }
    }
}

// To specify a constraint specific to Polygons, it is useful to introduce the concept of a linear ring:
internal fun Polygon.isPolygon(): Validation {
    return coordinates.isPolygon()
}

//  For type "MultiPolygon", the "coordinates" member is an array of  Polygon coordinate arrays.
internal fun MultiPolygon.isMultiPolygon(): Validation {
    return when {
        coordinates.isEmpty() -> Validation.Error("coordinates must be an array of Polygon coordinate arrays.")
        else -> {
            val validations = mutableListOf<Validation>()
            coordinates.forEach {
                validations.add(it.isPolygon())
            }
            validations.getFirstErrorOrValid()
        }
    }
}

//For type "LineString", the "coordinates" member is an array of two or more positions.
internal fun List<Position>.isLineString(): Validation {
    val validation = mutableListOf(hasAtLeastTwoPositions())

    forEach {
        validation.add(it.isPosition())
    }

    return validation.getFirstErrorOrValid()
}

// A linear ring is a closed LineString with four or more positions. And The first and last positions are equivalent.
internal fun List<Position>.isLinearRing(): Validation {
    return with(this) {
        if (count() >= 4 && first() == last())
            this.isLineString()
        else
            Validation.Error("The coordinates do not meet the LinearRing criteria")

    }
}

// To specify a constraint specific to Polygons, it is useful to introduce the concept of a linear ring:
internal fun List<List<Position>>.isPolygon(): Validation {
    return when {
        isEmpty() -> Validation.Error("coordinates' must be an array of two or more line strings")
        else -> {
            val validations = mutableListOf<Validation>()
            forEach {
                validations.add(it.isLinearRing())
            }
            validations.getFirstErrorOrValid()
        }
    }
}

internal fun List<Position>.hasAtLeastTwoPositions(): Validation {
    return when (count() < 2) {
        true -> Validation.Error("Coordinates must be an array of two or more positions")
        false -> Validation.Valid()
    }
}