package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.scanner.*;
import no.uio.ifi.asp.scanner.*;

public class RuntimeFunc extends RuntimeValue{

    String funcName;
    RuntimeScope rs;
    ArrayList<RuntimeValue> rvList = new ArrayList<>();
    AspFuncDef afd;

    public RuntimeFunc(String funcName, RuntimeScope rs, AspFuncDef afd){
        this.funcName = funcName;
        this.rs = rs;
        this.afd = afd;
    }

    @Override
    String typeName() {
        return "function";
    }

    public RuntimeFunc(RuntimeValue rv, ArrayList<RuntimeValue> rvList, RuntimeScope rs, AspFuncDef afd){

    }

    public String toString(){
        return this.funcName;
    }


    // jeg er u sikek rom den er riktig

    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
        RuntimeValue rv = null;
        if (actualParams == null){
            actualParams = new ArrayList<RuntimeValue>();
        }
        RuntimeScope rs2 = new RuntimeScope(rs);
        if(actualParams.size() != rvList.size()){
            runtimeError(" The parameters is not the same", where);
            return null;
        }

        // hvis listene har samme lengde!! går videre

        for(int i=0; i<actualParams.size(); i++){
            String id = actualParams.get(i).toString();
            rv = rs.find(id, afd);

            String id1 = rvList.get(i).toString();
            RuntimeValue val = actualParams.get(i);
            RuntimeValue rv2 = rs.find(val.toString(),afd); // if rv == null
            if(rv == null){
                rs.assign(id1, rv2);
            }else{
                rs.assign(id1, rv);
            }
        }


        return rv;
    }
}
