/*
 * Copyright (C) 2020 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package systems.hotech.retrofit.okhttp3.internal.platform;

import android.annotation.SuppressLint;
import android.net.ssl.SSLSockets;

import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

import java.util.List;

import javax.annotation.Nullable;
import javax.net.ssl.*;

import systems.hotech.retrofit.okhttp3.Protocol;

/**
 * Android 10+.
 */
@SuppressLint("NewApi")
class Android10Platform extends AndroidPlatform {
    Android10Platform(Class<?> sslParametersClass) {
        super(sslParametersClass, null, null, null, null, null);
    }

    @SuppressLint("NewApi")
    @IgnoreJRERequirement
    @Override
    public void configureTlsExtensions(
            SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        enableSessionTickets(sslSocket);

        SSLParameters sslParameters = sslSocket.getSSLParameters();

        // Enable ALPN.
        String[] protocolsArray = Platform.alpnProtocolNames(protocols).toArray(new String[0]);
        sslParameters.setApplicationProtocols(protocolsArray);

        sslSocket.setSSLParameters(sslParameters);
    }

    private void enableSessionTickets(SSLSocket sslSocket) {
        if (SSLSockets.isSupportedSocket(sslSocket)) {
            SSLSockets.setUseSessionTickets(sslSocket, true);
        }
    }

    @IgnoreJRERequirement
    @Override
    public @Nullable
    String getSelectedProtocol(SSLSocket socket) {
        String alpnResult = socket.getApplicationProtocol();

        if (alpnResult == null || alpnResult.isEmpty()) {
            return null;
        }

        return alpnResult;
    }

    public static @Nullable
    Platform buildIfSupported() {
        try {
            if (getSdkInt() >= 29) {
                Class<?> sslParametersClass =
                        Class.forName("com.android.org.conscrypt.SSLParametersImpl");

                return new Android10Platform(sslParametersClass);
            }
        } catch (ReflectiveOperationException ignored) {
        }

        return null; // Not an Android 10+ runtime.
    }
}
