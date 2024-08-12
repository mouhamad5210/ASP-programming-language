package no.uio.ifi.asp.runtime;

import java.util.HashMap;
import java.util.Map;

import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeDictValue extends RuntimeValue {
    HashMap<String, RuntimeValue> dictValue = new HashMap<>();

    public RuntimeDictValue(HashMap<String, RuntimeValue> dictValue) {
	    this.dictValue = dictValue;
    }

    @Override
    String typeName() {
        return "dict";
    }

    @Override
	public String showInfo() {
		StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Map.Entry<String, RuntimeValue> me : dictValue.entrySet()) {
            sb.append("'");
            sb.append(me.getKey());
            sb.append("': ");
            sb.append(me.getValue().showInfo());
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
	}

    @Override
    public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
        if (v instanceof RuntimeStringValue) {
            return dictValue.get(v.getStringValue("[] operator", where));
        }
        runtimeError("Subscription '[...]' undefined for " + typeName() + "!", where);
        return null;  // Required by the compiler!
    }

    @Override
    public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
        return new RuntimeBoolValue(false);
    }

    @Override
    public RuntimeValue evalNot(AspSyntax where) {
	    return new RuntimeBoolValue(dictValue.isEmpty());
    }
}
