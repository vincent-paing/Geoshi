package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.GeoShiValidation
import com.aungkyawpaing.geoshi.validation.Validation

data class MultiLineString(
    val coordinates: List<List<Position>>
) : Geometry() {
    override fun validate(): Validation =
        GeoShiValidation.isMultiLineString(coordinates = coordinates)

    override fun getType(): GeometryType = GeometryType.MULTI_LINE_STRING

}
