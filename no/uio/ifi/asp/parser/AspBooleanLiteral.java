package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspBooleanLiteral extends AspAtom {
    TokenKind value;

    AspBooleanLiteral(int n) {
        super(n);
    }

    void setValue(TokenKind value) {
        this.value = value;
    }

    static AspBooleanLiteral parse(Scanner s) {
        enterParser("boolean literal");
        AspBooleanLiteral booleanLiteral = new AspBooleanLiteral(s.curLineNum());
        TokenKind tk = s.curToken().kind;
        if (tk == trueToken || tk == falseToken) {
            booleanLiteral.setValue(tk);
            skip(s, tk);
        } else {
            parserError("Expected an expression boolean literal, but found a " + tk + "!", s.curLineNum());
        }
        leaveParser("boolean literal");
        return booleanLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(value.toString());
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return new RuntimeBoolValue(value == trueToken);
    }
}