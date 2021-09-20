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
    void 使われていないimportが1つの場合() throws FormatterException, IOException, Exception {
        String input = """
                package com.sun.something;

                import com.x;

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
    void 文字チェック() {
        String input = """
                package com.sun.something;

                import com.x;
                import com.y;
                import com.z;

                public class MockedLiveServiceExecutionContext {


                }
                """;
        StringBuilder sb = new StringBuilder(input);
        System.out.println("input:");
        System.out.println(sb.toString());

        System.out.println("check:");

        int offset = 0;
        sb.replace(28, 42, "");

        offset += "".length() - (42 - 28);
        sb.replace(offset + 42, offset + 56, "");

        offset += "".length() - (56 - 42);
        sb.replace(offset + 56, offset + 70, "");
        System.out.println(sb.toString());
    }
    // MEMO
    /*
    RemoveUnusedImports.removeUnusedImports が怪しい

    Range: 28..42, 42..56, 56..70が ""に置き換わる処理となる。

    結論: 置き換え処理自体は問題ないけど、全てが使われていないときに importの前後の空白を残すためそのまま残ってしまう。

    com.google.googlejavaformat.java.Main formatFiles
    FormatFileCallable fixImports
    RemoveUnusedImports.removeUnusedImports
    UnusedImportScanner.scan
    が処理している部分
     */
}
