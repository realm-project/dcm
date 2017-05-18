package net.realmproject.dcm.parcel.impl.branch;

import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.realmproject.dcm.parcel.core.Parcel;
import net.realmproject.dcm.parcel.core.ParcelReceiver;

public class JavaScriptParcelBranch extends AbstractParcelBranch {

	private static final ScriptEngine ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");
	private final String script;
	
	public JavaScriptParcelBranch(Map<String, ParcelReceiver> receivers, String javascript) {
		super(receivers);
		this.script = javascript;
	}

	@Override
	protected String getBranch(Parcel<?> parcel) {
	    Bindings b = ENGINE.createBindings();
	    b.put("parcel", parcel);
	    try {
			Object result = ENGINE.eval(script, b);
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
