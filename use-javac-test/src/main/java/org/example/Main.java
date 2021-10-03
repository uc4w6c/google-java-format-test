package org.example;

import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;

import com.sun.source.util.TreePathScanner;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.parser.JavacParser;
import com.sun.tools.javac.parser.ParserFactory;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Options;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Main {
  public static void main(String[] args) throws Exception {
    String input = """
                package com.sun.something;

                import java.util.List;

                public class Test {
                  public List index() {
                    return List.of("aa", "bb");
                  }
                }
                """;

    Context context = new Context();
    JCCompilationUnit unit = parse(context, input);
    JavacTrees trees = JavacTrees.instance(context);

    CustomTreeScanner scanner = new CustomTreeScanner();
    for (var jcTree : unit.getTypeDecls()) {
      // System.out.println(jcTree);
      scanner.scan(jcTree, null);
      for (String declareReference : scanner.declareReferences()) {
        System.out.println(declareReference);
      }
    }

    System.out.println(unit);
    System.out.println(trees);
  }

  private static JCCompilationUnit parse(Context context, String javaInput)
      throws Exception {
    DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
    context.put(DiagnosticListener.class, diagnostics);
    Options.instance(context).put("--enable-preview", "true");
    Options.instance(context).put("allowStringFolding", "false");
    JCCompilationUnit unit;
    JavacFileManager fileManager = new JavacFileManager(context, true, UTF_8);
    try {
      fileManager.setLocation(StandardLocation.PLATFORM_CLASS_PATH, List.of());
    } catch (IOException e) {
      // impossible
      throw new IOError(e);
    }
    SimpleJavaFileObject source =
        new SimpleJavaFileObject(URI.create("source"), JavaFileObject.Kind.SOURCE) {
          @Override
          public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return javaInput;
          }
        };
    Log.instance(context).useSource(source);
    ParserFactory parserFactory = ParserFactory.instance(context);
    JavacParser parser =
        parserFactory.newParser(
            javaInput, /*keepDocComments=*/ true, /*keepEndPos=*/ true, /*keepLineMap=*/ true);
    unit = parser.parseCompilationUnit();
    unit.sourcefile = source;
    return unit;
  }
}
// --add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
