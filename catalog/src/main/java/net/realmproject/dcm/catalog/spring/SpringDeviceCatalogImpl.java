/**
 * Copyright 2014 Realm Project
 * 
 * This file is part of the Device Control Module (DCM).
 * 
 * DCM is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * DCM is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * DCM. If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.realmproject.dcm.catalog.spring;


import net.realmproject.dcm.catalog.DeviceCatalog;

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
        if (DeviceCatalog.getDeviceFactoryImpl() == null) {
            new SpringDeviceCatalogImpl(new String[] { configPath });
            return true;
        }
        return false;
    }

    public static boolean loadXmlApplicationContext(String[] configPaths) throws BeansException {
        if (DeviceCatalog.getDeviceFactoryImpl() == null) {
            new SpringDeviceCatalogImpl(configPaths);
            return true;
        }
        return false;
    }

    public static boolean loadXmlApplicationContext(String configPath, ApplicationContext parent) throws BeansException {
        if (DeviceCatalog.getDeviceFactoryImpl() == null) {
            new SpringDeviceCatalogImpl(new String[] { configPath }, parent);
            return true;
        }
        return false;
    }

    public static boolean loadXmlApplicationContext(String[] configPaths, ApplicationContext parent)
            throws BeansException {
        if (DeviceCatalog.getDeviceFactoryImpl() == null) {
            new SpringDeviceCatalogImpl(configPaths, parent);
            return true;
        }
        return false;
    }

    private SpringDeviceCatalogImpl(String[] configPaths) throws BeansException {
        super();
        applicationContext = new FileSystemXmlApplicationContext(configPaths);
        ((AbstractApplicationContext) applicationContext).registerShutdownHook();
    }

    private SpringDeviceCatalogImpl(String[] configPaths, ApplicationContext parent) throws BeansException {
        super();
        applicationContext = new FileSystemXmlApplicationContext(configPaths, parent);
        ((AbstractApplicationContext) applicationContext).registerShutdownHook();
    }
}
