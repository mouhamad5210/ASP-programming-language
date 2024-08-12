package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspReturnStmt extends AspSmallStmt {
    AspExpr expr;

    AspReturnStmt(int n) {
        super(n);
    }

    static AspReturnStmt parse(Scanner s) {
        enterParser("return stmt");
        AspReturnStmt returnStmt = new AspReturnStmt(s.curLineNum());
        skip(s, returnToken);
        returnStmt.expr = AspExpr.parse(s);
        leaveParser("return stmt");
        return returnStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("return ");
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        RuntimeValue v = expr.eval(curScope);
        RuntimeValue vv = curScope.find(v.toString(), this); // sjekker om verdein finnes allerede
        if(vv == null){
            trace("return" + v.showInfo());
            throw new RuntimeReturnValue(vv, lineNum);
        }else{
            trace("return " + vv.showInfo());
            throw new RuntimeReturnValue(vv, lineNum);
        }
    }
}