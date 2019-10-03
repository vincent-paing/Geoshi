package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.GeometryType
import com.aungkyawpaing.geoshi.model.MultiLineString
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.*

internal class MultiLineStringJsonAdapter constructor(
  private val positionJsonAdapter: JsonAdapter<Position>
) : JsonAdapter<MultiLineString>() {


  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_COORDINATES = "coordinates"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_COORDINATES)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): MultiLineString? {
    var type: GeometryType? = null
    var lineStringPositionList = mutableListOf<List<Position>>()

    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(MultiLineStringJsonAdapter.KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of MultiLineString at ${reader.path}"), exception)
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

    if (type != GeometryType.MULTI_LINE_STRING) {
      throw JsonDataException("'type' is not of MultiLineString at ${reader.path}")
    }

    if (lineStringPositionList.isEmpty()) {
      throw JsonDataException("'cooridnates' must bean array of two or more line strings at ${reader.path}")
    }

    return MultiLineString(lineStringPositionList)
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: MultiLineString?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject() // {
      writer.name(MultiLineStringJsonAdapter.KEY_TYPE) // "type":
      writer.value(value.getType().convertToString()) // "MultiLine",

      writer.name(MultiLineStringJsonAdapter.KEY_COORDINATES) // "coordinates":
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
