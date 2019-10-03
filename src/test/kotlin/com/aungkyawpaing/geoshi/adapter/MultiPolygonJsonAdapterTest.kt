package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.MultiPolygon
import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class MultiPolygonJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testValidJsonToMultiPolygon() {
    //Given
    val jsonString =
      "{\"type\":\"MultiPolygon\",\"coordinates\":[[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]],[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.2,0.2],[100.2,0.8],[100.8,0.8],[100.8,0.2],[100.2,0.2]]]]}"

    val expected = MultiPolygon(
      coordinates = listOf(
        listOf(
          listOf(
            Position(102.0, 2.0),
            Position(103.0, 2.0),
            Position(103.0, 3.0),
            Position(102.0, 3.0),
            Position(102.0, 2.0)
          )
        ),
        listOf(
          listOf(
            Position(100.0, 0.0),
            Position(101.0, 0.0),
            Position(101.0, 1.0),
            Position(100.0, 1.0),
            Position(100.0, 0.0)
          ),
          listOf(
            Position(100.2, 0.2),
            Position(100.2, 0.8),
            Position(100.8, 0.8),
            Position(100.8, 0.2),
            Position(100.2, 0.2)
          )
        )
      )
    )

    //When
    val actual = moshi.adapter(MultiPolygon::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonMissingType() {
    //Given
    val jsonString =
      "{\"coordinates\":[[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]],[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.2,0.2],[100.2,0.8],[100.8,0.8],[100.8,0.2],[100.2,0.2]]]]}"

    //When
    val actual = moshi.adapter(MultiPolygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeGarbageValue() {
    //Given
    val jsonString =
      "{\"type\":\"Garbage\",\"coordinates\":[[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]],[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.2,0.2],[100.2,0.8],[100.8,0.8],[100.8,0.2],[100.2,0.2]]]]}"

    //When
    val actual = moshi.adapter(MultiPolygon::class.java).fromJson(jsonString)

  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonTypeCoordinatesMissing() {
    //Given
    val jsonString =
      "{\"type\":\"MultiPolygon\"}"

    //When
    val actual = moshi.adapter(MultiPolygon::class.java).fromJson(jsonString)


  }

  @Test
  fun testMultiPolygonToJson() {
    //Given
    val multiPolygon = MultiPolygon(
      coordinates = listOf(
        listOf(
          listOf(
            Position(102.0, 2.0),
            Position(103.0, 2.0),
            Position(103.0, 3.0),
            Position(102.0, 3.0),
            Position(102.0, 2.0)
          )
        ),
        listOf(
          listOf(
            Position(100.0, 0.0),
            Position(101.0, 0.0),
            Position(101.0, 1.0),
            Position(100.0, 1.0),
            Position(100.0, 0.0)
          ),
          listOf(
            Position(100.2, 0.2),
            Position(100.2, 0.8),
            Position(100.8, 0.8),
            Position(100.8, 0.2),
            Position(100.2, 0.2)
          )
        )
      )
    )

    val expected =
      "{\"type\":\"MultiPolygon\",\"coordinates\":[[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]],[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]],[[100.2,0.2],[100.2,0.8],[100.8,0.8],[100.8,0.2],[100.2,0.2]]]]}"

    //When
    val actual = moshi.adapter(MultiPolygon::class.java).toJson(multiPolygon)

    //Then
    Assert.assertEquals(expected, actual)
  }


}