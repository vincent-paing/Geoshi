package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.GeometryType
import com.aungkyawpaing.geoshi.model.MultiPoint
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.*
import java.lang.IllegalArgumentException

internal class MultiPointJsonAdapter constructor(
  private val positionJsonAdapter: JsonAdapter<Position>
) : JsonAdapter<MultiPoint>() {

  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_COORDINATES = "coordinates"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_COORDINATES)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): MultiPoint? {
    var type: GeometryType? = null
    var positions = mutableListOf<Position>()

    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(MultiPointJsonAdapter.KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of MultiPoint at ${reader.path}"), exception)
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

    if (type != GeometryType.MULIT_POINT) {
      throw JsonDataException("'type' is not of MultiPoint at ${reader.path}")
    }

    if (positions.isEmpty()) {
      throw  JsonDataException("'cooridnates' must bean array of one ore more positions ${reader.path}")
    }

    return MultiPoint(coordinates = positions)
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: MultiPoint?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject() // {
      writer.name(MultiPointJsonAdapter.KEY_TYPE) // "type":
      writer.value(value.getType().convertToString()) // "MultiLine",

      writer.name(MultiPointJsonAdapter.KEY_COORDINATES) // "coordinates":
      writer.beginArray() // [
      value.coordinates.forEach { position ->
        positionJsonAdapter.toJson(writer, position)
      }

      writer.endArray() // ]

      writer.endObject() // }
    }
  }

}