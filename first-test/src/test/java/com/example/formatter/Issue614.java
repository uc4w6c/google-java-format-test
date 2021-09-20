package com.example.formatter;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Issue614 {
    @Test
    void main() throws FormatterException {
        String input = """
                package foo;

                import java.util.Set;
                
                public class FormatTest {
                    private static final Set<String> foo = Set.of(
                            "11111111111111111111111111111111" // foo foo foo foo foo foo foo foo foo foo foo foo foo foo
                    );
                }
                """;
        System.out.println("input:");
        System.out.println(input);
        String output1 = new Formatter().formatSource(input);
        System.out.println("output one round:");
        System.out.println(output1);

        String output2 = new Formatter().formatSource(output1);
        System.out.println("output two round:");
        System.out.println(output2);

        assertEquals(output1, output2);
    }

    // FormatterTestにあったもの
    @Test
    void 用意されていたテスト_wrapLineComment() throws FormatterException {
        String input = """
                public class T {
                  public static void main(String[] args) { // one long incredibly  unbroken sentence moving from topic to topic so that no-one had a  chance to interrupt;
                  }
                }
                """;

        System.out.println("input:");
        System.out.println(input);
        String output1 = new Formatter().formatSource(input);
        System.out.println("output one round:");
        System.out.println(output1);
    }

    /*
    こっちの方が難しそう・・・

    1回目
      Set.of(
          "11111111111111111111111111111111" // foo foo foo foo foo foo foo foo foo foo foo foo foo
                                             // foo
          );

    2回目
      Set.of(
          "11111111111111111111111111111111" // foo foo foo foo foo foo foo foo foo foo foo foo foo
          // foo
          );

     */
}
