package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.*
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class FeatureJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun convertPointFeatureFromJsonString() {
    //Given
    val jsonString =
      "{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}}"

    val expected = Feature(
      id = null,
      geometry = Point(
        coordinates = Position(102.0, 0.5)
      ),
      properties = mapOf(
        "prop0" to "value0"
      )
    )

    //When
    val actual = moshi.adapter(Feature::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun convertFeatureFromJsonStringWithNullGeometry() {
    //Given
    val jsonString =
      "{\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"name\":\"Dinagat Islands\"}}"

    val expected = Feature(
      id = null,
      geometry = null,
      properties = mapOf(
        "name" to "Dinagat Islands"
      )
    )

    //When
    val actual = moshi.adapter(Feature::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }


  @Test
  fun convertPointFeatureWithIdFromJsonString() {
    //Given
    val jsonString =
      "{\"id\":\"FeatureId\",\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}}"

    val expected = Feature(
      id = "FeatureId",
      geometry = Point(
        coordinates = Position(102.0, 0.5)
      ),
      properties = mapOf(
        "prop0" to "value0"
      )
    )

    //When
    val actual = moshi.adapter(Feature::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun convertLineStringFeatureFromJsonString() {
    //Given
    val jsonString =
      "{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":0.0}}"

    val expected = Feature(
      id = null,
      geometry = LineString(
        coordinates = listOf(
          Position(102.0, 0.0),
          Position(103.0, 1.0),
          Position(104.0, 0.0),
          Position(105.0, 1.0)
        )
      ),
      properties = mapOf(
        "prop0" to "value0",
        "prop1" to 0.0
      )
    )

    //When
    val actual = moshi.adapter(Feature::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun convertPolygonFeatureFromJsonString() {
    //Given
    val jsonString =
      "{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":{\"this\":\"that\"}}}"

    val expected = Feature(
      id = null,
      geometry = Polygon(
        coordinates = listOf(
          listOf(
            Position(100.0, 0.0),
            Position(101.0, 0.0),
            Position(101.0, 1.0),
            Position(100.0, 1.0),
            Position(100.0, 0.0)
          )
        )
      ),
      properties = mapOf(
        "prop0" to "value0",
        "prop1" to mapOf(
          "this" to "that"
        )
      )
    )

    //When
    val actual = moshi.adapter(Feature::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }


  @Test(expected = JsonDataException::class)
  fun convertInvalidJsonStringNoType() {
    val jsonString =
      "{\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}}"

    //When
    moshi.adapter(Feature::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun convertInvalidJsonStringGarbageType() {
    val jsonString =
      "{\"type\":\"Garbage\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}}"

    //When
    moshi.adapter(Feature::class.java).fromJson(jsonString)
  }


  @Test
  fun convertFeatureToJsonString() {
    //Given
    val feature = Feature(
      id = null,
      geometry = Point(
        coordinates = Position(102.0, 0.5)
      ),
      properties = mapOf(
        "prop0" to "value0"
      )
    )

    val expected =
      "{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}}"

    //When
    val actual = moshi.adapter(Feature::class.java).toJson(feature)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun convertFeatureWithNullGeometryToJsonString() {
    //Given
    val feature = Feature(
      id = null,
      geometry = null,
      properties = mapOf(
        "prop0" to "value0"
      )
    )

    val expected =
      "{\"type\":\"Feature\",\"geometry\":null,\"properties\":{\"prop0\":\"value0\"}}"

    //When
    val actual = moshi.adapter(Feature::class.java).toJson(feature)

    //Then
    Assert.assertEquals(expected, actual)
  }
}