package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.Main;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspFactor extends AspSyntax {
    ArrayList<AspFactorPrefix> factorPrefixList = new ArrayList<>();
    ArrayList<AspPrimary> primaryList = new ArrayList<>();
    ArrayList<AspFactorOpr> factorOprList = new ArrayList<>();

    AspFactor(int n) {
        super(n);
    }

    static AspFactor parse (Scanner s) {
        enterParser("factor");
        AspFactor factor = new AspFactor(s.curLineNum());
        if (s.isFactorPrefix()) {
            factor.factorPrefixList.add(AspFactorPrefix.parse(s));
        } else {
            factor.factorPrefixList.add(null);
        }
        factor.primaryList.add(AspPrimary.parse(s));
        while (s.isFactorOpr()) {
            factor.factorOprList.add(AspFactorOpr.parse(s));
            if (s.isFactorPrefix()) {
                factor.factorPrefixList.add(AspFactorPrefix.parse(s));
            } else {
                factor.factorPrefixList.add(null);
            }
            factor.primaryList.add(AspPrimary.parse(s));
        }
        leaveParser("factor");
        return factor;
    }

    @Override
    public void prettyPrint() {
        for (int i = 0; i < primaryList.size(); i++) {
            if (i < factorPrefixList.size() && factorPrefixList.get(i) != null) {
                factorPrefixList.get(i).prettyPrint();
            }
            primaryList.get(i).prettyPrint();
            if (i < factorOprList.size()) {
                factorOprList.get(i).prettyPrint();
            }
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = null;
        if (factorPrefixList.get(0) != null) {
            TokenKind tk = factorPrefixList.get(0).factor;
            if (tk == TokenKind.minusToken) {
                v = primaryList.get(0).eval(curScope).evalNegate(this);
            } else if (tk == TokenKind.plusToken) {
                v = primaryList.get(0).eval(curScope).evalPositive(this);
            } else {
                Main.panic("Do not support the prefix " + tk);
            }
        } else {
            v = primaryList.get(0).eval(curScope);
        }

        for (int i = 0; i < factorOprList.size(); i++) {
            RuntimeValue v2 = null;
            if (factorPrefixList.get(i + 1) != null) {
                TokenKind tk = factorPrefixList.get(i + 1).factor;
                if (tk == TokenKind.minusToken) {
                    v2 = primaryList.get(i + 1).eval(curScope).evalNegate(this);
                } else if (tk == TokenKind.plusToken) {
                    v2 = primaryList.get(i + 1).eval(curScope).evalPositive(this);
                } else {
                    Main.panic("Do not support the prefix " + tk);
                }
            } else {
                v2 = primaryList.get(i + 1).eval(curScope);
            }

            TokenKind tk = factorOprList.get(i).tokenKind;
            switch (tk) {
                case percentToken:
                    v = v.evalModulo(v2, this);
                    break;
                case slashToken:
                    v = v.evalDivide(v2, this);
                    break;
                case doubleSlashToken:
                    v = v.evalIntDivide(v2, this);
                    break;
                case astToken:
                    v = v.evalMultiply(v2, this);
                    break;
                default:
                    Main.panic("Illegal factor operator: " + tk + "!");
            }
        }
        return v;
    }
}