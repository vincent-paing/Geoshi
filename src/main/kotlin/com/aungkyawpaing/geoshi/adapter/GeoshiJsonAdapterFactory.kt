package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import java.lang.reflect.Type

class GeoshiJsonAdapterFactory : JsonAdapter.Factory {

  override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
    when (type) {
      Position::class.java -> {
        return PositionJsonAdapter()
      }
      Point::class.java -> {
        return PointJsonAdapter(moshi.adapter(Position::class.java))
      }
      LineString::class.java -> {
        return LineStringJsonAdapter(moshi.adapter(Position::class.java))
      }
      Polygon::class.java -> {
        return PolygonJsonAdapter(moshi.adapter(Position::class.java))
      }
      MultiPoint::class.java -> {
        return MultiPointJsonAdapter(moshi.adapter(Position::class.java))
      }
      MultiLineString::class.java -> {
        return MultiLineStringJsonAdapter(moshi.adapter(Position::class.java))
      }
      MultiPolygon::class.java -> {
        return MultiPolygonJsonAdapter(moshi.adapter(Position::class.java))
      }
      GeometryCollection::class.java -> {
        return GeometryCollectionJsonAdapter(
          moshi.adapter(Point::class.java),
          moshi.adapter(LineString::class.java),
          moshi.adapter(Polygon::class.java),
          moshi.adapter(MultiPoint::class.java),
          moshi.adapter(MultiLineString::class.java),
          moshi.adapter(MultiPolygon::class.java)
        )
      }
      Feature::class.java -> {
        val wildCardAdapter = moshi.adapter<Any>(Any::class.java)
        return FeatureJsonAdapter(
          wildCardAdapter,
          moshi.adapter(Point::class.java),
          moshi.adapter(LineString::class.java),
          moshi.adapter(Polygon::class.java),
          moshi.adapter(MultiPoint::class.java),
          moshi.adapter(MultiLineString::class.java),
          moshi.adapter(MultiPolygon::class.java),
          moshi.adapter(GeometryCollection::class.java)
        )
      }
      FeatureCollection::class.java -> {
        return FeatureCollectionJsonAdapter(moshi.adapter(Feature::class.java))
      }
    }
    return null
  }


}