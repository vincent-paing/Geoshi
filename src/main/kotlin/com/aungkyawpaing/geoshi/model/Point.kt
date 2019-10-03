package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.GeoShiValidation
import com.aungkyawpaing.geoshi.validation.Validation

data class Point(
    val coordinates: Position
) : Geometry() {
    override fun validate(): Validation = GeoShiValidation.isPoint(coordinate = coordinates)

    override fun getType(): GeometryType = GeometryType.POINT

}