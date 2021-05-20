package org.javaparser.examples;

public class Main {

    public static void main(String[] args) {
        ReversePolishNotation rpn = new ReversePolishNotation();
        System.out.println(rpn.calc("3 4 *"));
    }
}