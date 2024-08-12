package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspArguments extends AspPrimarySuffix {
    ArrayList<AspExpr> exprList = new ArrayList<>();

    AspArguments(int n) {
        super(n);
    }

    static AspArguments parse(Scanner s) {
        enterParser("arguments");
        AspArguments arguments = new AspArguments(s.curLineNum());
        skip(s, leftParToken);
        if (s.curToken().kind != rightParToken) {
            while (true) {
                arguments.exprList.add(AspExpr.parse(s));
                if (s.curToken().kind == commaToken) {
                    skip(s, commaToken);
                } else {
                    break;
                }
            }
        }
        skip(s, rightParToken);
        leaveParser("arguments");
        return arguments;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("(");
        for (int i = 0; i < exprList.size(); i++) {
            if (i > 0) {
                prettyWrite(", ");
            }
            exprList.get(i).prettyPrint();
        }
        prettyWrite(")");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        ArrayList<RuntimeValue> runtimeValueList = new ArrayList<>();
        for (AspExpr expr : exprList) {
            runtimeValueList.add(expr.eval(curScope));
        }
        return new RuntimeListValue(runtimeValueList);
    }
}