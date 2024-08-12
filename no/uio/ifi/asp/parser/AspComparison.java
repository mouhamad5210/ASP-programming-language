package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspComparison extends AspSyntax {
    ArrayList<AspCompOpr> compOprList = new ArrayList<>();
    ArrayList<AspTerm> termList = new ArrayList<>();

    AspComparison(int n) {
        super(n);
    }

    static AspComparison parse(Scanner s) {
        enterParser("comparison");
        AspComparison comparison = new AspComparison(s.curLineNum());
        comparison.termList.add(AspTerm.parse(s));
        while (s.isCompOpr()) {
            comparison.compOprList.add(AspCompOpr.parse(s));
            comparison.termList.add(AspTerm.parse(s));
        }
        leaveParser("comparison");
        return comparison;
    }

    @Override
    public void prettyPrint() {
        for (int i = 0; i < termList.size(); i++) {
            if (i > 0) {
                compOprList.get(i - 1).prettyPrint();
            }
            termList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = termList.get(0).eval(curScope);
        if (compOprList.size() > 0) {
            for (int i = 0; i < compOprList.size(); i++) {
                TokenKind tk = compOprList.get(i).tokenKind;
                RuntimeValue stop = new RuntimeBoolValue(false);
                switch (tk) {
                    case lessToken:
                        stop = v.evalLess(termList.get(i + 1).eval(curScope), this);
                        break;
                    case greaterToken:
                        stop = v.evalGreater(termList.get(i + 1).eval(curScope), this);
                        break;
                    case doubleEqualToken:
                        stop = v.evalEqual(termList.get(i + 1).eval(curScope), this);
                        break;
                    case greaterEqualToken:
                        stop = v.evalGreaterEqual(termList.get(i + 1).eval(curScope), this);
                        break;
                    case lessEqualToken:
                        stop = v.evalLessEqual(termList.get(i + 1).eval(curScope), this);
                        break;
                    case notEqualToken:
                        stop = v.evalNotEqual(termList.get(i + 1).eval(curScope), this);
                        break;
                    default:
                        Main.panic("Illegal term operator: " + tk + "!");
                }
    
                v = termList.get(i + 1).eval(curScope);
    
                if (!stop.getBoolValue("and operator", this)) {
                    return new RuntimeBoolValue(false);
                }
            }
            return new RuntimeBoolValue(true);
        }
        return v;
    }
}