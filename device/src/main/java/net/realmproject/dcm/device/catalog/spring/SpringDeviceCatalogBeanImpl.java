/** Copyright (c) Canadian Light Source, Inc.  All rights reserved.
 *  - see license.txt for details.
 *
 * Description:
 *    SpringDeviceFactoryBeanImpl class.
 */
package net.realmproject.dcm.device.catalog.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author maxweld
 *
 */
public class SpringDeviceCatalogBeanImpl extends AbstractSpringDeviceCatalogImpl implements ApplicationContextAware {

	public SpringDeviceCatalogBeanImpl() {
		super();
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		((AbstractApplicationContext)applicationContext).registerShutdownHook();
	}	
}
