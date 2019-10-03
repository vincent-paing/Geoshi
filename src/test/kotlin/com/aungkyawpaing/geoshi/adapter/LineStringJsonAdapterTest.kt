package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.LineString
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test


class LineStringJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testValidJsonToLineString() {
    //Given
    val jsonString = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    val expected = LineString(
      coordinates = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
      )
    )

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString = "{\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString = "{\"type\":\"Garbage\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString = "{\"type\":\"LineString\"}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonOnlyOneCoordinate() {
    //Given
    val jsonString = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)
  }

  @Test
  fun testLineStringToJson() {
    //Given
    val lineString = LineString(
      coordinates = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
      )
    )

    val expected = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).toJson(lineString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidLineStringCoordinatesValidation() {
    //Given
    val jsonString = "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[190.0,99.0]]}"

    //When
    val actual = moshi.adapter(LineString::class.java).fromJson(jsonString)

  }
}