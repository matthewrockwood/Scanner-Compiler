package org.example;

public class Main {
    public static void main(String[] args) {
        String program = """
            var w
            var x
            var y
            initialize w = 5
            initialize x = 10
            initialize y = 15
            compute w = x + y + 4
            if x = y then
            output w
            output x
            endif
            """;

        Parser parser = new Parser();
        parser.synTree = new AbsSynTree();

        parser.parse(program);
        parser.getSynTree().show(); 
    }
}
