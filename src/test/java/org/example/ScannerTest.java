package org.example;

import org.junit.jupiter.api.Test;

import java.io.PushbackReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

class ScannerTest {

    @Test
    void test1(){
        PushbackReader pb = new PushbackReader(new StringReader("var score123"));
        Scanner scanner = new Scanner(pb);

        assertEquals(Scanner.TOKEN.VAR, scanner.scan());
        assertEquals(Scanner.TOKEN.ID, scanner.scan());
        assertEquals("score123", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());
    }
    @Test
    void test2() {
        PushbackReader pb = new PushbackReader(new StringReader("initialize salary = 500"));
        Scanner scanner = new Scanner(pb);

        assertEquals(Scanner.TOKEN.INITIALIZE, scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("salary", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.EQUALS,scanner.scan());
        assertEquals(Scanner.TOKEN.CONSTINT, scanner.scan());
        assertEquals("500", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());

    }
    @Test
    void test3() {
        PushbackReader pb = new PushbackReader(new StringReader("compute newsalary = originalsalary + raise"));
        Scanner scanner = new Scanner(pb);

        assertEquals(Scanner.TOKEN.COMPUTE, scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("newsalary", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.EQUALS,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("originalsalary", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.PLUS, scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("raise", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());


    }
    @Test
    void test4() {
        PushbackReader pb = new PushbackReader(new StringReader("output salary"));
        Scanner scanner = new Scanner(pb);

        assertEquals(Scanner.TOKEN.OUTPUT,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("salary", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());
    }
    @Test
    void test5() {
        PushbackReader pb = new PushbackReader(new StringReader("if x = y then endif"));
        Scanner scanner = new Scanner(pb);

        assertEquals(Scanner.TOKEN.IF,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("x", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.EQUALS,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("y", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.THEN,scanner.scan());
        assertEquals(Scanner.TOKEN.ENDIF,scanner.scan());
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());

    }
    @Test
    void test6() {
        PushbackReader pb = new PushbackReader(new StringReader("if x = y then output x endif"));
        Scanner scanner = new Scanner(pb);

        assertEquals(Scanner.TOKEN.IF,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("x", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.EQUALS,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("y", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.THEN,scanner.scan());
        assertEquals(Scanner.TOKEN.OUTPUT,scanner.scan());
        assertEquals(Scanner.TOKEN.ID,scanner.scan());
        assertEquals("x", scanner.getTokenBufferString());
        assertEquals(Scanner.TOKEN.ENDIF,scanner.scan());
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());



    }
    @Test
    void test7() {
        PushbackReader pb = new PushbackReader(new StringReader(""));
        Scanner scanner = new Scanner(pb);
        assertEquals(Scanner.TOKEN.SCANEOF, scanner.scan());


    }


}