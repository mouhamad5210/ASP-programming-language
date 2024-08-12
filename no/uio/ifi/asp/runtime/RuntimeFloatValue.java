package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
    double floatValue;

    public RuntimeFloatValue(double floatValue) {
	    this.floatValue = floatValue;
    }

    @Override
    String typeName() {
	    return "float";
    }

    @Override 
    public String toString() {
	    return String.valueOf(floatValue);
    }

    @Override
    public double getFloatValue(String what, AspSyntax where) {
        return floatValue;
    }

    public boolean getBoolValue(String what, AspSyntax where) {
        return floatValue > 0.0;
    }

    // For part 3:
    @Override
    public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(floatValue + v.getIntValue("+ operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue + v.getFloatValue("+ operator", where));
        }
        runtimeError("'+' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(floatValue / v.getIntValue("/ operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue / v.getFloatValue("/ operator", where));
        }
        runtimeError("'/' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue == v.getIntValue("== operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue == v.getFloatValue("== operator", where));
        }
        return new RuntimeBoolValue(false);
    }
    
    @Override
    public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue > v.getIntValue("> operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue > v.getFloatValue("> operator", where));
        }
        runtimeError("'>' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue >= v.getIntValue(">= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue >= v.getFloatValue(">= operator", where));
        }
        runtimeError("'>=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getIntValue("// operator", where)));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(Math.floor(floatValue / v.getFloatValue("// operator", where)));
        }
        runtimeError("'//' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue < v.getIntValue("< operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue < v.getFloatValue("< operator", where));
        }
        runtimeError("'<' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue <= v.getIntValue("<= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue <= v.getFloatValue("<= operator", where));
        }
        runtimeError("'<=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            double l = (double)v.getIntValue("% operator", where);
            return new RuntimeFloatValue(floatValue - l * Math.floor(floatValue / l));
        }
        if (v instanceof RuntimeFloatValue) {
            double l = v.getFloatValue("% operator", where);
            return new RuntimeFloatValue(floatValue - l * Math.floor(floatValue / l));
        }
        runtimeError("'%' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(floatValue * v.getIntValue("* operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue * v.getFloatValue("* operator", where));
        }
        runtimeError("'*' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalNegate(AspSyntax where) {
        return new RuntimeFloatValue((-1.0) * floatValue);
    }

    @Override
    public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeBoolValue(floatValue != v.getIntValue("!= operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeBoolValue(floatValue != v.getFloatValue("!= operator", where));
        }
        runtimeError("'!=' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }
    
    @Override
    public RuntimeValue evalPositive(AspSyntax where) {
        return new RuntimeFloatValue(floatValue);
    }
    
    @Override
    public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeIntegerValue) {
            return new RuntimeFloatValue(floatValue - v.getIntValue("- operator", where));
        }
        if (v instanceof RuntimeFloatValue) {
            return new RuntimeFloatValue(floatValue - v.getFloatValue("- operator", where));
        }
        runtimeError("'-' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	    return new RuntimeBoolValue(floatValue == 0.0);
    }
}
