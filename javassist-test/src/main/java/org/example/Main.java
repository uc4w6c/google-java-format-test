package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Main {
  public static void main(String[] args) throws Exception {
    String userDir = System.getProperty("user.dir");
    Path path = Paths.get(userDir);
    Path compileFilePath = path.resolve("outputs");

    ClassPool cp = ClassPool.getDefault();
    CtClass cc = cp.makeClass("TestNewClass");

    CtMethod mainMethod = CtNewMethod.make("""
            public static void main(String[] args) {
              System.out.println("Hello!");
            }
            """, cc);
    cc.addMethod(mainMethod);

    /*
    CtField f1 = CtField.make("int numerator = 1;", cc);
    cc.addField(f1);
    CtField f2 = CtField.make("int denominator = 1;", cc);
    cc.addField(f2);
     */
    cc.writeFile(compileFilePath.toString());
  }
}
/*
$ java TestNewClass
Hello!
が出力されることを確認出来た
 */
