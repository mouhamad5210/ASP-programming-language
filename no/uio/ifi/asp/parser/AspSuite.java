package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspSuite extends AspSyntax {
    ArrayList<AspStmt> stmtList = new ArrayList<>();
    AspSmallStmtList smallStmtList;

    AspSuite(int n) {
        super(n);
    }

    static AspSuite parse(Scanner s) {
        enterParser("suite");
        AspSuite suite = new AspSuite(s.curLineNum());
        if (s.curToken().kind == newLineToken) {
            skip(s, newLineToken);
            skip(s, indentToken);
            while (s.curToken().kind != dedentToken) {
                suite.stmtList.add(AspStmt.parse(s));
            }
            skip(s, dedentToken);
        } else {
            suite.smallStmtList = AspSmallStmtList.parse(s);
        }
        leaveParser("suite");
        return suite;
    }

    @Override
    public void prettyPrint() {
        if (smallStmtList != null) {
            smallStmtList.prettyPrint();
        } else {
            prettyWriteLn();
            prettyIndent();
            for (int i = 0; i < stmtList.size(); i++) {
                stmtList.get(i).prettyPrint();
            }
            prettyDedent();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}