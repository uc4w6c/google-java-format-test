package com.example.formatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.googlejavaformat.java.FormatterException;
import com.google.googlejavaformat.java.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Issue598 {
  @TempDir
  Path tempDir;

  @Test
  void main() throws FormatterException, IOException, Exception {
    String input = """
                package com.sun.something;

                import com.x;
                import com.y;
                import com.z;

                public class MockedLiveServiceExecutionContext {


                }
                """;

    Path path = tempDir.resolve("Foo.java");
    Files.write(path, input.getBytes(UTF_8));

    StringWriter out = new StringWriter();
    StringWriter err = new StringWriter();
    Main main = new Main(new PrintWriter(out, true), new PrintWriter(err, true), System.in);
    String[] args = new String[] {"-i", path.toString()};
    main.format(args);

    String output = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output:");
    System.out.println(output);
  }

  @Test
  void 前コメントを2回() throws FormatterException, IOException, Exception {
    String input = """
                package com.sun.something;
                // test
                import com.x;
                import com.y;
                import com.z;

                public class MockedLiveServiceExecutionContext {
                }
                """;

    Path path = tempDir.resolve("Foo.java");
    Files.write(path, input.getBytes(UTF_8));

    StringWriter out = new StringWriter();
    StringWriter err = new StringWriter();
    Main main = new Main(new PrintWriter(out, true), new PrintWriter(err, true), System.in);
    String[] args = new String[] {"-i", path.toString()};
    main.format(args);

    String output = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output:");
    System.out.println(output);

    main.format(args);
    String output2 = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output2:");
    System.out.println(output2);
  }

  @Test
  void 後ろコメントを2回() throws FormatterException, IOException, Exception {
    String input = """
                package com.sun.something;

                import com.x;
                import com.y;
                import com.z;
                // test

                public class MockedLiveServiceExecutionContext {
                }
                """;

    Path path = tempDir.resolve("Foo.java");
    Files.write(path, input.getBytes(UTF_8));

    StringWriter out = new StringWriter();
    StringWriter err = new StringWriter();
    Main main = new Main(new PrintWriter(out, true), new PrintWriter(err, true), System.in);
    String[] args = new String[] {"-i", path.toString()};
    main.format(args);

    String output = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output:");
    System.out.println(output);

    main.format(args);
    String output2 = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output2:");
    System.out.println(output2);
  }

  @Test
  void 前後コメントを2回() throws FormatterException, IOException, Exception {
    String input = """
                package com.sun.something;

                // test1
                import com.x;
                import com.y;
                import com.z;
                // test2

                public class MockedLiveServiceExecutionContext {
                }
                """;

    Path path = tempDir.resolve("Foo.java");
    Files.write(path, input.getBytes(UTF_8));

    StringWriter out = new StringWriter();
    StringWriter err = new StringWriter();
    Main main = new Main(new PrintWriter(out, true), new PrintWriter(err, true), System.in);
    String[] args = new String[] {"-i", path.toString()};
    main.format(args);

    String output = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output:");
    System.out.println(output);

    main.format(args);
    String output2 = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output2:");
    System.out.println(output2);
  }


  @Test
  void コメントなしを2回() throws FormatterException, IOException, Exception {
    String input = """
                package com.sun.something;

                import com.x;
                import com.y;
                import com.z;

                public class MockedLiveServiceExecutionContext {
                }
                """;

    Path path = tempDir.resolve("Foo.java");
    Files.write(path, input.getBytes(UTF_8));

    StringWriter out = new StringWriter();
    StringWriter err = new StringWriter();
    Main main = new Main(new PrintWriter(out, true), new PrintWriter(err, true), System.in);
    String[] args = new String[] {"-i", path.toString()};
    main.format(args);

    String output = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output:");
    System.out.println(output);

    main.format(args);
    String output2 = new String(Files.readAllBytes(path), UTF_8);
    System.out.println("output2:");
    System.out.println(output2);
  }
}
