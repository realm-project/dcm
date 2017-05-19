package net.realmproject.dcm.parcel.impl.flow.branch;

import java.util.Map;
import java.util.function.Function;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.realmproject.dcm.parcel.core.Parcel;

public class JavaScriptBranchResolver implements Function<Parcel<?>, String> {

	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
	private final String script;
	
	public JavaScriptBranchResolver(String javascript) {
		this.script = javascript;
	}

	public String apply(Parcel<?> parcel) {
	    Bindings b = ENGINE.createBindings();
	    b.put("parcel", parcel);
	    try {
			ENGINE.eval(script, b);
			Object result = b.get("branch");
			if (result instanceof String) {
				return (String) result;
			}
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
}
