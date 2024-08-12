package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIfStmt extends AspCompoundStmt {
    ArrayList<AspExpr> exprList = new ArrayList<>();
    ArrayList<AspSuite> suiteList = new ArrayList<>();

    AspIfStmt(int n) {
        super(n);
    }

    static AspIfStmt parse(Scanner s) {
        enterParser("if stmt");
        AspIfStmt ifStmt = new AspIfStmt(s.curLineNum());
        skip(s, ifToken);
        ifStmt.exprList.add(AspExpr.parse(s));
        skip(s, colonToken);
        ifStmt.suiteList.add(AspSuite.parse(s));
        while (s.curToken().kind == elifToken) {
            skip(s, elifToken);
            ifStmt.exprList.add(AspExpr.parse(s));
            skip(s, colonToken);
            ifStmt.suiteList.add(AspSuite.parse(s));
        }
        if (s.curToken().kind == elseToken) {
            skip(s, elseToken);
            skip(s, colonToken);
            ifStmt.suiteList.add(AspSuite.parse(s));
        }
        leaveParser("if stmt");
        return ifStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("if ");
        exprList.get(0).prettyPrint();
        prettyWrite(": ");
        suiteList.get(0).prettyPrint();

        int i = 1;
        while (exprList.size() > i) {
            prettyWrite("elif ");
            exprList.get(i).prettyPrint();
            prettyWrite(": ");
            suiteList.get(i).prettyPrint();
            i++;
        }

        if (exprList.size() < suiteList.size()) {
            prettyWrite("else");
            prettyWrite(": ");
            suiteList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}