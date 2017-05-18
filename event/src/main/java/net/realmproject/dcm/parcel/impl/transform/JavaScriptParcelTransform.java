package net.realmproject.dcm.parcel.impl.transform;

import java.util.function.Function;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.realmproject.dcm.parcel.core.Parcel;

public class JavaScriptParcelTransform implements Function<Parcel<?>, Parcel<?>> {

	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
	private final String script;

	public JavaScriptParcelTransform(String script) {
		this.script = script;
	}
	
	@Override
	public Parcel<?> apply(Parcel<?> t) {
	    Bindings b = ENGINE.createBindings();
	    b.put("parcel", t);
	    try {
			Object result = ENGINE.eval(script, b);
			if (result instanceof Parcel) {
				return (Parcel<?>) result;
			}
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	    
	}

}
