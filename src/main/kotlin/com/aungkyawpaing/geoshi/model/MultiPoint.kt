package com.aungkyawpaing.geoshi.model

import com.aungkyawpaing.geoshi.validation.GeoShiValidation
import com.aungkyawpaing.geoshi.validation.Validation

data class MultiPoint(
    val coordinates: List<Position>
) : Geometry() {
    override fun validate(): Validation = GeoShiValidation.isMultiPoint(coordinates = coordinates)

    override fun getType(): GeometryType = GeometryType.MULIT_POINT

}
