package com.example.translator.ghara;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JavaMethodRenamer {

    private Map<String, String> methodMap = new HashMap<>();

    public void loadMethodMappings(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("---");
                if (parts.length == 2) {
                    methodMap.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
    }

    public void renameMethodsInFile(String javaFilePath, String outputDir) throws Exception {
        JavaParser parser = new JavaParser();
        CompilationUnit cu = parser.parse(new File(javaFilePath)).getResult().orElseThrow(() -> new RuntimeException("Parsing failed"));

        cu.accept(new ModifierVisitor<Void>() {
            @Override
            public MethodDeclaration visit(MethodDeclaration md, Void arg) {
                super.visit(md, arg);
                String methodName = md.getNameAsString();
                if (methodMap.containsKey(methodName)) {
                    md.setName(methodMap.get(methodName));
                }
                return md;
            }
        }, null);

        Path outputPath = Paths.get(outputDir, new File(javaFilePath).getName());
        Files.write(outputPath, cu.toString().getBytes());
    }

    public static void main(String[] args) throws Exception {
        JavaMethodRenamer renamer = new JavaMethodRenamer();
        renamer.loadMethodMappings("methods.txt");
        renamer.renameMethodsInFile("C:\\JavaFileReader.java", "C:\\22");
    }
}
