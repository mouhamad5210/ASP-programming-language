package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFuncDef extends AspCompoundStmt {
    AspSuite suite;
    ArrayList<AspName> nameList = new ArrayList<>();

    AspFuncDef(int n) {
        super(n);
    }

    static AspFuncDef parse(Scanner s) {
        enterParser("func def");
        AspFuncDef funcDef = new AspFuncDef(s.curLineNum());
        skip(s, defToken);
        funcDef.nameList.add(AspName.parse(s));
        skip(s, leftParToken);
        while (s.curToken().kind != rightParToken) {
            funcDef.nameList.add(AspName.parse(s));
            if (s.curToken().kind == commaToken) {
                skip(s, commaToken);
            } else {
                break;
            }
        }
        skip(s, rightParToken);
        skip(s, colonToken);
        funcDef.suite = AspSuite.parse(s);
        leaveParser("func def");
        return funcDef;
    }

    @Override
    public void prettyPrint() {
        prettyWrite("def ");
        nameList.get(0).prettyPrint();
        prettyWrite(" (");
        if (nameList.size() > 1) {
            for (int i = 1; i < nameList.size(); i++) {
                if (i > 1) {
                    prettyWrite(", ");
                }
                nameList.get(i).prettyPrint();
            }
        }
        prettyWrite(")");
        prettyWrite(": ");
        suite.prettyPrint();
        prettyWriteLn("");
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        return null;
    }
}