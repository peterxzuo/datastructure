package com.peterzuo.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.peterzuo.utils.IpValidator;

import org.junit.jupiter.api.Test;

public class IpValidatorTests {
    @Test
    public void testValidateIPs(){
        String [] validIps = new String[]{
            "0.0.0.0",
            "255.255.255.255",
            "192.168.1.1",
            "172.1.1.1",
            "127.0.0.0",
            "8.12.3.4",
            "0.0.155.155"
        };

        for (int i=0; i<validIps.length; i++)
            assertTrue(IpValidator.validateIp(validIps[i]));
        
    }

    @Test
    public void testInvalidateIPs(){
        String [] invalidIps = new String[]{
            "0.0.0.",
            "255..255.255",
            "256.168.1.1",
            "172.1",
            "127.0.0.1560",
            "8x12.3.4",
            "0000.0.155.155"
        };

        for (int i=0; i<invalidIps.length; i++)
            assertFalse(IpValidator.validateIp(invalidIps[i]));
        
    }
}
