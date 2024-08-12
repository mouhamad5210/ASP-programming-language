// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAndTest extends AspSyntax {
    ArrayList<AspNotTest> notTestList = new ArrayList<>();

    AspAndTest(int n) {
        super(n);
    }

    static AspAndTest parse(Scanner s) {
        enterParser("and test");
        AspAndTest andTest = new AspAndTest(s.curLineNum());
        andTest.notTestList.add(AspNotTest.parse(s));
        while (s.curToken().kind == andToken) {
            skip(s, andToken);
            andTest.notTestList.add(AspNotTest.parse(s));
        }
        leaveParser("and test");
        return andTest;
    }

    @Override
    public void prettyPrint() {
        for (int i = 0; i < notTestList.size(); i++) {
            if (i > 0) {
                prettyWrite(" and ");
            }
            notTestList.get(i).prettyPrint();
        }
    }

    @Override
    RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = notTestList.get(0).eval(curScope);
        int i = 1;
        while (i < notTestList.size() && v.getBoolValue("and operator", this)) {
            v = notTestList.get(i).eval(curScope);
            i++;
        }
        return v;
    }
}
