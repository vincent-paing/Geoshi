package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.GeometryType
import com.aungkyawpaing.geoshi.model.MultiPolygon
import com.aungkyawpaing.geoshi.model.Position
import com.aungkyawpaing.geoshi.validation.Validation
import com.squareup.moshi.*
import java.lang.IllegalArgumentException

internal class MultiPolygonJsonAdapter constructor(
    private val positionJsonAdapter: JsonAdapter<Position>
) : JsonAdapter<MultiPolygon>() {

    companion object {
        private const val KEY_TYPE = "type"
        private const val KEY_COORDINATES = "coordinates"
        private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_COORDINATES)
    }

    @FromJson
    override fun fromJson(reader: JsonReader): MultiPolygon? {
        var type: GeometryType? = null
        var polygonPositionList = mutableListOf<List<List<Position>>>()

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.selectName(MultiPolygonJsonAdapter.KEYS_OPTIONS)) {
                0 -> {
                    try {
                        type = GeometryType.convertFromString(reader.nextString())
                    } catch (exception: IllegalArgumentException) {
                        throw JsonDataException(
                            ("'type' is not of MultiPolygon at ${reader.path}"),
                            exception
                        )
                    }
                }
                1 -> {
                    reader.beginArray()

                    while (reader.hasNext()) {
                        val polygonList = mutableListOf<List<Position>>()

                        reader.beginArray()
                        while (reader.hasNext()) {
                            val positionList = mutableListOf<Position>()

                            reader.beginArray()
                            while (reader.hasNext()) {
                                val position = positionJsonAdapter.fromJson(reader)

                                if (position != null) {
                                    positionList.add(position)
                                }
                            }
                            reader.endArray()

                            polygonList.add(positionList)
                        }
                        reader.endArray()
                        polygonPositionList.add(polygonList)
                    }

                    reader.endArray()
                }
                else -> {
                    reader.skipName()
                    reader.skipValue()
                }
            }
        }
        reader.endObject()

        if (type == null) {
            throw JsonDataException("Requires field : 'type' is missing at ${reader.path}")
        }

        if (type != GeometryType.MULTI_POLYGON) {
            throw JsonDataException("'type' is not of MultiPolygon at ${reader.path}")
        }


        val multiPolygon = MultiPolygon(coordinates = polygonPositionList)

        return when (val validation = multiPolygon.validate()) {
            is Validation.Valid -> multiPolygon
            else -> throw JsonDataException(validation.error)
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: MultiPolygon?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.beginObject() // {
            writer.name(MultiPolygonJsonAdapter.KEY_TYPE) // "type":
            writer.value(value.getType().convertToString()) // "MultiLine",

            writer.name(MultiPolygonJsonAdapter.KEY_COORDINATES) // "coordinates":
            writer.beginArray() // [
            value.coordinates.forEach { polygonList ->
                writer.beginArray()

                polygonList.forEach { positionList ->
                    writer.beginArray()

                    positionList.forEach { position ->
                        positionJsonAdapter.toJson(writer, position)
                    }

                    writer.endArray()
                }

                writer.endArray()
            }

            writer.endArray() // ]

            writer.endObject() // }
        }
    }


}