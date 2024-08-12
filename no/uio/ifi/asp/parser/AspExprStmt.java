package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspExprStmt extends AspSmallStmt {
    AspExpr expr;

    AspExprStmt(int n) {
        super(n);
    }

    static AspExprStmt parse(Scanner s) {
        enterParser("expr stmt");
        AspExprStmt exprStmt = new AspExprStmt(s.curLineNum());
        exprStmt.expr = AspExpr.parse(s);
        leaveParser("expr stmt");
        return exprStmt;
    }

    @Override
    public void prettyPrint() {
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return expr.eval(curScope);
    }
}