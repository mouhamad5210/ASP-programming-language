// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo


package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspAndTest> andTestList = new ArrayList<>();

    AspExpr(int n) {
        super(n);
    }

    public static AspExpr parse(Scanner s) {
        enterParser("expr");
        AspExpr expr = new AspExpr(s.curLineNum());
        expr.andTestList.add(AspAndTest.parse(s));
        while (s.curToken().kind == orToken) {
            skip(s, orToken);
            expr.andTestList.add(AspAndTest.parse(s));
        }
        leaveParser("expr");
        return expr;
    }

    @Override
    public void prettyPrint() {
        //-- Must be changed in part 2:
        for (int i = 0; i < andTestList.size(); i++) {
            if (i > 0){
                prettyWrite(" or ");
            }
            andTestList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = andTestList.get(0).eval(curScope);
        int i = 1;
        while (i < andTestList.size() && !v.getBoolValue("or operator", this)) {
            v = andTestList.get(i).eval(curScope);
            i++;
        }
        return v;
    }
}
