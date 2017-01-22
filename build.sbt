import Dependencies._

libraryDependencies ++= Seq(
    "net.debasishg" %% "redisclient" % "3.3"
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.molasses",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Molasses",
    libraryDependencies += scalaTest % Test
  )
// We shouldn't be running tests in parallel because of tests are hitting the same things
parallelExecution in Test := false