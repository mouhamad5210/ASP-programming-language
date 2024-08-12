package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSubscription extends AspPrimarySuffix {
    AspExpr expr;

    AspSubscription(int n) {
        super(n);
    }

    static AspSubscription parse(Scanner s) {
        enterParser("subscription");
        AspSubscription subscription = new AspSubscription(s.curLineNum());
        skip(s, leftBracketToken);
        subscription.expr = AspExpr.parse(s);
        skip(s, rightBracketToken);
        leaveParser("subscription");
        return subscription;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");
        expr.prettyPrint();
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return expr.eval(curScope);
    }
}