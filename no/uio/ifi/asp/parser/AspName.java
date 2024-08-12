package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspName extends AspAtom {
    String name;

    AspName(int n) {
        super(n);
    }

    void setName(String name) {
        this.name = name;
    }

    static AspName parse(Scanner s) {
        enterParser("name");
        AspName aspName = new AspName(s.curLineNum());
        aspName.setName(s.curToken().name);
        skip(s, nameToken);
        leaveParser("name");
        return aspName;
    }

    @Override
    public void prettyPrint() {
        prettyWrite(name);
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return new RuntimeStringValue(name);
    }
}