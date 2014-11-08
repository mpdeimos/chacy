Chacy [![Build Status](https://travis-ci.org/mpdeimos/chacy.svg?branch=master)](https://travis-ci.org/mpdeimos/chacy)
=====

Chacy is a Java Compiler Plugin that generates C# sources from Java. There is also experimental support for Vala.

At this time the implementation is still heavy work in progress and is not suited for production use at all.

How to use
----

Chacy has to be used as annotation processor at compile time.

* Compile your sources with Java 8 (no wories, you can still be binary compatible to Java 6)
* Add `com.mpdeimos.chacy.ifc.jar` to the classpath
* Add the following annotation processor `-processorpath com.mpdeimos.chacy.all.jar`
* All types annotated with `@Chacy.Type` will be converted

Contribution
----

Any contributions are welcome. Feel free to open a pull request.
