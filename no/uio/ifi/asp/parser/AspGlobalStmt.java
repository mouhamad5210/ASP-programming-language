package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspGlobalStmt extends AspSmallStmt {
    AspName name;

    AspGlobalStmt(int n) {
        super(n);
    }

    static AspGlobalStmt parse(Scanner s) {
        enterParser("global stmt");
        AspGlobalStmt globalStmt = new AspGlobalStmt(s.curLineNum());
        skip(s, globalToken);
        globalStmt.name = AspName.parse(s);
        leaveParser("global stmt");
        return globalStmt;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("global ");
        name.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}