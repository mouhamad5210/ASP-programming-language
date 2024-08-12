package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

abstract class AspCompoundStmt extends AspStmt {
    AspCompoundStmt(int n) {
        super(n);
    }

    static AspCompoundStmt parse(Scanner s) {
        enterParser("compound stmt");
        AspCompoundStmt compoundStmt = null;
        switch (s.curToken().kind) {
            case forToken:
                compoundStmt = AspForStmt.parse(s);
                break;
            case ifToken:
                compoundStmt = AspIfStmt.parse(s);
                break;
            case whileToken:
                compoundStmt = AspWhileStmt.parse(s);
                break;
            case defToken:
                compoundStmt = AspFuncDef.parse(s);
                break;
            default:
                parserError("Expected an expression compound stmt, but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("compound stmt");
        return compoundStmt;
    }
}