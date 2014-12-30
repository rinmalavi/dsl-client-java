import sbt._
import Keys._

import com.typesafe.sbteclipse.plugin.EclipsePlugin._
import net.virtualvoid.sbt.graph.Plugin._

// ----------------------------------------------------------------------------

trait Default {
  val defaultSettings =
    Defaults.defaultSettings ++
    eclipseSettings ++
    graphSettings ++ Seq(
      scalaVersion := "2.11.4"
    , crossPaths := false
    , autoScalaLibrary := false
    , unmanagedSourceDirectories in Compile := Seq((javaSource in Compile).value)
    , EclipseKeys.eclipseOutput := Some(".target")
    , EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE16)
    , EclipseKeys.projectFlavor := EclipseProjectFlavor.Java
    , javacOptions in doc := Seq(
        "-encoding", "UTF-8"
      )
    , javacOptions := Seq(
        "-deprecation"
      , "-Xlint"
      , "-source", "1.6"
      , "-target", "1.6"
      ) ++ (javacOptions in doc).value
    ) ++ (sys.env.get("JDK16_HOME") map { jdk16Home =>
      javacOptions in doc ++= Seq(
        "-bootclasspath"
      , Seq("rt", "jsse")
          map(jdk16Home + "/jre/lib/" + _ + ".jar")
          mkString(java.io.File.pathSeparator)
      )
    })

  def checkByteCode(jar: File): File = {
    val zipis = new java.util.zip.ZipInputStream(new java.io.FileInputStream(jar))

    Stream.continually(zipis.getNextEntry).takeWhile(null !=).filter(_.getName.endsWith(".class")).foreach{
      entry =>
        J2SEVersionCheck(readIS(zipis), J2SEVersion.`6` == _)
      }
    def readIS(is: java.io.InputStream) = Stream.continually(is.read).takeWhile(-1 !=).map(_.toByte).toArray
    jar
  }
}

// ----------------------------------------------------------------------------

trait Dependencies {
  // JodaTime
  val jodaTime = "joda-time" % "joda-time" % "2.5"

  // Json serialization
  val jackson = "com.fasterxml.jackson.core" % "jackson-databind" % "2.4.3"

  // Logging facade
  val slf4j = "org.slf4j" % "slf4j-api" % "1.7.7"

  // Amazon Web Services SDK (S3 type)
  val aws = "com.amazonaws" % "aws-java-sdk" % "1.8.11"

  // Android SDK
  val androidSDK = "com.google.android" % "android" % "4.1.1.4"

  // Testing
  val junit = "junit" % "junit" % "4.11"
  val junitInterface = "com.novocode" % "junit-interface" % "0.11"
  val jsonAssert = "org.skyscreamer" % "jsonassert" % "1.2.3"
  val xmlUnit = "xmlunit" % "xmlunit" % "1.5"
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.2"
}

// ----------------------------------------------------------------------------

object NGSBuild extends Build with Default with Dependencies {
  lazy val core = Project(
    "core"
  , file("core")
  , settings = defaultSettings ++ Seq(
      name := "dsl-client-core"
    , libraryDependencies ++= Seq(
        jodaTime
      )
    , unmanagedSourceDirectories in Test := Nil
    )
  )

  lazy val http = Project(
    "http"
  , file("http")
  , settings = defaultSettings ++ Seq(
      name := "dsl-client-http"
    , libraryDependencies ++= Seq(
        slf4j
      , jackson
      , androidSDK % "provided" intransitive()
      , aws % "provided"
      , jsonAssert % "test"
      , junit % "test"
      , junitInterface % "test"
      , logback % "test"
      , xmlUnit % "test"
      )
    , unmanagedSourceDirectories in Test := Seq((javaSource in Test).value)
    , testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
    , EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource
    , createVersionProperties
    , unmanagedJars in Test += baseDirectory.value / "test-lib" / "java-client.jar"
    )
  ) dependsOn(core)

  private val createVersionProperties =
    onLoad := {
      val body = "version=%s\ndate=%s" format (version.value, org.joda.time.LocalDate.now)

      val propertiesPath = (
        baseDirectory.value
        / "src" / "main" / "resources"
        / "com" / "dslplatform" / "client"
        / "dsl-client.properties"
      )

      org.apache.commons.io.FileUtils.writeStringToFile(propertiesPath, body, "UTF-8")
      onLoad.value
    }

  def aggregatedCompile = ScopeFilter(inProjects(core, http), inConfigurations(Compile))

