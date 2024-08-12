package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspTerm extends AspSyntax {
    ArrayList<AspFactor> factorList = new ArrayList<>();
    ArrayList<AspTermOpr> termOprList = new ArrayList<>();

    AspTerm(int n) {
        super(n);
    }

    static AspTerm parse(Scanner s) {
        enterParser("term");
        AspTerm term = new AspTerm(s.curLineNum());
        term.factorList.add(AspFactor.parse(s));
        while (s.isTermOpr()) {
            term.termOprList.add(AspTermOpr.parse(s));
            term.factorList.add(AspFactor.parse(s));
        }
        leaveParser("term");
        return term;
    }

    @Override
    public void prettyPrint() {
        for (int i = 0; i < factorList.size(); i++) {
            if (i > 0) {
                termOprList.get(i - 1).prettyPrint();
            }
            factorList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = factorList.get(0).eval(curScope);
        for (int i = 0; i < termOprList.size(); i++) {
            TokenKind tk = termOprList.get(i).tokenKind;
            switch (tk) {
                case plusToken:
                    v = v.evalAdd(factorList.get(i + 1).eval(curScope), this);
                    break;
                case minusToken:
                    v = v.evalSubtract(factorList.get(i + 1).eval(curScope), this);
                    break;
                default:
                    Main.panic("Illegal term operator: " + tk + "!");
            }
        }
        return v;
    }
}