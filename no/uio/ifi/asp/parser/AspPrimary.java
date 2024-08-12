package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;

class AspPrimary extends AspSyntax {
    AspAtom atom;
    ArrayList<AspPrimarySuffix> primarySuffixList = new ArrayList<>();

    AspPrimary(int n) {
        super(n);
    }

    static AspPrimary parse(Scanner s) {
        enterParser("primary");
        AspPrimary primary = new AspPrimary(s.curLineNum());
        primary.atom = AspAtom.parse(s);
        while (s.isArgument() || s.isSubscription()) {
            primary.primarySuffixList.add(AspPrimarySuffix.parse(s));
        }
        leaveParser("primary");
        return primary;
    }

    @Override
    public void prettyPrint() {
        atom.prettyPrint();
        for (int i = 0; i < primarySuffixList.size(); i++) {
            primarySuffixList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 3:
        RuntimeValue v = atom.eval(curScope);
        for (int i = 0; i < primarySuffixList.size(); i++) {
            if (primarySuffixList.get(i) instanceof AspSubscription) {
                v = v.evalSubscription(primarySuffixList.get(i).eval(curScope), this);
            } else if (primarySuffixList.get(i) instanceof AspArguments) {
                // TODO in part 4
            }
        }
        return v;
    }
}
