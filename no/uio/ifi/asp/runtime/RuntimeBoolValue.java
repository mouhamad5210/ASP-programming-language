// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeBoolValue extends RuntimeValue {
    boolean boolValue;

    public RuntimeBoolValue(boolean v) {
	boolValue = v;
    }

    @Override
    String typeName() {
	return "boolean";
    }

    @Override
    public String toString() {
	return (boolValue ? "True" : "False");
    }

    @Override
    public boolean getBoolValue(String what, AspSyntax where) {
	return boolValue;
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
	return boolValue ? 1 : 0;
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(false);
        }
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(boolValue ? 1 == v.getIntValue("== operator", where) : 0 == v.getIntValue("== operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(boolValue ? 1.0 == v.getFloatValue("== operator", where) : 0.0 == v.getFloatValue("== operator", where));
        }
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	return new RuntimeBoolValue(! boolValue);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeNoneValue) {
            return new RuntimeBoolValue(true);
        }
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(boolValue ? 1 != v.getIntValue("== operator", where) : 0 != v.getIntValue("== operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(boolValue ? 1.0 != v.getFloatValue("== operator", where) : 0.0 != v.getFloatValue("== operator", where));
        }
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("Type error for !=.", where);
        return null;  // Required by the compiler
    }

    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) < v.getIntValue("< operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) < v.getFloatValue("< operator", where));
        }
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("'<' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) <= v.getIntValue("<= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) <= v.getFloatValue("<= operator", where));
        }
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(true);
        }
        runtimeError("'<=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) > v.getIntValue("> operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) > v.getFloatValue("> operator", where));
        }
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("'>' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) >= v.getIntValue(">= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue((boolValue ? 1 : 0) >= v.getFloatValue(">= operator", where));
        }
        if (v instanceof RuntimeStringValue) {
            return new RuntimeBoolValue(false);
        }
        runtimeError("'>=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
}
