package no.uio.ifi.asp.parser;

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspDictDisplay extends AspAtom {
    ArrayList<AspStringLiteral> stringLiteralList = new ArrayList<>();
    ArrayList<AspExpr> exprList = new ArrayList<>();

    AspDictDisplay(int n) {
        super(n);
    }

    static AspDictDisplay parse(Scanner s) {
        enterParser("dict display");
        AspDictDisplay dictDisplay = new AspDictDisplay(s.curLineNum());
        skip(s, leftBraceToken);
        if (s.curToken().kind != rightBraceToken) {
            dictDisplay.stringLiteralList.add(AspStringLiteral.parse(s));
            skip(s, colonToken);
            dictDisplay.exprList.add(AspExpr.parse(s));
            while (s.curToken().kind == commaToken) {
                skip(s, commaToken);
                dictDisplay.stringLiteralList.add(AspStringLiteral.parse(s));
                skip(s, colonToken);
                dictDisplay.exprList.add(AspExpr.parse(s));
            }
        }
        skip(s, rightBraceToken);
        leaveParser("dict display");
        return dictDisplay;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("{");
        for (int i = 0; i < stringLiteralList.size(); i++) {
            stringLiteralList.get(i).prettyPrint();
            prettyWrite(":");
            exprList.get(i).prettyPrint();
            if (i < stringLiteralList.size() - 1) {
                prettyWrite(", ");
            }
        }
        prettyWrite("}");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        HashMap<String, RuntimeValue> dict = new HashMap<>();
        for (int i = 0; i < stringLiteralList.size(); i++) {
            dict.put(stringLiteralList.get(i).eval(curScope).toString(), exprList.get(i).eval(curScope));
        }
        return new RuntimeDictValue(dict);
    }
}
