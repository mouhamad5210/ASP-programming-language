package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeListValue extends RuntimeValue {
    ArrayList<RuntimeValue> listValue = new ArrayList<>();

    public RuntimeListValue(ArrayList<RuntimeValue> listValue) {
	    this.listValue = listValue;
    }

    @Override
    String typeName() {
        return "list";
    }

    @Override
    public String showInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (!listValue.isEmpty()) {
            for (RuntimeValue v : listValue) {
                sb.append(v.showInfo());
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
	    return sb.toString();
    }

    @Override
	public String toString() {
		return String.valueOf(listValue);
	}

    public ArrayList<RuntimeValue> getListValue() {
		return listValue;
	}

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntegerValue) {
            long nbTimes = v.getIntValue("* operator", where);
            ArrayList<RuntimeValue> newListValue = new ArrayList<>();
            for (long i = 0; i < nbTimes; i++) {
                newListValue.addAll(listValue);
            }
            return new RuntimeListValue(newListValue);
        }
        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return listValue.get((int)v.getIntValue("[] operator", where));
        }
        runtimeError("Subscription '[...]' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
	    if (v instanceof RuntimeListValue) {
            ArrayList<RuntimeValue> list2 = ((RuntimeListValue)v).getListValue();
            if (listValue.size() != list2.size()) {
                return new RuntimeBoolValue(false);
            }
            for (int i = 0; i < listValue.size(); i++) {
                if (!listValue.get(i).evalEqual(list2.get(i), where).getBoolValue("== operator", where)) {
                    return new RuntimeBoolValue(false);
                }
            }
            return new RuntimeBoolValue(true);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
	    if (v instanceof RuntimeListValue) {
            ArrayList<RuntimeValue> list2 = ((RuntimeListValue)v).getListValue();
            if (listValue.size() != list2.size()) {
                return new RuntimeBoolValue(true);
            }
            for (int i = 0; i < listValue.size(); i++) {
                if (!listValue.get(i).evalEqual(list2.get(i), where).getBoolValue("!= operator", where)) {
                    return new RuntimeBoolValue(true);
                }
            }
            return new RuntimeBoolValue(false);
        }
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	    return new RuntimeBoolValue(listValue.isEmpty());
    }
}
