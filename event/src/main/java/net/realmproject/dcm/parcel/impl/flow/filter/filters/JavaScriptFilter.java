package net.realmproject.dcm.parcel.impl.flow.filter.filters;

import java.util.function.Predicate;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.realmproject.dcm.parcel.core.Parcel;

public class JavaScriptFilter implements Predicate<Parcel<?>> {

	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
	private String script;
	
	public JavaScriptFilter(String script) {
		this.script = script;
	}
	
	@Override
	public boolean test(Parcel<?> t) {
	    Bindings b = ENGINE.createBindings();
	    b.put("parcel", t);
	    try {
			ENGINE.eval(script, b);
			Object result = b.get("accept");
			if (result instanceof Boolean) {
				return (Boolean) result;
			}
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return false;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	
	
}
