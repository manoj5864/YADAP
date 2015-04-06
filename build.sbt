import play.PlayJava

name := """YADAP"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaCore,
  javaJdbc,
  javaEbean,
  cache,
  javaWs,
//Database
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.mongodb" % "mongo-java-driver" % "2.12.2",
  "org.jongo" % "jongo" % "1.0",
//Autherization
  "be.objectify" %% "deadbolt-java" % "2.3.1",
  "com.feth" % "play-authenticate_2.11" % "0.6.8",
//Frontend
  "org.webjars" % "jquery" % "2.1.3",
  "org.webjars" % "jquery-ui" % "1.11.2",
  "org.webjars" % "bootstrap" % "3.3.2",
  "org.webjars" % "angularjs" % "1.3.8",
  "org.webjars" % "requirejs" % "2.1.15",
  "org.webjars" % "angular-datatables" % "0.3.0",
  "org.webjars" % "datatables" % "1.10.4",
  "org.webjars" % "datatables-bootstrap" % "2-20120202-2",
  "com.wordnik" %% "swagger-play2" % "1.3.12" exclude("org.reflections", "reflections"),
  "org.reflections" % "reflections" % "0.9.8" notTransitive ()
)

resolvers ++= Seq(
  Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
  Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
  Resolver.url("sbt-plugin-releases", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
  "play-easymail (release)" at "http://joscha.github.io/play-easymail/repo/releases/",
  "play-easymail (snapshot)" at "http://joscha.github.io/play-easymail/repo/snapshots/",
  "play-authenticate (release)" at "http://joscha.github.io/play-authenticate/repo/releases/",
  "play-authenticate (snapshot)" at "http://joscha.github.io/play-authenticate/repo/snapshots/",
  "Central Repo" at "http://search.maven.org/"
)

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)