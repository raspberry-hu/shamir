package com.share.shamir.util.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ipfsUtils {
    private static io.ipfs.api.IPFS IPFS = new IPFS("/ip4/10.28.217.174/tcp/5001");

    public static void main(String[] args) throws IOException {
        IPFS.refs.local();
        String temp = "测试";
        String upload = upload(temp.getBytes());
        System.out.println(upload);
    }

    public static String upload(String fileName) throws IOException {
        NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(fileName));
        MerkleNode addResult = IPFS.add(file).get(0);
        return addResult.hash.toString();
    }

    public static String upload(byte[] data) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper(data);
        MerkleNode addResult = IPFS.add(file).get(0);
        return addResult.hash.toString();
    }

    public static byte[] download(String hash) {
        byte[] data = null;
        try {
            data = IPFS.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void download(String hash, String destFile) {
        byte[] data = null;
        try {
            data = IPFS.cat(Multihash.fromBase58(hash));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (data != null && data.length > 0) {
            File file = new File(destFile);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                fos.write(data);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
