[![Download](https://api.bintray.com/packages/vincent-paing/maven/geoshi/images/download.svg)](https://bintray.com/vincent-paing/maven/geoshi/_latestVersion)
[![Build Status](https://travis-ci.com/vincent-paing/Geoshi.svg?branch=master)](https://travis-ci.com/vincent-paing/Geoshi)


# Geoshi

Geoshi is a GeoJSON Adapter for [Moshi](https://github.com/square/moshi)

## Usage

Initialize your Moshi instance with the following:

```kotlin
val moshi: Moshi = Moshi.Builder()
      .add(GeoshiJsonAdapterFactory())
      //Do stuffs
      .build()
```

You can then start using the data classes provided. Out-of-box, 10 objects are provided

1. FeatureCollection
2. Feature
3. GeometryCollection
4. Position
5. Point
6. LineString
7. Polygon
8. MultiPoint
9. MultiLineString
10. MultiPolygon 

For example, you can convert this following string into Point

```kotlin 
val jsonString = "{\"type\":\"Point\",\"coordinates\":[100.0,0.0]}"

val point =  moshi.adapter(Point::class.java).fromJson(jsonString)

print(point) //Point(coordinates=Position(longitude=100.0, latitude=0.0, altitude=null)
```

## What's left?

This is very alpha release, and still requires a lot of things to be stable. It does some basic validations but it still requires more validations checks according to GeoJSON specification. **PR are welcomed**

## Download

For gradle:

```
repositories {
    jcenter()
}

compile 'com.aungkyawpaing.geoshi:geoshi-adapter:0.0.2'
```

## License

```
Copyright 2019 Aung Kyaw Paing

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
