package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.LineString
import com.aungkyawpaing.geoshi.model.MultiPoint
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test


class MultiPointJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testValidJsonToMultiPoint() {
    //Given
    val jsonString = "{\"type\":\"MultiPoint\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    val expected = MultiPoint(
      coordinates = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
      )
    )

    //When
    val actual = moshi.adapter(MultiPoint::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testValidJsonOnlyOneCoordinate() {
    //Given
    val jsonString = "{\"type\":\"MultiPoint\",\"coordinates\":[[100.0,0.0]]}"
    val expected = MultiPoint(
      coordinates = listOf(
        Position(100.0, 0.0)
      )
    )

    //When
    val actual = moshi.adapter(MultiPoint::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString = "{\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(MultiPoint::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString = "{\"type\":\"Garbage\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(MultiPoint::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString = "{\"type\":\"MultiPoint\"}"

    //When
    val actual = moshi.adapter(MultiPoint::class.java).fromJson(jsonString)
  }

  @Test
  fun testMultiPointToJson() {
    //Given
    val multiPoint = MultiPoint(
      coordinates = listOf(
        Position(100.0, 0.0),
        Position(101.0, 1.0)
      )
    )

    val expected = "{\"type\":\"MultiPoint\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"

    //When
    val actual = moshi.adapter(MultiPoint::class.java).toJson(multiPoint)

    //Then
    Assert.assertEquals(expected, actual)
  }


}