package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTermOpr extends AspSyntax {
    TokenKind tokenKind;

    AspTermOpr(int n) {
        super(n);
    }

    void setTokenKind(TokenKind tokenKind) {
        this.tokenKind = tokenKind;
    }

    static AspTermOpr parse(Scanner s) {
        enterParser("term opr");
        AspTermOpr termOpr = new AspTermOpr(s.curLineNum());
        TokenKind tk = s.curToken().kind;
        if (tk == plusToken || tk == minusToken) {
            termOpr.setTokenKind(tk);
            skip(s, tk);
        }
        leaveParser("term opr");
        return termOpr;
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