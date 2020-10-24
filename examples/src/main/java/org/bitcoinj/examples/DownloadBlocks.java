package org.bitcoinj.examples;

import org.bitcoinj.core.*;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
import org.bitcoinj.store.MemoryBlockStore;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DownloadBlocks {
    public static void main(String[] args) throws BlockStoreException, ExecutionException, InterruptedException {
        System.out.println("Connecting to node");
        final NetworkParameters params = MainNetParams.get();
        BlockStore blockStore = new MemoryBlockStore(params);
        BlockChain chain = new BlockChain(params, blockStore);
        PeerGroup peerGroup = new PeerGroup(params, chain);
        peerGroup.addPeerDiscovery(new DnsDiscovery(params));
        peerGroup.setUseLocalhostPeerWhenPossible(false);

        peerGroup.start();
        peerGroup.waitForPeers(10).get();
        Peer peer = peerGroup.getDownloadPeer();

        for (String hash : new String[]{
                "00000000000107a3186071032033500c2fd62a000bfb13b5cfdf831f9e301854",
                "0000000000007d6856bce66915b0eaed443e1df8f9e8337abcd95406a51b9afa",
                "000000000000de50219a595309b2c786793d68437a60357930bf61e3bfd1f6eb", // <<<<< doesn't work with xazabj-0.13 branch
        }) {

            Sha256Hash blockHash = Sha256Hash.wrap(hash);
            Future<Block> future = peer.getBlock(blockHash);
            System.out.println("Waiting for node to send us the requested block: " + blockHash);
            Block block = future.get();
            System.out.println(block);
            try {
                UnsafeByteArrayOutputStream bos = new UnsafeByteArrayOutputStream(block.getMessageSize());
                block.bitcoinSerialize(bos);
                System.out.println(Utils.HEX.encode(bos.toByteArray()));
            } catch(IOException x) {
                throw new RuntimeException(x.getMessage());
            }
        }
        peerGroup.stopAsync();
    }
}
