package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.GeometryType
import com.aungkyawpaing.geoshi.model.LineString
import com.aungkyawpaing.geoshi.model.Position
import com.aungkyawpaing.geoshi.validation.Validation
import com.aungkyawpaing.geoshi.validation.isLineString
import com.squareup.moshi.*

internal class LineStringJsonAdapter constructor(
  private val positionJsonAdapter: JsonAdapter<Position>
) : JsonAdapter<LineString>() {

  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_COORDINATES = "coordinates"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_COORDINATES)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): LineString? {
    var type: GeometryType? = null
    var positions = mutableListOf<Position>()

    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(LineStringJsonAdapter.KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of LineString at ${reader.path}"), exception)
          }
        }
        1 -> {
          reader.beginArray()

          while (reader.hasNext()) {
            val position = positionJsonAdapter.fromJson(reader)

            if (position != null) {
              positions.add(position)
            }
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

    if (type != GeometryType.LINESTRING) {
      throw JsonDataException("'type' is not of LineString at ${reader.path}")
    }

    if (positions.size < 2) {
      throw  JsonDataException("'cooridnates' must bean array of two or more positions ${reader.path}")
    }

    val lineString =  LineString(coordinates = positions)

    return when(val validation = lineString.isLineString()){
        is Validation.Valid -> lineString
        else -> throw JsonDataException(validation.error)
    }
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: LineString?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject() // {
      writer.name(KEY_TYPE) // "type":
      writer.value(value.getType().convertToString()) // "MultiLine",

      writer.name(KEY_COORDINATES) // "coordinates":
      writer.beginArray() // [
      value.coordinates.forEach { position ->
        positionJsonAdapter.toJson(writer, position)
      }

      writer.endArray() // ]

      writer.endObject() // }
    }
  }


}