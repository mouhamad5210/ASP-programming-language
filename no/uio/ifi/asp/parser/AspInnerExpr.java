package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspInnerExpr extends AspAtom {
    AspExpr expr;

    AspInnerExpr(int n) {
        super(n);
    }

    static AspInnerExpr parse(Scanner s) {
        enterParser("inner expr");
        AspInnerExpr innerExpr = new AspInnerExpr(s.curLineNum());
        skip(s, leftParToken);
        innerExpr.expr = AspExpr.parse(s);
        skip(s, rightParToken);
        leaveParser("inner expr");
        return innerExpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        expr.prettyPrint();
        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return expr.eval(curScope);
    }
}