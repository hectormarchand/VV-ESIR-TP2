package fr.istic.vv;

import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.err.println("Should provide the path to the source code");
            System.exit(1);
        }

        File file = new File(args[0]);
        if(!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a path to an existing readable directory");
            System.exit(2);
        }

        SourceRoot root = new SourceRoot(file.toPath());
        PublicElementsPrinter printer = new PublicElementsPrinter();
        root.parse("", (localPath, absolutePath, result) -> {
            result.ifSuccessful(unit -> unit.accept(printer, null));
            return SourceRoot.Callback.Result.DONT_SAVE;
        });

        List<FieldWrapper> fieldList = PublicElementsPrinter.fieldList;
        List<String> methodList = PublicElementsPrinter.methodList;

        // delete everything in file output.txt
        try {
            new FileWriter(System.getProperty("user.dir") + "/code/javaparser-starter/src/main/java/fr/istic/vv/output.txt", false).close();
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        for (FieldWrapper f : fieldList) {
            String search = "get" + Character.toString(f.getFieldName().charAt(0)).toUpperCase() + f.getFieldName().substring(1);
            boolean found = false;

            for (String method : methodList) {
                if (method.contains(search)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                try {
                    FileWriter myWriter = new FileWriter(System.getProperty("user.dir") + "/code/javaparser-starter/src/main/java/fr/istic/vv/output.txt", true);
                    myWriter.append("NO GETTER : " + f.getFieldName() + "   IN CLASS " + f.getClassName() + "   IN PACKAGE " + f.getPackageName() + "\n");
                    myWriter.close();
                }catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }

        }
    }
}
