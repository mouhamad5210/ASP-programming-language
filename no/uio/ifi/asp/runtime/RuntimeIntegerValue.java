package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntegerValue extends RuntimeValue {
    long intValue;

    public RuntimeIntegerValue(long intValue) {
	    this.intValue = intValue;
    }

    @Override
    String typeName() {
	    return "integer";
    }

    @Override 
    public String toString() {
	    return String.valueOf(intValue);
    }

    @Override
    public long getIntValue(String what, AspSyntax where) {
        return intValue;
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return intValue * 1.0;
    }

    public boolean getBoolValue(String what, AspSyntax where) {
        return intValue > 0;
    }

    // For part 3:
    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intValue + v.getIntValue("+ operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue + v.getFloatValue("+ operator", where));
        }
        runtimeError("'+' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            if (intValue % v.getIntValue("/ operator", where) == 0) {
                return new RuntimeIntegerValue(intValue / v.getIntValue("/ operator", where));
            }
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue / v.getFloatValue("/ operator", where));
        }
        runtimeError("'/' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue == v.getIntValue("== operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue == v.getFloatValue("== operator", where));
        }
        return new RuntimeBoolValue(false);
    }
    
    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue > v.getIntValue("> operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue > v.getFloatValue("> operator", where));
        }
        runtimeError("'>' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue >= v.getIntValue(">= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue >= v.getFloatValue(">= operator", where));
        }
        runtimeError("'>=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(Math.floorDiv(intValue, v.getIntValue("// operator", where)));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("// operator", where)));
        }
        runtimeError("'//' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue < v.getIntValue("< operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue < v.getFloatValue("< operator", where));
        }
        runtimeError("'<' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue <= v.getIntValue("<= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue <= v.getFloatValue("<= operator", where));
        }
        runtimeError("'<=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(Math.floorMod(intValue, v.getIntValue("% operator", where)));
        }
        if (v instanceof RuntimeFloatValue) {
            double l = v.getFloatValue("% operator", where);
            return new RuntimeFloatValue(intValue - l * Math.floor(intValue / l));
        }
        runtimeError("'%' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intValue * v.getIntValue("* operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue * v.getFloatValue("* operator", where));
        }
        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeIntegerValue((-1) * intValue);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(intValue != v.getIntValue("!= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(intValue != v.getFloatValue("!= operator", where));
        }
        runtimeError("'!=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeIntegerValue(intValue);
    }
    
    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeIntegerValue(intValue - v.getIntValue("- operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(intValue - v.getFloatValue("- operator", where));
        }
        runtimeError("'-' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	    return new RuntimeBoolValue(intValue == 0);
    }
}
