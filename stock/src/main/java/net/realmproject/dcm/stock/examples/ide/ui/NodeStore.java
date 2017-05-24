package net.realmproject.dcm.stock.examples.ide.ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import javassist.Modifier;
import net.realmproject.dcm.parcel.core.ParcelNode;

public class NodeStore {

	Reflections reflections;
	List<Class<? extends ParcelNode>> nodeClasses;
	
	public NodeStore() {

		reflections = new Reflections("net.realmproject.dcm");
		Set<Class<? extends ParcelNode>> allClasses = reflections.getSubTypesOf(ParcelNode.class);
		nodeClasses = allClasses.stream().filter(c -> !(c.isInterface() || isAbstract(c) || c.isAnonymousClass())).collect(Collectors.toList());
		nodeClasses.sort((a, b) -> a.getSimpleName().compareTo(b.getSimpleName()));

	}
	
	public List<Class<? extends ParcelNode>> getParcelNodeClasses() {
		return nodeClasses;
	}
	
	public static void main(String[] args) {
		NodeStore n = new NodeStore();
		System.out.println(n.nodeClasses);
	}
	
	private boolean isAbstract(Class<?> clazz) {
		return Modifier.isAbstract(clazz.getModifiers());
	}
	
}
