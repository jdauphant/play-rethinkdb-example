name := """play-rethinkdb-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.4"

resolvers ++= Seq(
  "RethinkScala Repository" at "http://kclay.github.io/releases"
)

libraryDependencies ++= Seq(
  "com.rethinkscala"  %% "core"                 % "0.4.5"
)
