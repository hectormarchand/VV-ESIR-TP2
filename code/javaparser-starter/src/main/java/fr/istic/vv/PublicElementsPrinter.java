package fr.istic.vv;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;

import java.util.ArrayList;
import java.util.List;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {
    public static List<FieldWrapper> fieldList = new ArrayList<>();
    public static List<String> methodList = new ArrayList<>();


    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        if(!declaration.isPublic()) return;
        //System.out.println(declaration.getFullyQualifiedName().orElse("[Anonymous]"))

        String cName = declaration.getNameAsString();
        String pName = declaration.findCompilationUnit().get().getPackageDeclaration().get().getNameAsString();

        for(MethodDeclaration method : declaration.getMethods()) {
            if (method.isPublic()) {
                methodList.add(method.getDeclarationAsString(false, false, false));
            }
            method.accept(this, arg);
        }
        for(FieldDeclaration field : declaration.getFields()) {
            if (!field.isPublic()) {
                FieldWrapper f = new FieldWrapper(cName, pName, field.getVariable(0).getNameAsString());
                fieldList.add(f);
            }
        }
        // Printing nested types in the top level
        for(BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration)
                member.accept(this, arg);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if(!declaration.isPublic()) return;
        //System.out.println("  " + declaration.getDeclarationAsString(true, true));
    }
}
