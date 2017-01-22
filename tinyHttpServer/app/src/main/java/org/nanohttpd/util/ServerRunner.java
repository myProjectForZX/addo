package org.nanohttpd.util;

/*
 * #%L
 * NanoHttpd-Webserver
 * %%
 * Copyright (C) 2012 - 2015 nanohttpd
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the nanohttpd nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import android.content.Context;
import android.content.res.AssetManager;

import com.szp.tinyhttpserver.LogUtils;
import com.szp.tinyhttpserver.NanoHttpServer;
import com.szp.tinyhttpserver.NotStartNanoServer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.nanohttpd.protocols.http.NanoHTTPD;

public class ServerRunner {
    private static NanoHTTPD HttpServer;
    /**
     * logger to log to.
     */
    private static final Logger LOG = Logger.getLogger(ServerRunner.class.getName());

    public static void executeInstance(NanoHTTPD server, AssetManager am, Context context){
        if(HttpServer == null)
            HttpServer = server;
        else
            return;

        try {
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        } catch (IOException ioe) {
            LogUtils.e("ServerRunner", "start nano http server error");
        }

        if(server instanceof NanoHttpServer) {
            ((NanoHttpServer) server).setAssetMgr(am);
            ((NanoHttpServer) server).setContext(context);
        }
    }

    public static void exitInstance() {
        if(HttpServer != null)
            HttpServer.stop();
        HttpServer = null;
    }

    public static <T extends NanoHTTPD> void run(Class<T> serverClass, AssetManager am, Context context) {
        try {
            executeInstance(serverClass.newInstance(), am, context);
        } catch (Exception e) {
            ServerRunner.LOG.log(Level.SEVERE, "Could not create server", e);
        }
    }

    public static <T extends NanoHTTPD> void exit() {
        try {
            exitInstance();
        } catch (Exception e) {
            ServerRunner.LOG.log(Level.SEVERE, "Could not create server", e);
        }
    }
}
