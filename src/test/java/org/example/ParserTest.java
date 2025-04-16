package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testValidProgram() {
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

        boolean result;
        try {
            parser.parse(program);
            result = true;
            parser.getSynTree().show();
        } catch (Exception e) {
            result = false;
            parser.getSynTree().show();
        }

        assertTrue(result, "Valid");
    }
}
