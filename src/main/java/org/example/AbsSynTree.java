package org.example;

import java.util.ArrayList;
import java.util.List;

public class AbsSynTree {
   private NodeProgram root;
    public NodeProgram getRoot() {
        return root;
    }

    public void setRoot(NodeProgram root) {
        this.root = root;
    }

    public void show(){
        root.show();
    }
    abstract class NodeBase{
        public abstract void show();
    }
    abstract class NodeExpr extends NodeBase{

    }
    abstract class NodeStmt extends NodeBase{

    }
    class NodeID extends NodeExpr{
        String name;

        @Override
        public void show() {
            System.out.println("AST id " + name);
        }
    }
    class NodeConstInt extends NodeExpr{
        int x;

        @Override
        public void show() {
            System.out.println("AST const int " + x);
        }
    }
    class NodePlus extends NodeExpr{
        NodeExpr lhs;
        NodeExpr rhs;

        NodePlus(NodeExpr lhs, NodeExpr rhs){
            this.lhs = lhs;
            this.rhs = rhs;
        }
        @Override
        public void show() {
            System.out.println("AST plus");
            System.out.print("LHS: ");
            lhs.show();
            System.out.print("RHS: ");
            rhs.show();
        }
    }
    class NodeOutput extends NodeStmt{
        NodeID output;

        public NodeOutput(NodeID output) {
            this.output = output;
        }
        @Override
        public void show() {
            System.out.println("AST output");
            output.show();
        }
    }
    class NodeInitialize extends NodeStmt{
        NodeID id;
        NodeConstInt num;

        public NodeInitialize(NodeID id, NodeConstInt num) {
            this.id = id;
            this.num = num;
        }

        @Override
        public void show() {
            System.out.println("AST initialize");
            id.show();
            num.show();
        }
    }
    class NodeCompute extends NodeStmt{
        NodeID id;
        NodeExpr expr;

        public NodeCompute(NodeID id, NodeExpr expr) {
            this.id = id;
            this.expr = expr;
        }

        @Override
        public void show() {
            System.out.println("AST compute");
            id.show();
            expr.show();
        }
    }
    class NodeStmts extends NodeBase{
        List<NodeStmt> stmtList = new ArrayList<>();

        @Override
        public void show() {
            System.out.println("AST statements");
            for (NodeStmt s: stmtList) {
                s.show();
            }
        }
    }
    class Nodeif extends NodeStmt{
        NodeID lhs;
        NodeID rhs;
        NodeStmts ifTrue;

        public Nodeif(NodeID lhs, NodeID rhs, NodeStmts ifTrue) {

            this.lhs = lhs;

            this.rhs = rhs;
            this.ifTrue = ifTrue;
        }

        @Override
        public void show() {
            System.out.println("AST if");
            System.out.print("LHS: ");
            lhs.show();
            System.out.print("RHS: ");
            rhs.show();
            ifTrue.show();
            System.out.println("AST endif");
        }
    }
    class NodeVars extends NodeBase{
        List<NodeID> ids = new ArrayList<>();

        @Override
        public void show() {
            System.out.println("AST variables");
            for (NodeID i: ids) {
                i.show();
            }
        }

    }
    class NodeProgram extends NodeBase{
        NodeVars vars;
        NodeStmts stmts;

        public NodeProgram(NodeVars vars, NodeStmts stmts) {
            this.vars = vars;
            this.stmts = stmts;
        }

        @Override
        public void show() {
        vars.show();
        stmts.show();
        }
    }


}
