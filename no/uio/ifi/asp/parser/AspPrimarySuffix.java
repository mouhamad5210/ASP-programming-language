package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

abstract class AspPrimarySuffix extends AspSyntax {
    AspPrimarySuffix(int n) {
        super(n);
    }

    static AspPrimarySuffix parse(Scanner s) {
        enterParser("primary suffix");
        AspPrimarySuffix primarySuffix = null;
        if (s.isArgument()) {
            primarySuffix = AspArguments.parse(s);
        } else if (s.isSubscription()) {
            primarySuffix = AspSubscription.parse(s);
        } else {
            parserError("Expected an expression primary suffix, but found a " + s.curToken().kind + "!", s.curLineNum());
        }
        leaveParser("primary suffix");
        return primarySuffix;
    }
}