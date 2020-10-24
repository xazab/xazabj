package org.bitcoinj.crypto;

import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.xazabj.bls.BLS;
import org.xazabj.bls.PrivateKey;
import org.xazabj.bls.PublicKey;
import org.junit.Test;

import java.math.BigInteger;
import java.util.Random;

import static com.google.common.base.Preconditions.checkState;
import static org.junit.Assert.assertEquals;

public class BLSLazyPublicKeyTest {

    private static NetworkParameters PARAMS = MainNetParams.get();

    static {
        Context context = new Context(PARAMS);
        BLS.Init();
    }

    private byte [] getRandomSeed(int size) {
        Random rand = new Random();
        BigInteger result = new BigInteger((size) *8 - 1, rand); // (2^4-1) = 15 is the maximum value
        byte [] bytes = new byte [32];
        System.arraycopy(result.toByteArray(), 0, bytes, 0, result.toByteArray().length);
        return bytes;
    }

    @Test
    public void testLazyPublicKey() {
        // Generate a random public key
        byte [] seed = getRandomSeed(32);
        PrivateKey sk = PrivateKey.FromSeed(seed, seed.length);
        PublicKey pk = sk.GetPublicKey();

        byte [] publicKeyBytes = pk.Serialize();
        BLSPublicKey publicKey = new BLSPublicKey(PARAMS, publicKeyBytes, 0);

        // create a lazy public key from the bytes only
        BLSLazyPublicKey lazyPublicKeyFromBytes = new BLSLazyPublicKey(PARAMS, publicKeyBytes, 0);
        checkState(!lazyPublicKeyFromBytes.isPublicKeyInitialized()); // the BLS object should not be initialized

        // create a lazy public key from a BLS public key object
        BLSLazyPublicKey lazyPublicKeyFromObject = new BLSLazyPublicKey(publicKey);
        checkState(lazyPublicKeyFromObject.isPublicKeyInitialized()); // the BLS object should be initialized

        //ensure that the serialized string forms match
        assertEquals(lazyPublicKeyFromBytes.toString(), lazyPublicKeyFromObject.toString());

        // initialize the lazy public key that was created from bytes
        BLSPublicKey initializedPublicKey = lazyPublicKeyFromBytes.getPublicKey();
        checkState(lazyPublicKeyFromBytes.isPublicKeyInitialized()); // the BLS object should be initialized
        assertEquals(lazyPublicKeyFromBytes.toString(), lazyPublicKeyFromObject.toString());

    }
}
