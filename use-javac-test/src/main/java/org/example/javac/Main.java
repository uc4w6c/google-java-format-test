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

// パース処理
// com.sun.tools.javac.parser.Tokens がトークン一覧
// JavaTokenizerでファイル内の文字列を取得して一つ一つposしてTokensと一致するものを判断しているっぽい？
// JavacParser.parseCompilationUnitにてパースしているっぽい
// com.sun.tools.javac.parser.Scannerでトークンを総合管理(nextTokenで次のトークンを取得可能)
// 依存関係: JavacParser -> Scanner -> JavaTokenizer -> Tokens

// こっちはスキャナー
// TreeScanner.visitApply
//   scan(tree.meth); // System.out.println("Hello!");
// TreeScanner.visitSelect
//   scan(tree.selected); // System.out
// Flow.visitIdent //

// 出力処理
// JavaCompiler.generate
