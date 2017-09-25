package com.start.laundryapp;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nijats87 on 9/22/2017.
 */

public class ServerAdress {

    public static final URL server_URL;
        static {
            try {
                server_URL = new URL("http://138.201.157.254:8017/");
            } catch (MalformedURLException exc) {
                throw new Error(exc);
            }
    }
}

