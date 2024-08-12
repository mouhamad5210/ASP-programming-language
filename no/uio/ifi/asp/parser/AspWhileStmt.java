package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspWhileStmt extends AspCompoundStmt {
    AspExpr expr;
    AspSuite suite;

    AspWhileStmt(int n) {
        super(n);
    }

    static AspWhileStmt parse(Scanner s) {
        enterParser("while stmt");
        AspWhileStmt whileStmt = new AspWhileStmt(s.curLineNum());
        skip(s, whileToken);
        whileStmt.expr = AspExpr.parse(s);
        skip(s, colonToken);
        whileStmt.suite = AspSuite.parse(s);
        leaveParser("while stmt");
        return whileStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("while ");
        expr.prettyPrint();
        prettyWrite(": ");
        suite.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        while(true){
            RuntimeValue rv = expr.eval(curScope);
            if(rv.getBoolValue("while test", this)){
                trace("While test true ");
                suite.eval(curScope);
            }
            else{
                break;
            }

        }
        trace("while test false");
        return null;
    }
}