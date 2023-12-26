val scala3Version = "3.3.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "discord-server-bridge",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "com.discord4j" % "discord4j-core" % "3.2.0",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
      "ch.qos.logback" % "logback-classic" % "1.2.10"
    )
  )
