package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIntegerLiteral extends AspAtom {
    long number;

    AspIntegerLiteral(int n) {
        super(n);
    }

    void setNumber(long number) {
        this.number = number;
    }

    static AspIntegerLiteral parse(Scanner s) {
        enterParser("integer literal");
        AspIntegerLiteral integerLiteral = new AspIntegerLiteral(s.curLineNum());
        integerLiteral.setNumber(s.curToken().integerLit);
        skip(s, integerToken);
        leaveParser("integer literal");
        return integerLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(String.valueOf(number));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return new RuntimeIntegerValue(number);
    }
}