  def aggregatedTest = ScopeFilter(inProjects(core, http), inConfigurations(Test))

  def rootSettings = Seq(
    sources in Compile                        := sources.all(aggregatedCompile).value.flatten,
    unmanagedSources in Compile               := unmanagedSources.all(aggregatedCompile).value.flatten,
    unmanagedSourceDirectories in Compile     := unmanagedSourceDirectories.all(aggregatedCompile).value.flatten,
    unmanagedResourceDirectories in Compile   := unmanagedResourceDirectories.all(aggregatedCompile).value.flatten,
    sources in Test                           := sources.all(aggregatedTest).value.flatten,
    unmanagedSources in Test                  := unmanagedSources.all(aggregatedTest).value.flatten,
    unmanagedSourceDirectories in Test        := unmanagedSourceDirectories.all(aggregatedTest).value.flatten,
    unmanagedResourceDirectories in Test      := unmanagedResourceDirectories.all(aggregatedTest).value.flatten,
    libraryDependencies                       := libraryDependencies.all(aggregatedCompile).value.flatten,
    unmanagedJars in Test                     := unmanagedJars.all(aggregatedTest).value.flatten,
    packageBin in Compile                     := (packageBin in Compile).map{checkByteCode}.value
  )

  lazy val root = (project in file(".")) settings ((defaultSettings ++ rootSettings): _*)
}

object Revenj {

  private val scalaclienttest = "scalaclienttest"
  private val packageName = "com.dslplatform.test"

  private var revenjProcess: Option[Process] = None

  private val credentials = System.getProperty("user.home") + "/.config/dsl-platform/test.credentials"

  private val testLib: Def.Initialize[File] = Def.setting {
    baseDirectory.value / "test-lib" / "java-client.jar"
  }

  private val revenjExe: Def.Initialize[File] = Def.setting {
    baseDirectory.value / "revenj" / "Revenj.Http.exe"
  }

  private val testDll: Def.Initialize[File] = Def.setting {
    baseDirectory.value / "revenj" / "test.dll"
  }

  val testLibTask: Def.Initialize[Task[File]] = Def.taskDyn {
    makeScalaClientTestJar.value
    Def.task { testLib.value }
  }

  private def makeRevenj = Def.task {
    val base = baseDirectory.value
    val basePath = base.getAbsolutePath
    if (!testDll.value.exists()) {
      com.dslplatform.compiler.client.Main.main(Array(
        s"-revenj=${basePath}/revenj/test.dll",
        s"-dependency:revenj=${basePath}/revenj",
        s"-namespace=$packageName",
        s"-db=localhost:5432/$scalaclienttest?user=$scalaclienttest&password=$scalaclienttest",
        s"-dsl=${basePath}/src/test/resources/dsl",
        "-download",
        "-no-colors",
        "-apply",
        "-log",
        s"-properties=$credentials"))
      IO.copyFile(
        base / "test-lib" / "Revenj.Http.exe.config",
        base / "revenj" / "Revenj.Http.exe.config"
      )
    }
  }

  private def makeJavaClientTestJarCall(f: File) =
    com.dslplatform.compiler.client.Main.main(Array(
      s"-java_client=${f.getAbsolutePath}",
      s"-namespace=$packageName",
      "-no-colors",
      "-dsl=http/src/test/resources/dsl",
      "-download",
      "-active-record",
      "-log",
      s"-properties=$credentials"))

  private def makeScalaClientTestJar = Def.task {
    val testLibFile: File = testLib.value
    if (!testLibFile.getParentFile.exists()) testLibFile.getParentFile.mkdir()
    if (!testLibFile.exists()) makeJavaClientTestJarCall(testLibFile)
  }

  private def startRevenj = Def.task {
    scala.util.Try {
      if (sys.props("os.name").toLowerCase(java.util.Locale.ENGLISH).contains("windows")) {
        Seq(revenjExe.value.getPath).run
      }
      else {
        Seq("mono", revenjExe.value.getPath).run
      }
    }
  }

  def setup: Def.Initialize[Task[() => Unit]] = Def.taskDyn {
    makeScalaClientTestJar.value
    makeRevenj.value
    Def.taskDyn {
      revenjProcess = startRevenj.value.toOption
      Def.task { () => () }
    }
  }

  def shutdown: Def.Initialize[Task[() => Unit]] = Def.task {
    () =>
      revenjProcess.map(_.destroy).getOrElse()
  }
}