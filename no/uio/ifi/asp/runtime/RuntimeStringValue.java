package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
    String stringValue;

    public RuntimeStringValue(String stringValue) {
	    this.stringValue = stringValue;
    }

    @Override
    protected String typeName() {
	    return "string";
    }

    @Override
    public String showInfo() {
	    return "'" + stringValue + "'";
    }

    @Override
    public String toString() {
	    return stringValue;
    }

    @Override
    public String getStringValue(String what, AspSyntax where) {
        return stringValue;
    }

    @Override
	public boolean getBoolValue(String what, AspSyntax where) {
		return !stringValue.isEmpty();
	}

    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue){
            return new RuntimeStringValue(stringValue + v.getStringValue("+ operator", where));
        }
        runtimeError("'+' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
        if (v instanceof RuntimeIntegerValue) {
            long nbTimes = v.getIntValue("* operator", where);
            StringBuilder sb = new StringBuilder();
            for (long i = 0; i < nbTimes; i++) {
                sb.append(stringValue);
            }
            return new RuntimeStringValue(sb.toString());
        }
        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null; // Required by the compiler
    }

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeStringValue(String.valueOf(stringValue.charAt((int)v.getIntValue("[] operator", where))));
        }
        runtimeError("Subscription '[...]' unknown " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.equals(v.getStringValue("== operator", where)));
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(!stringValue.equals(v.getStringValue("!= operator", where)));
        }
        return new RuntimeBoolValue(true);
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("< operator", where)) < 0);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("<= operator", where)) <= 0);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue("> operator", where)) > 0);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(stringValue.compareTo(v.getStringValue(">= operator", where)) >= 0);
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	    return new RuntimeBoolValue(stringValue.isEmpty());
    }
}
