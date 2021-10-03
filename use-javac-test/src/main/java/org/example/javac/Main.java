package org.example.javac;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
  public static void main(String[] args) {
    String userDir = System.getProperty("user.dir");
    Path path = Paths.get(userDir);
    Path compileFilePath = path.resolve("src/main/java/org/example/javac/test/Main.java");
    String[] testArgs = {compileFilePath.toString()};
    com.sun.tools.javac.main.Main compiler =
        new com.sun.tools.javac.main.Main("javac");
    int exitCode = compiler.compile(testArgs).exitCode;
    System.out.println(exitCode);
  }
}
// --add-exports jdk.compiler/com.sun.tools.javac.main=ALL-UNNAMED
