// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo


package no.uio.ifi.asp.parser;

import no.uio.ifi.asp.scanner.*;

abstract class AspAtom extends AspSyntax {
    AspAtom(int n) {
        super(n);
    }

    static AspAtom parse(Scanner s) {
        enterParser("atom");
        AspAtom atom = null;
        TokenKind tk = s.curToken().kind;
        switch (tk) {
            case nameToken:
                atom = AspName.parse(s);
                break;
            case integerToken:
                atom = AspIntegerLiteral.parse(s);
                break;
            case floatToken:
                atom = AspFloatLiteral.parse(s);
                break;
            case stringToken:
                atom = AspStringLiteral.parse(s);
                break;
            case falseToken:
            case trueToken:
                atom = AspBooleanLiteral.parse(s);
                break;
            case noneToken:
                atom = AspNoneLiteral.parse(s);
                break;
            case leftParToken:
                atom = AspInnerExpr.parse(s);
                break;
            case leftBracketToken:
                atom = AspListDisplay.parse(s);
                break;
            case leftBraceToken:
                atom = AspDictDisplay.parse(s);
                break;
            default:
                parserError("Expected an expression atom, but found a " + tk + "!", s.curLineNum());
        }
        leaveParser("atom");
        return atom;
    }
}
