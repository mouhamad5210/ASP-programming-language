package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspStringLiteral extends AspAtom {
    String string;

    AspStringLiteral(int n) {
        super(n);
    }

    void setString(String string) {
        this.string = string;
    }

    static AspStringLiteral parse(Scanner s) {
        enterParser("string literal");
        AspStringLiteral stringLiteral = new AspStringLiteral(s.curLineNum());
        stringLiteral.setString(s.curToken().stringLit);
        skip(s, stringToken);
        leaveParser("string literal");
        return stringLiteral;
    }

    @Override
    public void prettyPrint() {
        if (string.contains("\"")) {
            prettyWrite("'" + string + "'");
        } else {
            prettyWrite('"' + string + '"');
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return new RuntimeStringValue(string);
    }
}