package org.eclipse.scava.crossflow.restmule.core.data.okhttp3.wrappers;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import okhttp3.CipherSuite;
import okhttp3.Handshake;
import okhttp3.TlsVersion;

public class OkHttp3Handshake implements Serializable {

    // Handshake (non serializable) <-- okHttp3Response.getHandshake()
    // Handshake <-- OkHttp3Handshake.get()
    //      String <-- handshake.tlsVersionJavaName().javaName()
    //      String <-- handshake.cipherSuiteJavaName().javaName()
    //      List<Certificates> <-- handshake.peerCertificates()
    //      List<Certificates> <-- handshake.localCertificates()
    // Note: Certificates are serializable

    private static final Logger LOG = LogManager.getLogger(OkHttp3Handshake.class);

    public String tlsVersionJavaName;
    public String cipherSuiteJavaName;
    public List<Certificate> peerCertificates;
    public List<Certificate> localCertificates;

    public OkHttp3Handshake() {
        // default constructor for json framework
    }

    public OkHttp3Handshake(Handshake handshake) {
        this.tlsVersionJavaName = handshake.tlsVersion().javaName();
        this.cipherSuiteJavaName = handshake.cipherSuite().javaName();
        this.peerCertificates = handshake.peerCertificates();
        this.localCertificates = handshake.localCertificates();
    }

    public Handshake get() {
        TlsVersion tlsVersion = TlsVersion.forJavaName(tlsVersionJavaName);
        CipherSuite cipherSuite = CipherSuite.forJavaName(cipherSuiteJavaName);
        return Handshake.get(tlsVersion, cipherSuite, peerCertificates, localCertificates);
    }

}
