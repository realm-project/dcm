package net.realmproject.dcm.stock.examples.ide.graph.actions;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

public class NoopAction implements WidgetAction {

	public NoopAction() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public State mouseClicked(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mousePressed(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mouseReleased(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mouseEntered(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mouseExited(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mouseDragged(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mouseMoved(Widget widget, WidgetMouseEvent event) {
		return State.REJECTED;
	}

	@Override
	public State mouseWheelMoved(Widget widget, WidgetMouseWheelEvent event) {
		return State.REJECTED;
	}

	@Override
	public State keyTyped(Widget widget, WidgetKeyEvent event) {
		return State.REJECTED;
	}

	@Override
	public State keyPressed(Widget widget, WidgetKeyEvent event) {
		return State.REJECTED;
	}

	@Override
	public State keyReleased(Widget widget, WidgetKeyEvent event) {
		return State.REJECTED;
	}

	@Override
	public State focusGained(Widget widget, WidgetFocusEvent event) {
		return State.REJECTED;
	}

	@Override
	public State focusLost(Widget widget, WidgetFocusEvent event) {
		// TODO Auto-generated method stub
		return State.REJECTED;
	}

	@Override
	public State dragEnter(Widget widget, WidgetDropTargetDragEvent event) {
		return State.REJECTED;
	}

	@Override
	public State dragOver(Widget widget, WidgetDropTargetDragEvent event) {
		return State.REJECTED;
	}

	@Override
	public State dropActionChanged(Widget widget, WidgetDropTargetDragEvent event) {
		return State.REJECTED;
	}

	@Override
	public State dragExit(Widget widget, WidgetDropTargetEvent event) {
		return State.REJECTED;
	}

	@Override
	public State drop(Widget widget, WidgetDropTargetDropEvent event) {
		return State.REJECTED;
	}

}
