package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.*
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class GeoshiJsonAdapterFactoryTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testPosition() {
    val actual = geoshiJsonAdapterFactory.create(Position::class.java, mutableSetOf(), moshi)
    val expected = PositionJsonAdapter::class.java
    Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testPoint() {
    val actual = geoshiJsonAdapterFactory.create(Point::class.java, mutableSetOf(), moshi)
    val expected = PointJsonAdapter::class.java
    Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testLineString() {
    val actual = geoshiJsonAdapterFactory.create(LineString::class.java, mutableSetOf(), moshi)
    val expected = LineStringJsonAdapter::class.java
    Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testPolygon() {
    val actual = geoshiJsonAdapterFactory.create(Polygon::class.java, mutableSetOf(), moshi)
    val expected = PolygonJsonAdapter::class.java
    Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testMultiPoint() {
    val actual = geoshiJsonAdapterFactory.create(MultiPoint::class.java, mutableSetOf(), moshi)
    val expected = MultiPointJsonAdapter::class.java
    Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testMultiLineString() {
    val actual = geoshiJsonAdapterFactory.create(MultiLineString::class.java, mutableSetOf(), moshi)
    val expected = MultiLineStringJsonAdapter::class.java
    Assert.assertEquals(expected, actual!!::class.java)
  }


  @Test
  fun testMultiPolygon() {
    val actual = geoshiJsonAdapterFactory.create(MultiPolygon::class.java, mutableSetOf(), moshi)
    val expected = MultiPolygonJsonAdapter::class.java
    return Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testGeometryCollection() {
    val actual = geoshiJsonAdapterFactory.create(GeometryCollection::class.java, mutableSetOf(), moshi)
    val expected = GeometryCollectionJsonAdapter::class.java
    return Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testFeature() {
    val actual = geoshiJsonAdapterFactory.create(Feature::class.java, mutableSetOf(), moshi)
    val expected = FeatureJsonAdapter::class.java
    return Assert.assertEquals(expected, actual!!::class.java)
  }

  @Test
  fun testFeatureCollection() {
    val actual = geoshiJsonAdapterFactory.create(FeatureCollection::class.java, mutableSetOf(), moshi)
    val expected = FeatureCollectionJsonAdapter::class.java
    return Assert.assertEquals(expected, actual!!::class.java)
  }


}