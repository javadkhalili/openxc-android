package com.openxc.remote.sources.usb;

import java.lang.InterruptedException;

import java.net.URI;
import java.net.URISyntaxException;

import com.openxc.remote.sources.usb.UsbVehicleDataSource;
import com.openxc.remote.sources.SourceCallback;
import com.openxc.remote.sources.VehicleDataSourceException;
import com.openxc.remote.sources.VehicleDataSourceResourceException;
import com.openxc.remote.sources.VehicleDataSource;

import junit.framework.Assert;

import android.test.AndroidTestCase;

import android.test.suitebuilder.annotation.SmallTest;

public class UsbVehicleDataSourceTest extends AndroidTestCase {
    URI deviceUri;
    URI malformedDeviceUri;
    URI incorrectSchemeUri;
    UsbVehicleDataSource source;
    SourceCallback callback;
    Thread thread;

    @Override
    protected void setUp() {
        try {
            deviceUri = new URI("usb://04d8/0053");
            malformedDeviceUri = new URI("usb://04d8");
            incorrectSchemeUri = new URI("file://04d8");
        } catch(URISyntaxException e) {
            Assert.fail("Couldn't construct resource URIs: " + e);
        }

        callback = new SourceCallback() {
            public void receive(String name, Object value, Object event) {
            }
        };
    }

    @Override
    protected void tearDown() {
        if(source != null) {
            source.stop();
        }
        if(thread != null) {
            try {
                thread.join();
            } catch(InterruptedException e) {}
        }
    }

    @SmallTest
    public void testDefaultDevice() throws VehicleDataSourceException {
        source = new UsbVehicleDataSource(getContext(), callback);
    }

    @SmallTest
    public void testCustomDevice() throws VehicleDataSourceException {
        source = new UsbVehicleDataSource(getContext(), callback,
                deviceUri);
    }

    @SmallTest
    public void testMalformedUri() throws VehicleDataSourceException {
        try {
            new UsbVehicleDataSource(getContext(), callback,
                    malformedDeviceUri);
        } catch(VehicleDataSourceResourceException e) {
            return;
        }
        Assert.fail("Expected a VehicleDataSourceResourceException");
    }

    @SmallTest
    public void testUriWithBadScheme() throws VehicleDataSourceException {
        try {
            new UsbVehicleDataSource(getContext(), callback,
                    incorrectSchemeUri);
        } catch(VehicleDataSourceResourceException e) {
            return;
        }
        Assert.fail("Expected a VehicleDataSourceResourceException");
    }
}
