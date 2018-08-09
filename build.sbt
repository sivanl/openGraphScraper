name := """play-java-rest-api-example"""

version := "1.0-SNAPSHOT"

lazy val GatlingTest = config("gatling") extend Test

lazy val root = (project in file(".")).enablePlugins(PlayJava, GatlingPlugin).configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )

scalaVersion := "2.11.11"

libraryDependencies += filters
libraryDependencies += javaJpa

libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
libraryDependencies += "io.dropwizard.metrics" % "metrics-core" % "3.2.1"
libraryDependencies += "com.palominolabs.http" % "url-builder" % "1.1.0"
libraryDependencies += "net.jodah" % "failsafe" % "1.0.3"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.2.5"
libraryDependencies += "com.google.http-client" % "google-http-client-jackson2" % "1.21.0"
libraryDependencies += "com.google.code.gson" % "gson" % "2.6.1"
libraryDependencies += "org.jsoup" % "jsoup" % "1.11.3"
libraryDependencies += "org.json" % "json" % "20090211"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.5" % Test
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.2.5" % Test

PlayKeys.externalizeResources := false

testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
