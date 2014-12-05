/**
 * Copyright (c) Canadian Light Source, Inc. All rights reserved. - see
 * license.txt for details.
 *
 * Description: AbstractDeviceFactoryImpl class.
 * 
 */
package net.realmproject.dcm.device.catalog.impl;


import net.realmproject.dcm.device.catalog.DeviceCatalog;
import net.realmproject.dcm.device.catalog.DeviceCatalogImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author maxweld
 *
 */
public abstract class AbstractDeviceCatalogImpl implements DeviceCatalogImpl {

    protected Log log = LogFactory.getLog(getClass());

    protected AbstractDeviceCatalogImpl() {
        DeviceCatalog.setDeviceFactoryImpl(this);
    }

}
