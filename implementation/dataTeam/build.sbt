
libraryDependencies += "org.apache.spark" % "spark-core_2.10" % "1.4.1" % "provided"
libraryDependencies += "org.apache.spark" % "spark-streaming_2.10" % "1.4.1" % "provided"
libraryDependencies += "org.apache.spark" % "spark-streaming-twitter_2.10" % "1.4.1"

// to avoid merge conflict
mergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class")
=> MergeStrategy.first

  case x => (mergeStrategy in assembly).value(x)
}

// to avoid to include all the dependencies in the same .jar
//assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false, includeDependency = false)