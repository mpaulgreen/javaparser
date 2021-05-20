package org.javaparser.examples;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class VoidVisitorStarter {
    private static final String FILE_PATH = "src/main/java/org/javaparser/examples/ReversePolishNotation.java";

    public static void main(String[] args) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        VoidVisitor<List<String>> methodNameVisior = new MethodNamePrinter();
        List<String> methodNames = new ArrayList<>();
        methodNameVisior.visit(cu, methodNames);
        methodNames.forEach(methodName -> System.out.println("Method Collected    " + methodName));
    }

    private static class MethodNamePrinter extends VoidVisitorAdapter<List<String>> {

        @Override
        public void visit(MethodDeclaration md, List<String> collector) {
            super.visit(md, collector);
            collector.add(md.getNameAsString());
        }
    }
}
