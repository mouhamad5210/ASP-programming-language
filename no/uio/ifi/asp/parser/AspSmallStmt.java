package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

abstract class AspSmallStmt extends AspSyntax {
    AspSmallStmt(int n) {
        super(n);
    }

    static AspSmallStmt parse(Scanner s) {
        enterParser("small stmt");
        AspSmallStmt smallStmt = null;
        switch (s.curToken().kind){
            case nameToken:
            case integerToken:
            case floatToken:
            case stringToken:
                if (s.anyEqualToken()) {
                    smallStmt = AspAssignment.parse(s);
                    break;
                }
                smallStmt = AspExprStmt.parse(s);
                break;
            case globalToken:
                smallStmt = AspGlobalStmt.parse(s);
                break;
            case passToken:
                smallStmt = AspPassStmt.parse(s);
                break;
            case returnToken:
                smallStmt = AspReturnStmt.parse(s);
                break;
            default:
                parserError("Expected an expression small stmt, but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("small stmt");
        return smallStmt;
    }
}