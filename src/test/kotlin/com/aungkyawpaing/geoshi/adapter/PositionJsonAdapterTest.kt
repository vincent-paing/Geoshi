package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.Position
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PositionJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun testValidJsonToPosition() {
    //Given
    val jsonString = "[100.0, 0.0]"
    val expected = Position(
      longitude = 100.0,
      latitude = 0.0
    )

    //When
    val actual = moshi.adapter<Position>(Position::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)

  }

  @Test
  fun testValidJsonWithAltitudeToPosition() {
    //Given
    val jsonString = "[100.0, 0.0, 200.0]"
    val expected = Position(
      longitude = 100.0,
      latitude = 0.0,
      altitude = 200.0
    )
    //When
    val actual = moshi.adapter<Position>(Position::class.java).fromJson(jsonString)
    //Then

    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun testInvalidJsonStringWithLessThanTwoElements() {
    //Given
    val jsonString = "[100.0]"

    //When
    val expected = moshi.adapter<Position>(Position::class.java).fromJson(jsonString)
  }

  @Test
  fun testPositionToJson() {

    //Given
    val position = Position(
      longitude = 100.0,
      latitude = 0.0
    )

    val expected = "[100.0,0.0]"

    //When
    val actual = moshi.adapter<Position>(Position::class.java).toJson(position)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test
  fun testPositionWithAltitudeToJson() {

    //Given
    val position = Position(
      longitude = 100.0,
      latitude = 0.0,
      altitude = 200.0
    )

    val expected = "[100.0,0.0,200.0]"

    //When
    val actual = moshi.adapter<Position>(Position::class.java).toJson(position)

    //Then
    Assert.assertEquals(expected, actual)
  }

}