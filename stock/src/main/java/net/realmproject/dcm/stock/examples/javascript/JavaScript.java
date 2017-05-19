package net.realmproject.dcm.stock.examples.javascript;

import java.util.concurrent.ExecutionException;

import net.realmproject.dcm.parcel.core.link.ParcelLink;
import net.realmproject.dcm.parcel.core.service.ParcelService;
import net.realmproject.dcm.parcel.core.transform.ParcelTransformLink;
import net.realmproject.dcm.parcel.impl.branch.IParcelBranch;
import net.realmproject.dcm.parcel.impl.branch.JavaScriptBranchResolver;
import net.realmproject.dcm.parcel.impl.service.IParcelService;
import net.realmproject.dcm.parcel.impl.transform.IParcelTransformLink;
import net.realmproject.dcm.parcel.impl.transform.JavaScriptParcelTransform;

public class JavaScript {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		//Stupid example where the service doubles even length words and reverses odd length words
		
	
		ParcelLink doubler = new IParcelTransformLink(new JavaScriptParcelTransform("s = parcel.getPayload(); parcel.setPayload(s+s);"));
		ParcelLink reverser= new IParcelTransformLink(new JavaScriptParcelTransform("s = parcel.getPayload(); parcel.setPayload(s.split('').reverse().join(''));"));
		

		IParcelBranch lengthBranch = new IParcelBranch();
		lengthBranch.link("even", doubler);
		lengthBranch.link("odd", reverser);
		lengthBranch.setBranchResolver(new JavaScriptBranchResolver("s = parcel.getPayload(); if (s.length() % 2 == 0) {branch='even';} else {branch='odd';}"));
		
		
		ParcelService<String, String> wordy = new IParcelService<>();
		wordy.setId("wordy");
		
		
		wordy.link(lengthBranch);
		doubler.link(wordy);
		reverser.link(wordy);
		
		
		System.out.println(wordy.call("double").get());
		System.out.println(wordy.call("reverse").get());
		
	}
	
}
