# MultithreadedOrderSystem

Java 21 / Maven multi-module application featuring generics, Lombok Builder, JavaFaker, JPMS modules, lambda expressions, method references and multithreaded processing.

Modules:

- `order-processing`: products, generic orders and processing API, with an internal non-exported validation package.
- `order-storage`: concurrent order storage.
- `order-threads`: thread manager and sample application.

Run tests from the root with Maven `clean test`.

Run the demo from the `order-threads` Maven module with the `exec:java` goal after installing the reactor projects (`mvn install` from the root). The demonstration uses the Maven exec plugin's classpath launcher; the JPMS descriptors are compiled and checked by the Java compiler.
