package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspCompOpr extends AspSyntax {
    TokenKind tokenKind;

    AspCompOpr(int n) {
        super(n);
    }

    void setTokenKind(TokenKind tokenKind) {
        this.tokenKind = tokenKind;
    }

    static AspCompOpr parse(Scanner s) {
        enterParser("comp opr");
        AspCompOpr compOpr = new AspCompOpr(s.curLineNum());
        TokenKind tk = s.curToken().kind;
        if (tk == lessToken || tk == greaterToken || tk == doubleEqualToken || tk == greaterEqualToken || tk == lessEqualToken || tk == notEqualToken) {
            compOpr.setTokenKind(tk);
            skip(s, tk);
        } else {
            parserError("Expected an expression comp opr, but found a " + tk + "!", s.curLineNum());
        }
        leaveParser("comp opr");
        return compOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" " + tokenKind.toString() + " ");
    }

    @Override
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