package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorOpr extends AspSyntax {
    TokenKind tokenKind;

    AspFactorOpr(int n) {
        super(n);
    }

    void setTokenKind(TokenKind tokenKind) {
        this.tokenKind = tokenKind;
    }

    static AspFactorOpr parse(Scanner s) {
        enterParser("factor opr");
        AspFactorOpr factorOpr = new AspFactorOpr(s.curLineNum());
        TokenKind tk = s.curToken().kind;
        if (tk == astToken || tk == slashToken || tk == percentToken || tk == doubleSlashToken) {
            factorOpr.setTokenKind(tk);
            skip(s, tk);
        } else {
            parserError("Expected an expression factor opr, but found a " + tk + "!", s.curLineNum());
        }
        leaveParser("factor opr");
        return factorOpr;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(" " + tokenKind.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}