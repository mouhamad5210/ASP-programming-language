package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

abstract class AspStmt extends AspSyntax {
    AspStmt(int n) {
        super(n);
    }

    static AspStmt parse(Scanner s) {
        enterParser("stmt");
        AspStmt stmt = null;
        switch (s.curToken().kind) {
            case forToken:
            case ifToken:
            case whileToken:
            case defToken:
                stmt = AspCompoundStmt.parse(s);
                break;
            case nameToken:
            case integerToken:
            case floatToken:
            case stringToken:
            case passToken:
            case returnToken:
            case globalToken:
                stmt = AspSmallStmtList.parse(s);
                break;
            default:
                parserError("Expected an expression stmt, but found a " + s.curToken().kind + "!", s.curLineNum());
        }

        leaveParser("stmt");
        return stmt;
    }
}