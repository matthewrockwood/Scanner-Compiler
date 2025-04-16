package org.example;

import java.io.PushbackReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    public AbsSynTree getSynTree() {
        return synTree;
    }

    public AbsSynTree synTree;

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

    public AbsSynTree.NodeProgram parse(String program){
        StringReader sr = new StringReader(program);
        PushbackReader pb = new PushbackReader(sr);
        scanner = new Scanner(pb);
        this.token = scanner.scan();

       AbsSynTree.NodeVars vars = parseVariables();
        AbsSynTree.NodeStmts stmts = parseStatements();

        AbsSynTree.NodeProgram root = synTree.new NodeProgram(vars,stmts);

        synTree.setRoot(root);

        if(tokenMatch(Scanner.TOKEN.SCANEOF)){
            System.out.println("Success");
        }
        else{
            System.out.println("Unmatched EOF");
        }
        return synTree.getRoot();
    }

    private AbsSynTree.NodeVars parseVariables() {
        AbsSynTree.NodeVars ans = synTree.new NodeVars();
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

                AbsSynTree.NodeID nodeID = synTree.new NodeID();
                nodeID.name = varName;
                ans.ids.add(nodeID);
                tokenMatch(Scanner.TOKEN.ID);
            } else {
                throwError("ID");
            }

        }
        return ans;
    }

    private AbsSynTree.NodeStmts parseStatements() {
        AbsSynTree.NodeStmts stmts = synTree.new NodeStmts();
        while (token == Scanner.TOKEN.OUTPUT || token == Scanner.TOKEN.INITIALIZE
                || token == Scanner.TOKEN.IF || token == Scanner.TOKEN.COMPUTE) {
            AbsSynTree.NodeStmt stmt = parseStatement();
            stmts.stmtList.add(stmt);
        }
        return stmts;
    }

    private AbsSynTree.NodeStmt parseStatement() {

        if (token == Scanner.TOKEN.OUTPUT) {
            tokenMatch(Scanner.TOKEN.OUTPUT);

            String varName = scanner.getTokenBufferString();
            AbsSynTree.NodeID ID = synTree.new NodeID();
            ID.name = varName;

            tokenMatch(Scanner.TOKEN.ID);

            return synTree.new NodeOutput(ID);

        } else if (token == Scanner.TOKEN.INITIALIZE) {
            tokenMatch(Scanner.TOKEN.INITIALIZE);

            String varName = scanner.getTokenBufferString();
            AbsSynTree.NodeID ID = synTree.new NodeID();
            ID.name = varName;

            tokenMatch(Scanner.TOKEN.ID);
            tokenMatch(Scanner.TOKEN.EQUALS);

            int constNum = Integer.parseInt(scanner.getTokenBufferString());
            AbsSynTree.NodeConstInt constNode = synTree.new NodeConstInt();
            constNode.x = constNum;

            tokenMatch(Scanner.TOKEN.CONSTINT);

            return synTree.new NodeInitialize(ID,constNode);


        } else if (token == Scanner.TOKEN.IF) {
            tokenMatch(Scanner.TOKEN.IF);

            // Get LHS ID
            String varName1 = scanner.getTokenBufferString();
            AbsSynTree.NodeID ID1 = synTree.new NodeID();
            ID1.name = varName1;
            tokenMatch(Scanner.TOKEN.ID);

            tokenMatch(Scanner.TOKEN.EQUALS);

            // Get RHS ID
            String varName2 = scanner.getTokenBufferString();
            AbsSynTree.NodeID ID2 = synTree.new NodeID();
            ID2.name = varName2;
            tokenMatch(Scanner.TOKEN.ID);

            tokenMatch(Scanner.TOKEN.THEN);
            AbsSynTree.NodeStmts iftrue = parseStatements();
            tokenMatch(Scanner.TOKEN.ENDIF);

            return synTree.new Nodeif(ID1, ID2, iftrue);
        }
        else if (token == Scanner.TOKEN.COMPUTE) {
            tokenMatch(Scanner.TOKEN.COMPUTE);

            String varName = scanner.getTokenBufferString();
            AbsSynTree.NodeID ID = synTree.new NodeID();
            ID.name = varName;

            tokenMatch(Scanner.TOKEN.ID);
            tokenMatch(Scanner.TOKEN.EQUALS);
            AbsSynTree.NodeExpr compute = parseAdd();

            return synTree.new NodeCompute(ID,compute);
        } else {
            throwError("Statement (OUTPUT, INITIALIZE, IF, COMPUTE)");
            return null;
        }

    }

    private AbsSynTree.NodeExpr parseAdd() {
        AbsSynTree.NodeExpr lhs = parseValue();


        return parseAddEnd(lhs);
    }

    private AbsSynTree.NodeExpr parseAddEnd(AbsSynTree.NodeExpr lhs) {

        while (token == Scanner.TOKEN.PLUS) {
            tokenMatch(Scanner.TOKEN.PLUS);
            AbsSynTree.NodeExpr rhs = parseValue();
            lhs = synTree.new NodePlus(lhs,rhs);
        }
        return lhs;
    }

    private AbsSynTree.NodeExpr parseValue() {
        if (token == Scanner.TOKEN.ID) {
            String varName = scanner.getTokenBufferString();
            AbsSynTree.NodeID ID = synTree.new NodeID();
            ID.name = varName;

            tokenMatch(token);

            return ID;

        } else if (token == Scanner.TOKEN.CONSTINT) {
            int value = Integer.parseInt(scanner.getTokenBufferString());
            AbsSynTree.NodeConstInt ans = synTree.new NodeConstInt();
            ans.x = value;

            tokenMatch(token);

            return ans;
        } else {
            throwError("ID or CONSTINT");
            return null;
        }
    }

    public void getNextToken(){
        token = scanner.scan();
    }
}
