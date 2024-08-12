package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAssignment extends AspSmallStmt {
    AspName name;
    AspExpr expr;
    ArrayList<AspSubscription> subscriptionList = new ArrayList<>();

    AspAssignment(int n) {
        super(n);
    }

    static AspAssignment parse(Scanner s) {
        enterParser("assignment");
        AspAssignment assignment = new AspAssignment(s.curLineNum());
        assignment.name = AspName.parse(s);
        while (s.curToken().kind != equalToken) {
            assignment.subscriptionList.add(AspSubscription.parse(s));
        }
        skip(s, equalToken);
        assignment.expr = AspExpr.parse(s);
        leaveParser("assignment");
        return assignment;
    }

    @Override
    public void prettyPrint() {
        name.prettyPrint();
        for (int i = 0; i < subscriptionList.size(); i++) {
            subscriptionList.get(i).prettyPrint();
        }
        prettyWrite(" = ");
        expr.prettyPrint();
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:

        return null;
    }
}
