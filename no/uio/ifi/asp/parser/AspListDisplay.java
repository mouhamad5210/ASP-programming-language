package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspListDisplay extends AspAtom {
    ArrayList<AspExpr> exprList = new ArrayList<>();

    AspListDisplay(int n) {
        super(n);
    }

    static AspListDisplay parse(Scanner s) {
        enterParser("list display");
        AspListDisplay listDisplay = new AspListDisplay(s.curLineNum());
        skip(s, leftBracketToken);
        if (s.curToken().kind != rightBracketToken) {
            listDisplay.exprList.add(AspExpr.parse(s));
            while (s.curToken().kind == commaToken) {
                skip(s, commaToken);
                listDisplay.exprList.add(AspExpr.parse(s));
            }
        }
        skip(s, rightBracketToken);
        leaveParser("list display");
        return listDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("[");
        for (int i = 0; i < exprList.size(); i++) {
            if (i > 0) {
                prettyWrite(", ");
            }
            exprList.get(i).prettyPrint();
        }
        prettyWrite("]");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        ArrayList<RuntimeValue> listValue = new ArrayList<>();
        for (AspExpr expr : exprList) {
            listValue.add(expr.eval(curScope));
        }
        return new RuntimeListValue(listValue);
    }
}