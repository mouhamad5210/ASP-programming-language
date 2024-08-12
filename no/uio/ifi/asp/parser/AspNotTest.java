package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspNotTest extends AspSyntax {
    AspComparison comparison;
    boolean notTokenExisting = false;

    AspNotTest(int n) {
        super(n);
    }

    void setComparison(AspComparison comparison) {
        this.comparison = comparison;
    }

    void setNotTokenExisting(boolean notTokenExisting) {
        this.notTokenExisting = notTokenExisting;
    }

    static AspNotTest parse (Scanner s) {
        enterParser("not test");
        AspNotTest notTest = new AspNotTest(s.curLineNum());
        if (s.curToken().kind == notToken) {
            skip(s, notToken);
            notTest.setNotTokenExisting(true);
        }
        notTest.setComparison(AspComparison.parse(s));
        leaveParser("not test");
        return notTest;
    }

    @Override
    public void prettyPrint() {
        if (notTokenExisting) {
            prettyWrite("not ");
        }
        comparison.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = comparison.eval(curScope);
        if (notTokenExisting){
            v = v.evalNot(this);
        }
        return v;
    }
}