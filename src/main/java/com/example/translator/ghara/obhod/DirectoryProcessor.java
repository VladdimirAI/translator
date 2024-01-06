package com.example.translator.ghara.obhod;

import com.example.translator.ghara.JavaMethodRenamer;

import java.io.File;
import java.io.IOException;

public class DirectoryProcessor {

    private JavaMethodRenamer renamer;

    public DirectoryProcessor(JavaMethodRenamer renamer) {
        this.renamer = renamer;
    }

    public void processDirectory(File directory) throws Exception {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    processDirectory(file);
                } else if (file.getName().endsWith(".java")) {
                    renamer.renameMethodsInFile(file.getAbsolutePath(), file.getParent());
                }
            }
        }
    }
}
