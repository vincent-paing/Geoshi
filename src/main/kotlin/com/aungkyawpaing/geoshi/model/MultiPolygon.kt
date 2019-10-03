package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.GeoShiValidation
import com.aungkyawpaing.geoshi.validation.Validation

data class MultiPolygon(
    val coordinates: List<List<List<Position>>>
) : Geometry() {
    override fun validate(): Validation = GeoShiValidation.isMultiPolygon(coordinates = coordinates)

    override fun getType(): GeometryType = GeometryType.MULTI_POLYGON

}
