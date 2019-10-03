package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.Polygon
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class PolygonJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testValidJsonToMultiLineString() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    val expected = Polygon(
      coordinates = listOf(
        listOf(
          Position(100.0, 0.0),
          Position(101.0, 0.0),
          Position(101.0, 1.0),
          Position(100.0, 1.0),
          Position(100.0, 0.0)
        ),
        listOf(
          Position(100.8, 0.8),
          Position(100.8, 0.2),
          Position(100.2, 0.2),
          Position(100.2, 0.8),
          Position(100.8, 0.8)
        )
      )
    )

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString =
      "{\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString =
      "{\"type\":\"Garbage\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\"}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test
  fun testMultiLineStringToJson() {
    //Given
    val polygon = Polygon(
      coordinates = listOf(
        listOf(
          Position(100.0, 0.0),
          Position(101.0, 0.0),
          Position(101.0, 1.0),
          Position(100.0, 1.0),
          Position(100.0, 0.0)
        ),
        listOf(
          Position(100.8, 0.8),
          Position(100.8, 0.2),
          Position(100.2, 0.2),
          Position(100.2, 0.8),
          Position(100.8, 0.8)
        )
      )
    )

    val expected =
      "{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).toJson(polygon)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidPolygonCoordinatesValidation() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\",\"coordinates\":[[[190.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[190.0,0.0]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidPolygonValidationWithLineRingFirstAndLast() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\",\"coordinates\":[[[110.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.8,0.8],[100.8,0.2],[100.2,0.2],[100.2,0.8],[100.8,0.8]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidPolygonValidationWithLineRingSizeFourOrMore() {
    //Given
    val jsonString =
      "{\"type\":\"Polygon\",\"coordinates\":[[[100.2,0.2],[100.2,0.8],[100.2,0.2]]]}"

    //When
    val actual = moshi.adapter(Polygon::class.java).fromJson(jsonString)

  }

}