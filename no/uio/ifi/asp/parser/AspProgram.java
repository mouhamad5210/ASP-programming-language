// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspStmt> stmtList = new ArrayList<>();

    AspProgram(int n) {
        super(n);
    }

    public static AspProgram parse(Scanner s) {
        enterParser("program");
        AspProgram program = new AspProgram(s.curLineNum());
        while (s.curToken().kind != eofToken) {
            //-- Must be changed in part 2:
            program.stmtList.add(AspStmt.parse(s));
        }
        leaveParser("program");
        return program;
    }

    @Override
    public void prettyPrint() {
        //-- Must be changed in part 2:
        for (int i = 0; i < stmtList.size(); i++) {
            stmtList.get(i).prettyPrint();
        }
    }

    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
        //-- Must be changed in part 4:
        return null;
    }
}
