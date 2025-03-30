package org.example;

import java.io.PushbackReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    enum TYPE {INTDATATYPE}

    public class SymbolTableItem {
        String name;
        TYPE type;
        public SymbolTableItem(String name, TYPE type) {
            this.name = name;
            this.type = type;
        }
    }

    Map<String, SymbolTableItem> lookupTable= new HashMap<>();
    Scanner scanner;
    Scanner.TOKEN token;

    public boolean tokenMatch(Scanner.TOKEN expectedToken) {
        if (this.token == expectedToken) {
            System.out.println("Matched token: " + token + " with buffer: " + scanner.getTokenBufferString());
            getNextToken();
            return true;
        } else {
            throwError(expectedToken.toString());
            return false;
        }
    }

    private void throwError(String expected) {
        System.out.println("Parse Error");
        System.out.println("Expected: " + expected);
        System.out.println("Received: " + token);
        System.out.println("Buffer: " + scanner.getTokenBufferString());
        System.exit(1);
    }

    public boolean parse(String program){
        StringReader sr = new StringReader(program);
        PushbackReader pb = new PushbackReader(sr);
        scanner = new Scanner(pb);
        this.token = scanner.scan();

        parseVariables();
        parseStatements();

        if(tokenMatch(Scanner.TOKEN.SCANEOF)){
            System.out.println("Success");
        }
        else{
            System.out.println("Unmatched EOF");
        }
        return true;
    }

    private void parseVariables() {
        while (token == Scanner.TOKEN.VAR) {
            tokenMatch(Scanner.TOKEN.VAR);
            if (token == Scanner.TOKEN.ID) {
                String varName = scanner.getTokenBufferString();
                if (lookupTable.containsKey(varName)) {
                    System.out.println("Parse Error");
                    System.out.println("Variable already declared: " + varName);
                    System.exit(1);
                } else {
                    lookupTable.put(varName, new SymbolTableItem(varName, TYPE.INTDATATYPE));
                }
                tokenMatch(Scanner.TOKEN.ID);
            } else {
                throwError("ID");
            }
        }
    }

    private void parseStatements() {
        while (token == Scanner.TOKEN.OUTPUT || token == Scanner.TOKEN.INITIALIZE
                || token == Scanner.TOKEN.IF || token == Scanner.TOKEN.COMPUTE) {
            parseStatement();
        }
    }

    private void parseStatement() {
        if (token == Scanner.TOKEN.OUTPUT) {
            tokenMatch(Scanner.TOKEN.OUTPUT);
            tokenMatch(Scanner.TOKEN.ID);
        } else if (token == Scanner.TOKEN.INITIALIZE) {
            tokenMatch(Scanner.TOKEN.INITIALIZE);
            tokenMatch(Scanner.TOKEN.ID);
            tokenMatch(Scanner.TOKEN.EQUALS);
            tokenMatch(Scanner.TOKEN.CONSTINT);
        } else if (token == Scanner.TOKEN.IF) {
            tokenMatch(Scanner.TOKEN.IF);
            tokenMatch(Scanner.TOKEN.ID);
            tokenMatch(Scanner.TOKEN.EQUALS);
            tokenMatch(Scanner.TOKEN.ID);
            tokenMatch(Scanner.TOKEN.THEN);
            parseStatements();
            tokenMatch(Scanner.TOKEN.ENDIF);
        } else if (token == Scanner.TOKEN.COMPUTE) {
            tokenMatch(Scanner.TOKEN.COMPUTE);
            tokenMatch(Scanner.TOKEN.ID);
            tokenMatch(Scanner.TOKEN.EQUALS);
            parseAdd();
        } else {
            throwError("Statement (OUTPUT, INITIALIZE, IF, COMPUTE)");
        }
    }

    private void parseAdd() {
        parseValue();
        parseAddEnd();
    }

    private void parseAddEnd() {
        while (token == Scanner.TOKEN.PLUS) {
            tokenMatch(Scanner.TOKEN.PLUS);
            parseValue();
        }
    }

    private void parseValue() {
        if (token == Scanner.TOKEN.ID || token == Scanner.TOKEN.CONSTINT) {
            tokenMatch(token);
        } else {
            throwError("ID or CONSTINT");
        }
    }

    public void getNextToken(){
        token = scanner.scan();
    }
}
