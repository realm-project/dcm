package net.realmproject.dcm.stock.examples.ui.actions;

import java.util.function.BiFunction;

import org.netbeans.api.visual.widget.Widget;

public class ClickAction extends NoopAction {

	private BiFunction<Widget, WidgetMouseEvent, State> function;
	public ClickAction(BiFunction<Widget, WidgetMouseEvent, State> function) {
		this.function = function;
	}
	
	@Override
	public State mouseClicked(Widget widget, WidgetMouseEvent event) {
		return function.apply(widget, event);
	}
}
