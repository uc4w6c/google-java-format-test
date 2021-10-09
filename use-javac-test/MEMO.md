Intellij Run実行時のjvmオプション

```
--add-exports jdk.compiler/com.sun.tools.javac.api=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.file=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.parser=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.tree=ALL-UNNAMED --add-exports jdk.compiler/com.sun.tools.javac.util=ALL-UNNAMED
```

## バイトコード

バイトコードの確認
```
$ hexdump -C src/main/java/org/example/javac/test/Main.class
```

参考
https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html

### constant_poolの読み方

cp_info {
  u1 tag;
  u1 info[];
}

tagの内容によってinfoの参照箇所が異なる。

tagの内容
https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.4-140

例えば、tagが10ならCONSTANT_Methodrefなので、
4.4.2 CONSTANT_Fieldref_info、CONSTANT_Methodref_info、およびCONSTANT_InterfaceMethodref_infoを参照

CONSTANT_Methodref_info {
  u1 tag;
  u2 class_index;
  u2 name_and_type_index;
}

javapと比較しながら確認すると少しわかる。
class_index, name_and_type_indexはclassとname_and_typeのコンスタントプールの場所が格納されている。(indexなので何番目のコンスタントプールなのか)

### CONSTANT_Utf8_info
CONSTANT_Utf8_info {
  u1 tag;
  u2 length;
  u1 bytes[length];
}

01
00 10
6a6176612f6c616e672f4f626a656374
上の6a〜74をutf-8で16進数から変換すると java/lang/Objectになる

### CONSTANT_Fieldref_info
Fieldref に紐づく以下にはクラスとフィールド情報が設定される。
class_index -> CONSTANT_Class
name_and_type_index -> CONSTANT_NameAndType
  CONSTANT_NameAndType
    -> CONSTANT_Utf8 メソッド名
    -> CONSTANT_Utf8 戻りのクラス名


### attribute_info

attribute_name_indexに応じて
info の参照するものが異なる

コンスタントプールに対象のもおんがある。
COdeなら Code_attributeをみる


https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-4.html#jvms-4.7.3

Code Attribute の code[code_length]の仕様はこっち
https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-7.html

---
というわけでバイトコード操作するのはなかなか厳しそう。
デモとして.classファイルの中をちょっと変えて出力変わるのを確かめるのと、
バイトコード操作ライブラリ(javassistなど)で書き換えるみたいなのを試した方が良いかも。


