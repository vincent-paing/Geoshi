package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.GeoShiValidation
import com.aungkyawpaing.geoshi.validation.Validation

data class Polygon(
    val coordinates: List<List<Position>>
) : Geometry() {
    override fun validate(): Validation = GeoShiValidation.isPolygon(coordinates = coordinates)

    override fun getType(): GeometryType = GeometryType.POLYGON

}
