package com.aungkyawpaing.geoshi.adapter

import com.aungkyawpaing.geoshi.model.*
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.Moshi
import org.junit.Assert
import org.junit.Test

class FeatureCollectionJsonAdapterTest {

  private val geoshiJsonAdapterFactory = GeoshiJsonAdapterFactory()
  private val moshi = Moshi.Builder().add(geoshiJsonAdapterFactory).build()

  @Test
  fun convertValidJsonToFeatureCollection() {
    //Given

    val jsonString =
      "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":0.0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":{\"this\":\"that\"}}}]}"


    val expected = FeatureCollection(
      features =
      listOf(
        Feature(
          geometry = Point(
            coordinates = Position(102.0, 0.5)
          ),
          properties = mapOf(
            "prop0" to "value0"
          )
        ),
        Feature(
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
        ), Feature(
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
      )
    )

    //When
    val actual = moshi.adapter(FeatureCollection::class.java).fromJson(jsonString)

    //Then
    Assert.assertEquals(expected, actual)
  }

  @Test(expected = JsonDataException::class)
  fun convertInvalidJsonEmptyType() {
    //Given
    val jsonString =
      "{\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":0.0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":{\"this\":\"that\"}}}]}"

    //When
    moshi.adapter(FeatureCollection::class.java).fromJson(jsonString)
  }

  @Test(expected = JsonDataException::class)
  fun convertInvalidJsonGarbageValue() {
    //Given
    val jsonString =
      "{\"type\":\"Garbage\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":0.0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":{\"this\":\"that\"}}}]}"

    //When
    moshi.adapter(FeatureCollection::class.java).fromJson(jsonString)
  }

  @Test
  fun convertFeatureCollectionToJsonString() {
    //Given
    val featureCollection = FeatureCollection(
      features =
      listOf(
        Feature(
          geometry = Point(
            coordinates = Position(102.0, 0.5)
          ),
          properties = mapOf(
            "prop0" to "value0"
          )
        ),
        Feature(
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
        ), Feature(
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
      )
    )

    val expected =
      "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[102.0,0.5]},\"properties\":{\"prop0\":\"value0\"}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[102.0,0.0],[103.0,1.0],[104.0,0.0],[105.0,1.0]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":0.0}},{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[100.0,0.0],[101.0,0.0],[101.0,1.0],[100.0,1.0],[100.0,0.0]]]},\"properties\":{\"prop0\":\"value0\",\"prop1\":{\"this\":\"that\"}}}]}"

    //When
    val actual = moshi.adapter(FeatureCollection::class.java).toJson(featureCollection)

    //Then
    Assert.assertEquals(expected, actual)
  }
}