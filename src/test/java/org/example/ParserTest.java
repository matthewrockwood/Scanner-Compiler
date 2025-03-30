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
            var z
            initialize w = 5
            initialize x = 10
            initialize y = 15
            initialize z = 20
            compute w = x + y + z
            if x = y then
                output w
                output x
            endif
            output y
            output z
            """;

        Parser parser = new Parser();
        boolean result = parser.parse(program);
        assertTrue(result, "Valid Program");
    }

    @Test
    public void testInvalidProgram() {
        String program = """
            var w
            var x
            initialize w = 5
            initialize x = 10
            output w
            output x
            if w x then
                output w
            endif
            """;

        Parser parser = new Parser();
        boolean result = parser.parse(program);
        assertFalse(result, "Invalid Program");
    }
}
