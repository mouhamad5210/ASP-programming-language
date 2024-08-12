package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSmallStmtList extends AspStmt {
    ArrayList<AspSmallStmt> smallStmtList = new ArrayList<>();

    AspSmallStmtList(int n) {
        super(n);
    }

    public static AspSmallStmtList parse(Scanner s) {
        enterParser("small stmt list");
        AspSmallStmtList aspSmallStmtList = new AspSmallStmtList(s.curLineNum());
        while (s.curToken().kind != newLineToken) {
            aspSmallStmtList.smallStmtList.add(AspSmallStmt.parse(s));
            if (s.curToken().kind == semicolonToken) {
                skip(s, TokenKind.semicolonToken);
            }
        }
        skip(s, TokenKind.newLineToken);
        leaveParser("small stmt list");
        return aspSmallStmtList;
    }

    @Override
    public void prettyPrint() {
        for (int i = 0; i < smallStmtList.size(); i++) {
            if (i > 0) {
                prettyWrite("; ");
            }
            smallStmtList.get(i).prettyPrint();
        }
        prettyWriteLn();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}