package com.share.shamir.util.eosio.java.abieos.serialization;

import com.google.protobuf.UInt64Value;
import io.jafka.jeos.EosApi;
import io.jafka.jeos.EosApiFactory;
import io.jafka.jeos.LocalApi;
import io.jafka.jeos.convert.Packer;
import io.jafka.jeos.core.common.SignArg;
import io.jafka.jeos.core.common.transaction.PackedTransaction;
import io.jafka.jeos.core.common.transaction.TransactionAction;
import io.jafka.jeos.core.common.transaction.TransactionAuthorization;
import io.jafka.jeos.core.request.chain.transaction.PushTransactionRequest;
import io.jafka.jeos.core.response.chain.ChainInfo;
import io.jafka.jeos.core.response.chain.transaction.PushedTransaction;
import io.jafka.jeos.util.KeyUtil;
import io.jafka.jeos.util.Raw;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Jeos {
    static String chainUrl = "http://10.28.217.174:10101";
    Jeos(String chainUrl) {
        this.chainUrl = chainUrl;
        EosApi client = EosApiFactory.create(chainUrl, chainUrl, chainUrl);
        ChainInfo info = client.getChainInfo();
        System.out.println("chain info:"+info);
    }
    private static String sign(String privateKey, SignArg arg, PackedTransaction t) {
        Raw raw = Packer.packPackedTransaction(arg.getChainId(), t);
        raw.pack(ByteBuffer.allocate(33).array());
        String hash = KeyUtil.signHash(privateKey, raw.bytes());
        return hash;
    }
    public static void main(String[] args) throws Exception {
        EosApi client = EosApiFactory.create("http://10.28.217.174:10101", "http://10.28.217.174:10101", "http://10.28.217.174:10101");
        ChainInfo info = client.getChainInfo();
        System.out.println("chain info:"+info);
//        callGetContract((long) 1);
//        callCreateContract("123","321","322");
        callEraseContract((long) 1);
    }
    static void callCreateContract(String username, String shamirKey, String shamirUserKey) throws Exception{
        // --- get the current state of blockchain
        EosApi eosApi = EosApiFactory.create(chainUrl, chainUrl, chainUrl);
        SignArg arg = eosApi.getSignArg(120);
        System.out.println(eosApi.getObjectMapper().writeValueAsString(arg));

        // --- sign the transation of token tansfer
        String privateKey = "5J61mY3dcgHb4egBYVWz4av68y24JzqteKRHMFrDXyhmQdbkhbr";//replace the real private key
        String from = "alice";
        LocalApi localApi = EosApiFactory.createLocalApi();

        // ① pack transfer data
        Raw raw = new Raw();
        raw.packName("alice");
        raw.pack(username);
        raw.pack(shamirKey);
        raw.pack(shamirUserKey);
        raw.pack("string");
        raw.pack(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String transferData = raw.toHex();

        // ③ create the authorization
        List<TransactionAuthorization> authorizations = Arrays.asList(new TransactionAuthorization(from, "active"));

        // ④ build the all actions
        List<TransactionAction> actions = Arrays.asList(//
                new TransactionAction("shamirmanage", "create", authorizations, transferData)
        );

        // ⑤ build the packed transaction
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setExpiration(arg.getHeadBlockTime().plusSeconds(arg.getExpiredSecond()));
        packedTransaction.setRefBlockNum(arg.getLastIrreversibleBlockNum());
        packedTransaction.setRefBlockPrefix(arg.getRefBlockPrefix());

        packedTransaction.setMaxNetUsageWords(0);
        packedTransaction.setMaxCpuUsageMs(0);
        packedTransaction.setDelaySec(0);
        packedTransaction.setActions(actions);

        String hash = sign(privateKey, arg, packedTransaction);
        PushTransactionRequest req = new PushTransactionRequest();
        req.setTransaction(packedTransaction);
        req.setSignatures(Arrays.asList(hash));

        System.out.println(localApi.getObjectMapper().writeValueAsString(req));

        // --- push the signed-transaction to the blockchain
        PushedTransaction pts = eosApi.pushTransaction(req);
        System.out.println(localApi.getObjectMapper().writeValueAsString(pts));
    }

    static void callGetContract(Long id) throws Exception{
        // --- get the current state of blockchain
        EosApi eosApi = EosApiFactory.create("http://10.28.217.174:10101", "http://10.28.217.174:10101", "http://10.28.217.174:10101");
        SignArg arg = eosApi.getSignArg(120);
        System.out.println(eosApi.getObjectMapper().writeValueAsString(arg));

        // --- sign the transation of token tansfer
        String privateKey = "5J61mY3dcgHb4egBYVWz4av68y24JzqteKRHMFrDXyhmQdbkhbr";//replace the real private key
        String from = "alice";
        LocalApi localApi = EosApiFactory.createLocalApi();

        // ① pack transfer data
        Raw raw = new Raw();
        raw.packUint64(id);
        String transferData = raw.toHex();

        // ③ create the authorization
        List<TransactionAuthorization> authorizations = Arrays.asList(new TransactionAuthorization(from, "active"));

        // ④ build the all actions
        List<TransactionAction> actions = Arrays.asList(//
                new TransactionAction("shamirmanage", "get", authorizations, transferData)
        );

        // ⑤ build the packed transaction
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setExpiration(arg.getHeadBlockTime().plusSeconds(arg.getExpiredSecond()));
        packedTransaction.setRefBlockNum(arg.getLastIrreversibleBlockNum());
        packedTransaction.setRefBlockPrefix(arg.getRefBlockPrefix());

        packedTransaction.setMaxNetUsageWords(0);
        packedTransaction.setMaxCpuUsageMs(0);
        packedTransaction.setDelaySec(0);
        packedTransaction.setActions(actions);

        String hash = sign(privateKey, arg, packedTransaction);
        PushTransactionRequest req = new PushTransactionRequest();
        req.setTransaction(packedTransaction);
        req.setSignatures(Arrays.asList(hash));

        System.out.println(localApi.getObjectMapper().writeValueAsString(req));

        // --- push the signed-transaction to the blockchain
        PushedTransaction pts = eosApi.pushTransaction(req);
        System.out.println(localApi.getObjectMapper().writeValueAsString(pts));
    }

    static void callEraseContract(Long id) throws Exception{
        // --- get the current state of blockchain
        EosApi eosApi = EosApiFactory.create(chainUrl, chainUrl, chainUrl);
        SignArg arg = eosApi.getSignArg(120);
        System.out.println(eosApi.getObjectMapper().writeValueAsString(arg));

        // --- sign the transation of token tansfer
        String privateKey = "5J61mY3dcgHb4egBYVWz4av68y24JzqteKRHMFrDXyhmQdbkhbr";//replace the real private key
        String from = "alice";
        LocalApi localApi = EosApiFactory.createLocalApi();

        // ① pack transfer data
        Raw raw = new Raw();
        raw.packName("alice");
        raw.packUint64(id);
        String transferData = raw.toHex();

        // ③ create the authorization
        List<TransactionAuthorization> authorizations = Arrays.asList(new TransactionAuthorization(from, "active"));

        // ④ build the all actions
        List<TransactionAction> actions = Arrays.asList(//
                new TransactionAction("shamirmanage", "erase", authorizations, transferData)
        );

        // ⑤ build the packed transaction
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setExpiration(arg.getHeadBlockTime().plusSeconds(arg.getExpiredSecond()));
        packedTransaction.setRefBlockNum(arg.getLastIrreversibleBlockNum());
        packedTransaction.setRefBlockPrefix(arg.getRefBlockPrefix());

        packedTransaction.setMaxNetUsageWords(0);
        packedTransaction.setMaxCpuUsageMs(0);
        packedTransaction.setDelaySec(0);
        packedTransaction.setActions(actions);

        String hash = sign(privateKey, arg, packedTransaction);
        PushTransactionRequest req = new PushTransactionRequest();
        req.setTransaction(packedTransaction);
        req.setSignatures(Arrays.asList(hash));

        System.out.println(localApi.getObjectMapper().writeValueAsString(req));

        // --- push the signed-transaction to the blockchain
        PushedTransaction pts = eosApi.pushTransaction(req);
        System.out.println(localApi.getObjectMapper().writeValueAsString(pts));
    }
}
