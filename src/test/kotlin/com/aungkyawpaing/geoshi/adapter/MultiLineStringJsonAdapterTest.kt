package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.LineString
import com.aungkyawpaing.geoshi.model.MultiLineString
import com.aungkyawpaing.geoshi.model.MultiPoint
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class MultiLineStringJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testValidJsonToMultiLineString() {
    //Given
    val jsonString =
      "{\"type\":\"MultiLineString\",\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}"

    val expected = MultiLineString(
      coordinates = listOf(
        listOf(
          Position(100.0, 0.0),
          Position(101.0, 1.0)
        ),
        listOf(
          Position(102.0, 2.0),
          Position(103.0, 3.0)
        )
      )
    )

    //When
    val actual = moshi.adapter(MultiLineString::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString =
      "{\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}"

    //When
    val actual = moshi.adapter(MultiLineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString = "{\"type\":\"Garbage\",\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}"

    //When
    val actual = moshi.adapter(MultiLineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString =
      "{\"type\":\"MultiLineString\"}"

    //When
    val actual = moshi.adapter(MultiLineString::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonOnlyOneCoordinate() {
    //Given
    val jsonString = "{\"type\":\"Garbage\",\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}"

    //When
    val actual = moshi.adapter(MultiLineString::class.java).fromJson(jsonString)
  }

  @Test
  fun testMultiLineStringToJson() {
    //Given
    val multiLineString = MultiLineString(
      coordinates = listOf(
        listOf(
          Position(100.0, 0.0),
          Position(101.0, 1.0)
        ),
        listOf(
          Position(102.0, 2.0),
          Position(103.0, 3.0)
        )
      )
    )

    val expected =
      "{\"type\":\"MultiLineString\",\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}"

    //When
    val actual = moshi.adapter(MultiLineString::class.java).toJson(multiLineString)

    //Then
    Assert.assertEquals(expected, actual)
  }


}