name := """play-rethinkdb-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "RethinkScala Repository" at "http://dl.bintray.com/jdauphant/maven/"
)

libraryDependencies ++= Seq(
  "com.rethinkscala"  %% "core"                 % "0.4.7-SNAPSHOT"
)
