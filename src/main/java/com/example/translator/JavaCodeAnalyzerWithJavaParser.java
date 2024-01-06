package com.example.translator;//package com.example.translator;



import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class JavaCodeAnalyzerWithJavaParser {

    private Set<String> packages = new HashSet<>();
    private Set<String> classes = new HashSet<>();
    private Set<String> methods = new HashSet<>();
    private Set<String> variables = new HashSet<>();
    private Set<String> comments = new HashSet<>();
    private Set<String> todos = new HashSet<>();




    public void analyzeFile(String filePath) throws Exception {
        FileInputStream in = new FileInputStream(filePath);


//        String content1 = new String(in.readAllBytes());
//        System.out.println(content1);

        // Конфигурация парсера
        ParserConfiguration configuration = new ParserConfiguration();
        JavaParser parser = new JavaParser(configuration);

        CompilationUnit cu = null;
        try {
            cu = parser.parse(in).getResult().orElseThrow(() -> new IllegalArgumentException("Не удалось проанализировать файл."));
            // ... остальная часть кода ...
        } catch (Exception e) {
            e.printStackTrace();
        }


        // Визитер для классов
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(ClassOrInterfaceDeclaration n, Void arg) {
                classes.add(n.getNameAsString());
                super.visit(n, arg);
            }
        }, null);

        // Визитер для методов
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(MethodDeclaration n, Void arg) {
                methods.add(n.getNameAsString());
                super.visit(n, arg);
            }
        }, null);

        // Визитер для переменных
        cu.accept(new VoidVisitorAdapter<Void>() {
            @Override
            public void visit(VariableDeclarationExpr n, Void arg) {
                n.getVariables().forEach(var -> {
                    if (var.getNameAsString().length() > 1) { // Исключение однобуквенных переменных
//                        System.out.println(var + "_____________________");
                        variables.add(var.getNameAsString());
                    }
                });
                super.visit(n, arg);
            }
        }, null);

        // Визитер для комментариев
        cu.getAllComments().forEach(comment -> {
            String content = comment.getContent();
            if (content.contains("TODO")) {
                todos.add(content);
            } else {
                comments.add(content);
            }
        });


        // Если в файле объявлен пакет, добавляем его
        cu.getPackageDeclaration().ifPresent(pd -> packages.add(pd.getNameAsString()));


        System.out.println("Packages found: " + packages.size());
        System.out.println("Classes found: " + classes.size());
        System.out.println("Methods found: " + methods.size());
        System.out.println("Variables found: " + variables.size());
        System.out.println("Comments found: " + comments.size());
        System.out.println("TODOs found: " + todos.size());


        // Запись результатов в файлы
        writeSetToFile(packages, "packages.txt");
        writeSetToFile(classes, "classes.txt");
        writeSetToFile(methods, "methods.txt");
        writeSetToFile(variables, "variables.txt");
        writeSetToFile(comments, "comments.txt");
        writeSetToFile(todos, "todos.txt");

        in.close();
    }

    private void writeSetToFile(Set<String> set, String fileName) throws Exception {
        Files.write(Paths.get(fileName), set);
    }

    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.out.println("Использование: JavaCodeAnalyzerWithJavaParser <файл для анализа>");
//            return;
//        }
        String pathToFile = "C:\\MYTRANSLATOR\\translator\\src\\main\\java\\com\\example\\translator\\JavaFileReader.java";

        new JavaCodeAnalyzerWithJavaParser().analyzeFile(pathToFile);
    }
}

//
//import com.github.javaparser.JavaParser;
//import com.github.javaparser.ParserConfiguration;
//import com.github.javaparser.StaticJavaParser;
//import com.github.javaparser.ast.CompilationUnit;
//import com.github.javaparser.ast.body.*;
//import com.github.javaparser.ast.expr.VariableDeclarationExpr;
//import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
//
//import java.io.FileInputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.HashSet;
//import java.util.Set;
//
//public class JavaCodeAnalyzerWithJavaParser {
//
//    private Set<String> packages = new HashSet<>();
//    private Set<String> classes = new HashSet<>();
//    private Set<String> methods = new HashSet<>();
//    private Set<String> variables = new HashSet<>();
//
//    public void analyzeFile(String filePath) throws Exception {
//        FileInputStream in = new FileInputStream(filePath);
//
//        // Configure the parser
//        ParserConfiguration configuration = new ParserConfiguration();
//        JavaParser parser = new JavaParser(configuration);
//
//        // Parse the file
//        CompilationUnit cu = parser.parse(in).getResult().orElseThrow(() -> new IllegalArgumentException("Unable to parse the file."));
//
//        // Visit class names
//        cu.accept(new VoidVisitorAdapter<Void>() {
//            @Override
//            public void visit(ClassOrInterfaceDeclaration n, Void arg) {
//                classes.add(n.getNameAsString());
//                super.visit(n, arg);
//            }
//        }, null);
//
//        // Visit method names
//        cu.accept(new VoidVisitorAdapter<Void>() {
//            @Override
//            public void visit(MethodDeclaration n, Void arg) {
//                methods.add(n.getNameAsString());
//                super.visit(n, arg);
//            }
//        }, null);
//
//        // Visit variable names
//        cu.accept(new VoidVisitorAdapter<Void>() {
//            @Override
//            public void visit(VariableDeclarationExpr n, Void arg) {
//                n.getVariables().forEach(var -> {
//                    if (var.getNameAsString().length() > 1) { // Exclude single-letter variables
//                        variables.add(var.getNameAsString());
//                    }
//                });
//                super.visit(n, arg);
//            }
//        }, null);
//
//        // If the file has a package declaration, add it
//        cu.getPackageDeclaration().ifPresent(pd -> packages.add(pd.getNameAsString()));
//
//        // Write results to files
//        writeSetToFile(packages, "packages.txt");
//        writeSetToFile(classes, "classes.txt");
//        writeSetToFile(methods, "methods.txt");
//        writeSetToFile(variables, "variables.txt");
//
//        in.close();
//    }
//
//    private void writeSetToFile(Set<String> set, String fileName) throws Exception {
//        Files.write(Paths.get(fileName), set);
//    }
//
//    public static void main(String[] args) throws Exception {
//        if (args.length != 1) {
//            System.out.println("Usage: JavaCodeAnalyzerWithJavaParser <file-to-analyze>");
//            return;
//        }
//        new JavaCodeAnalyzerWithJavaParser().analyzeFile(args[0]);
//    }
//}