package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.*
import com.squareup.moshi.*
import java.lang.IllegalArgumentException

internal class FeatureCollectionJsonAdapter constructor(
  private val featureJsonAdapter: JsonAdapter<Feature>
) : JsonAdapter<FeatureCollection>() {

  companion object {
    private const val KEY_TYPE = "type"
    private const val KEY_FEATURE = "features"
    private val KEYS_OPTIONS = JsonReader.Options.of(KEY_TYPE, KEY_FEATURE)
  }

  @FromJson
  override fun fromJson(reader: JsonReader): FeatureCollection? {

    var type: GeometryType? = null
    var featureList = mutableListOf<Feature>()


    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(FeatureCollectionJsonAdapter.KEYS_OPTIONS)) {
        0 -> {
          try {
            type = GeometryType.convertFromString(reader.nextString())
          } catch (exception: IllegalArgumentException) {
            throw JsonDataException(("'type' is not of FeatureCollection at ${reader.path}"), exception)
          }
        }
        1 -> {
          reader.beginArray()

          while (reader.hasNext()) {
            val jsonValueMap = reader.readJsonValue() as Map<String, String>
            val geometryTypeString = jsonValueMap[FeatureCollectionJsonAdapter.KEY_TYPE]

            if (geometryTypeString != null) {
              val type = GeometryType.convertFromString(geometryTypeString)

              if (type == GeometryType.FEATURE) {
                val feature = featureJsonAdapter.fromJsonValue(jsonValueMap)
                if (feature != null) featureList.add(feature)
              }
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

    if (type == null) {
      throw JsonDataException("Requires field : 'type' is missing at ${reader.path}")
    }

    if (type != GeometryType.FEATURE_COLLECTION) {
      throw JsonDataException("'type' is not of FeatureCollection at ${reader.path}")
    }

    reader.endObject()

    return FeatureCollection(featureList)
  }

  @ToJson
  override fun toJson(writer: JsonWriter, value: FeatureCollection?) {
    if (value == null) {
      writer.nullValue()
    } else {
      writer.beginObject()
      writer.name(FeatureCollectionJsonAdapter.KEY_TYPE) // "type":
      writer.value(value.getType().convertToString()) // "GeometryCollection",

      writer.name(FeatureCollectionJsonAdapter.KEY_FEATURE) // "geometries":
      writer.beginArray() // [
      value.features.forEach { feature ->
        featureJsonAdapter.toJson(writer, feature)
      }
      writer.endArray()

      writer.endObject()
    }
  }

}