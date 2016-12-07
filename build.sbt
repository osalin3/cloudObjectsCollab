name := "denis_roldan_finalproject"

version := "1.0"


scalaVersion := "2.11.8"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += "maven repo" at "https://repo.spongepowered.org/maven/"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-jackson-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.11",
  "com.typesafe.akka" %% "akka-http-xml-experimental" % "2.4.11",
  "com.typesafe.play" %% "play-json" % "2.3.4",
  "org.scalactic" %% "scalactic" % "3.0.0",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "org.eclipse.jdt" % "org.eclipse.jdt.core" % "3.10.0",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "mysql" % "mysql-connector-java" % "6.0.5"
)
//libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % "2.4.14",
//"com.typesafe.akka" %% "akka-agent" % "2.4.14",
//"com.typesafe.akka" %% "akka-camel" % "2.4.14",
//"com.typesafe.akka" %% "akka-cluster" % "2.4.14",
//"com.typesafe.akka" %% "akka-cluster-metrics" % "2.4.14",
//"com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.14",
//"com.typesafe.akka" %% "akka-cluster-tools" % "2.4.14",
//"com.typesafe.akka" %% "akka-contrib" % "2.4.14",
//"com.typesafe.akka" %% "akka-multi-node-testkit" % "2.4.14",
//"com.typesafe.akka" %% "akka-osgi" % "2.4.14",
//"com.typesafe.akka" %% "akka-persistence" % "2.4.14",
//"com.typesafe.akka" %% "akka-persistence-tck" % "2.4.14",
//"com.typesafe.akka" %% "akka-remote" % "2.4.14",
//"com.typesafe.akka" %% "akka-slf4j" % "2.4.14",
//"com.typesafe.akka" %% "akka-stream" % "2.4.14",
//"com.typesafe.akka" %% "akka-stream-testkit" % "2.4.14",
//"com.typesafe.akka" %% "akka-testkit" % "2.4.14",
//"com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.14",
//"com.typesafe.akka" %% "akka-typed-experimental" % "2.4.14",
//"com.typesafe.akka" %% "akka-persistence-query-experimental" % "2.4.14",
//  "com.typesafe.akka" %% "akka-http-core" % "10.0.0",
//"com.typesafe.akka" %% "akka-http" % "10.0.0",
//"com.typesafe.akka" %% "akka-http-testkit" % "10.0.0",
//"com.typesafe.akka" %% "akka-http-spray-json" % "10.0.0",
//"com.typesafe.akka" %% "akka-http-jackson" % "10.0.0",
//"com.typesafe.akka" %% "akka-http-xml" % "10.0.0",
//  "com.typesafe.play" %% "play-json" % "2.3.4"
//)