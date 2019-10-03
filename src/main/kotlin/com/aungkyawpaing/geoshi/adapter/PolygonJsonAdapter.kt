package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.GeometryType
import com.aungkyawpaing.geoshi.model.Polygon
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.*

internal class PolygonJsonAdapter constructor(
  private val positionJsonAdapter: JsonAdapter<Position>
) : JsonAdapter<Polygon>() {

  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_COORDINATES = "coordinates"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_COORDINATES)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): Polygon? {
    var type: GeometryType? = null
    var lineStringPositionList = mutableListOf<List<Position>>()

    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(PolygonJsonAdapter.KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of Polygon at ${reader.path}"), exception)
          }
        }
        1 -> {
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

            lineStringPositionList.add(positionList)
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

    if (type != GeometryType.POLYGON) {
      throw JsonDataException("'type' is not of Polygon at ${reader.path}")
    }

    if (lineStringPositionList.isEmpty()) {
      throw JsonDataException("'coordinates' must bean array of two or more line strings at ${reader.path}")
    }

    return Polygon(lineStringPositionList)
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: Polygon?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject() // {
      writer.name(PolygonJsonAdapter.KEY_TYPE) // "type":
      writer.value(value.getType().convertToString()) // "MultiLine",

      writer.name(PolygonJsonAdapter.KEY_COORDINATES) // "coordinates":
      writer.beginArray() // [
      value.coordinates.forEach { positionList ->
        writer.beginArray()
        positionList.forEach { position ->
          positionJsonAdapter.toJson(writer, position)
        }
        writer.endArray()

      }

      writer.endArray() // ]

      writer.endObject() // }
    }
  }

}