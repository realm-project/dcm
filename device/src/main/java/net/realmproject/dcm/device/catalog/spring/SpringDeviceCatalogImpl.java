/** Copyright (c) Canadian Light Source, Inc.  All rights reserved.
 *  - see license.txt for details.
 *
 * Description:
 *    SpringDeviceFactoryImpl class.
 */
package net.realmproject.dcm.device.catalog.spring;

import net.realmproject.dcm.device.catalog.DeviceCatalog;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author maxweld
 * 
 */
public class SpringDeviceCatalogImpl extends AbstractSpringDeviceCatalogImpl {
	
	public static boolean loadXmlApplicationContext(String configPath) throws BeansException {
		if(DeviceCatalog.getDeviceFactoryImpl() == null) {
			new SpringDeviceCatalogImpl(new String[] { configPath });
			return true;
		}
		return false;
	}
	
	public static boolean loadXmlApplicationContext(String[] configPaths) throws BeansException {
		if(DeviceCatalog.getDeviceFactoryImpl() == null) {
			new SpringDeviceCatalogImpl(configPaths);
			return true;
		}
		return false;
	}
	
	public static boolean loadXmlApplicationContext(String configPath, ApplicationContext parent) throws BeansException {
		if(DeviceCatalog.getDeviceFactoryImpl() == null) {
			new SpringDeviceCatalogImpl(new String[] { configPath }, parent);
			return true;
		}
		return false;
	}
	
	public static boolean loadXmlApplicationContext(String[] configPaths, ApplicationContext parent) throws BeansException {
		if(DeviceCatalog.getDeviceFactoryImpl() == null) {
			new SpringDeviceCatalogImpl(configPaths, parent);
			return true;
		}
		return false;
	}
	
	private SpringDeviceCatalogImpl(String[] configPaths) throws BeansException {
		super();
		applicationContext = new FileSystemXmlApplicationContext(configPaths);
		((AbstractApplicationContext)applicationContext).registerShutdownHook();
	}
	
	private SpringDeviceCatalogImpl(String[] configPaths, ApplicationContext parent) throws BeansException {
		super();
		applicationContext = new FileSystemXmlApplicationContext(configPaths, parent);
		((AbstractApplicationContext)applicationContext).registerShutdownHook();
	}
}

