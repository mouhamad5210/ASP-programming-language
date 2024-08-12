package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFloatLiteral extends AspAtom {
    double number;

    AspFloatLiteral(int n) {
        super(n);
    }

    void setNumber(double number) {
        this.number = number;
    }

    static AspFloatLiteral parse(Scanner s) {
        enterParser("float literal");
        AspFloatLiteral floatLiteral = new AspFloatLiteral(s.curLineNum());
        floatLiteral.setNumber(s.curToken().floatLit);
        skip(s, floatToken);
        leaveParser("float literal");
        return floatLiteral;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(String.format("%.06f", number));
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return new RuntimeFloatValue(number);
    }
}