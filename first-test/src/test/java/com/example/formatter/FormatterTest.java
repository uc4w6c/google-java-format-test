package com.example.formatter;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;
import org.junit.jupiter.api.Test;

public class FormatterTest {
    @Test
    void main() throws FormatterException {
        String input = """
                package com.example;



                public class Main {
                    public static void main(String[] args) {
                        System.out.println("Hello");
                    }
                } 
                """;
        String output = new Formatter().formatSource(input);
        System.out.println(output);
    }
}
