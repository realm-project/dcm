package net.realmproject.dcm.parcel.impl.flow.transform.javascript;

import java.util.function.Function;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.realmproject.dcm.parcel.core.Parcel;

public class JavaScriptParcelTransform implements Function<Parcel<?>, Parcel<?>> {

	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
	private String script;

	public JavaScriptParcelTransform(String script) {
		this.script = script;
	}
	
	@Override
	public Parcel<?> apply(Parcel<?> parcel) {
	    Bindings b = ENGINE.createBindings();
	    b.put("parcel", parcel);
	    try {
			ENGINE.eval(script, b);
			return parcel;
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	    
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	
	
	

}
