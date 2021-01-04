package com.peterzuo.utils;

import java.util.regex.Pattern;

/*
* IP address is a string in the form "A.B.C.D", where the value of A, B, C, and D may range from 0 to 255. 
* Leading zeros are allowed. The length of A, B, C, or D can't be greater than 3. 
*/
public class IpValidator {

    private static String ipAddressPatternString;
    private static Pattern ipAddressPattern;

    static {
        ipAddressPatternString="^(([0-1]\\d{0,2}|\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}([0-1]\\d{0,2}|\\d{1,2}|2[0-4]\\d|25[0-5])$";
        ipAddressPattern = Pattern.compile(ipAddressPatternString);
    }

    private IpValidator(){
        throw new IllegalStateException("Utility Class");
    }

    public static boolean validateIp(String ipAddress){
        return ipAddressPattern.matcher(ipAddress).matches();
    }
}
