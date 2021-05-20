package org.javaparser.examples;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Pattern;


public class ModifyingVisitorStarter {

    private static final String FILE_PATH = "src/main/java/org/javaparser/examples/ReversePolishNotation.java";
    private static final Pattern LOOK_AHEAD_THREE = Pattern.compile("(\\d)(?=(\\d{3})+$)");

    public static void main(String[] args) throws FileNotFoundException {
        CompilationUnit cu = StaticJavaParser.parse(new File(FILE_PATH));
        ModifierVisitor<?> numericLiteralVisitor = new IntegerLiteralModifier();
        numericLiteralVisitor.visit(cu, null);
        System.out.println(cu.toString());
    }

    static String formatWithUnderscores(String value) {
        String withoutUnderscores = value.replaceAll("_", "");
        return LOOK_AHEAD_THREE.matcher(withoutUnderscores).replaceAll("$1_");
    }

    private static class IntegerLiteralModifier extends ModifierVisitor<Void> {
        public FieldDeclaration visit(FieldDeclaration fd, Void arg) {
            super.visit(fd, arg);
            fd.getVariables().forEach(
                    v -> v.getInitializer().ifPresent(
                            i -> i.ifIntegerLiteralExpr(
                                    i1 -> v.setInitializer(formatWithUnderscores(i1.getValue())
                                    )
                            )
                    )
            );
            return fd;
        }
    }
}
