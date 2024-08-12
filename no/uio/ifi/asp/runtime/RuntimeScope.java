// © 2021 Dag Langmyhr, Institutt for informatikk, Universitetet i Oslo

package no.uio.ifi.asp.runtime;

// For part 4:

import java.util.ArrayList;
import java.util.HashMap;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeScope {
    private RuntimeScope outer;
    private HashMap<String,RuntimeValue> decls = new HashMap<>();
    private ArrayList<String> globalNames = new ArrayList<>();

    public RuntimeScope() {
	// Used by the library (and when testing expressions in part 3)
	outer = null;
    }

    public RuntimeScope(RuntimeScope oScope) {
	// Used by all user scopes
	outer = oScope;
    }


    public void assign(String id, RuntimeValue val) {
	decls.put(id, val);
    }

    public RuntimeValue find(String id, AspSyntax where) {
	if (globalNames.contains(id)) {
	    RuntimeValue v = Main.globalScope.decls.get(id);
	    if (v != null) return v;
	} else {
	    RuntimeScope scope = this;
	    while (scope != null) {
		RuntimeValue v = scope.decls.get(id);
		if (v != null) return v;
		scope = scope.outer;
	    }
	}
	RuntimeValue.runtimeError("Name " + id + " not defined!", where);
	return null;  // Required by the compiler.
    }

    public boolean hasDefined(String id) {
	return decls.get(id) != null;
    }

    public boolean hasGlobalName(String id) {
	return globalNames.contains(id);
    }

    public void registerGlobalName(String id) {
	globalNames.add(id);
    }
}
