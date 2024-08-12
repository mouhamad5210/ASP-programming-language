package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorPrefix extends AspSyntax {
    TokenKind factor;

    AspFactorPrefix(int n) {
        super(n);
    }

    void setFactor(TokenKind factor) {
        this.factor = factor;
    }

    static AspFactorPrefix parse(Scanner s) {
        enterParser("factor prefix");
        AspFactorPrefix factorPrefix = new AspFactorPrefix(s.curLineNum());
        TokenKind tk = s.curToken().kind;
        if (tk == plusToken || tk == minusToken) {
            factorPrefix.setFactor(tk);
            skip(s, tk);
        }
        leaveParser("factor prefix");
        return factorPrefix;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(factor.toString() + " ");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}