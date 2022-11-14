package com.share.shamir.util.eosio.java.abieos.serialization;

import one.block.eosiojava.error.session.TransactionPrepareError;
import one.block.eosiojava.error.session.TransactionSignAndBroadCastError;
import one.block.eosiojava.implementations.ABIProviderImpl;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojava.models.rpcProvider.Action;
import one.block.eosiojava.models.rpcProvider.Authorization;
import one.block.eosiojava.models.rpcProvider.response.SendTransactionResponse;
import one.block.eosiojava.session.TransactionProcessor;
import one.block.eosiojava.session.TransactionSession;
import one.block.eosiojavaabieosserializationprovider.AbiEosSerializationProviderImpl;
import one.block.eosiojavarpcprovider.implementations.EosioJavaRpcProviderImpl;
import one.block.eosiosoftkeysignatureprovider.SoftKeySignatureProviderImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class provider {

//    public interface TransactionTaskCallback {
//        void tes();
//    }
//    public static void main(String[] args) throws Exception {
//        tes();
//    }

    public static void main(String[] args) throws Exception {
        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://172.17.0.9:8888");
        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
        IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
        ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();

        ((SoftKeySignatureProviderImpl) signatureProvider).importKey("5KQwrPbwdL6PhXujxW37FSSQZ1JiwsST4cqQzDeyXtP79zkvFD3");

        TransactionSession session = new TransactionSession(
                serializationProvider,
                rpcProvider,
                abiProvider,
                signatureProvider
        );

        TransactionProcessor processor = session.getTransactionProcessor();
        String jsonData = "{\n" +
                "\"name\": \"tt\",\n" +
                "\"asset\": \"1000000000.0000 TT\",\n" +
                "\"memo\" : \"Something\"\n" +
                "}";
//        Action action = new Action("eosio.token", "create", Collections.singletonList(new Authorization("eosio.token", "active")), jsonData);
        List<Authorization> authorizations =
                new ArrayList<>();

        authorizations.add(
                new Authorization(
                        "eosio.token",
                        "active"));

        List<Action> actions =
                new ArrayList<>();

        actions.add(new Action(
                        "eosio.token",
                        "create", authorizations, jsonData));
        processor.prepare(actions);
        SendTransactionResponse sendTransactionResponse = processor.signAndBroadcast();
//        try {
//
//            // Prepare transaction with above action. A transaction can be executed with multiple action.
//            System.out.println("Preparing Transaction...");
//            processor.prepare(Collections.singletonList(action));
//
//            // Sign and broadcast the transaction.
//            System.out.println("Signing and Broadcasting Transaction...");
//            SendTransactionResponse response = processor.signAndBroadcast();
//            System.out.println("1");
////            this.publishProgress(Boolean.toString(true), "Finished!  Your transaction id is:  " + response.getTransactionId());
//            System.out.println("Finished!  Your transaction id is: ");
//            System.out.println("3");
//            System.out.println(response.getTransactionId());
//            System.out.println("2");
//        } catch (TransactionPrepareError transactionPrepareError) {
//            // Happens if preparing transaction unsuccessful
//            transactionPrepareError.printStackTrace();
////            this.publishProgress(Boolean.toString(false), transactionPrepareError.getLocalizedMessage());
//            System.out.println(transactionPrepareError.getLocalizedMessage());
//        } catch (TransactionSignAndBroadCastError transactionSignAndBroadCastError) {
//            // Happens if Sign transaction or broadcast transaction unsuccessful.
//            transactionSignAndBroadCastError.printStackTrace();
//
//            // try to get backend error if the error come from backend
////            RPCResponseError rpcResponseError = ErrorUtils.getBackendError(transactionSignAndBroadCastError);
//            System.out.println(transactionSignAndBroadCastError);
////            if (rpcResponseError != null) {
////                String backendErrorMessage = ErrorUtils.getBackendErrorMessageFromResponse(rpcResponseError);
////                this.publishProgress(Boolean.toString(false), backendErrorMessage);
////                return null;
//            }

//            this.publishProgress(Boolean.toString(false), transactionSignAndBroadCastError.getMessage());
        }
    }

//    public static void main(String[] args) throws Exception {
//        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://172.17.0.9:8888");
//        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
//        IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
//        ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();
//
//        ((SoftKeySignatureProviderImpl) signatureProvider).importKey("5J61mY3dcgHb4egBYVWz4av68y24JzqteKRHMFrDXyhmQdbkhbr");
//
//        TransactionSession session = new TransactionSession(
//                serializationProvider,
//                rpcProvider,
//                abiProvider,
//                signatureProvider
//        );
//
//        TransactionProcessor processor = session.getTransactionProcessor();
//
//// Now the TransactionConfig can be altered, if desired
//        TransactionConfig transactionConfig = processor.getTransactionConfig();
//
//// Use blocksBehind (default 3) the current head block to calculate TAPOS
//        transactionConfig.setUseLastIrreversible(false);
//// Set the expiration time of transactions 600 seconds later than the timestamp
//// of the block used to calculate TAPOS
//        transactionConfig.setExpiresSeconds(600);
//
//// Update the TransactionProcessor with the config changes
//        processor.setTransactionConfig(transactionConfig);
//
//        String jsonData = "{\n" +
//                "\"from\": \"alice\",\n" +
//                "\"to\": \"tt\",\n" +
//                "\"quantity\": \"10.0000 SYS\",\n" +
//                "\"memo\" : \"Something\"\n" +
//                "}";
//
//        List<Authorization> authorizations = new ArrayList<>();
//        authorizations.add(new Authorization("alice", "active"));
//        List<Action> actions = new ArrayList<>();
//        actions.add(new Action("eosio.token", "transfer", authorizations, jsonData));
//
//        processor.prepare(actions);
//
//        SendTransactionResponse sendTransactionResponse = processor.signAndBroadcast();
//
//// Starting with EOSIO 2.1 actions can have return values associated with them.
//// If the actions have return values they can be accessed from the response.
//        ArrayList<Object> actionReturnValues = sendTransactionResponse.getActionValues();
//        System.out.println(actionReturnValues.size());
//    }
//    public static void main(String[] args) throws Exception {
//        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://172.17.0.9:8888");
//        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
//        IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
//        ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();
//
//        ((SoftKeySignatureProviderImpl) signatureProvider).importKey("5J8ExGR4tXTso9BAo7DvncchqXSmJxBeGchKBvw1bMLMtxjFQE2");
//
//        TransactionSession session = new TransactionSession(
//                serializationProvider,
//                rpcProvider,
//                abiProvider,
//                signatureProvider
//        );
//
//        TransactionProcessor processor = session.getTransactionProcessor();
//
//// Now the TransactionConfig can be altered, if desired
//        TransactionConfig transactionConfig = processor.getTransactionConfig();
//
//// Use blocksBehind (default 3) the current head block to calculate TAPOS
//        transactionConfig.setUseLastIrreversible(false);
//// Set the expiration time of transactions 600 seconds later than the timestamp
//// of the block used to calculate TAPOS
//        transactionConfig.setExpiresSeconds(600);
//
//// Update the TransactionProcessor with the config changes
//        processor.setTransactionConfig(transactionConfig);
//
//        String jsonData = "{\n" +
//                "\"name\": \"alice\",\n" +
//                "\"first_name\": \"alice\",\n" +
//                "\"last_name\": \"alice\",\n" +
//                "\"street\" : \"alice\",\n" +
//                "\"city\" : \"beijing\",\n" +
//                "\"state\" : \"BUPT\"\n" +
//                "}";
//        System.out.println(jsonData);
////        Action action = new Action("metadata", "upsert", Collections.singletonList(new Authorization("alice", "active")), jsonData);
//        List<Authorization> authorizations = new ArrayList<>();
//        authorizations.add(new Authorization("alice", "active"));
//        List<Action> actions = new ArrayList<>();
//        actions.add(new Action("addressbook", "upsert", authorizations, jsonData));
//
//        processor.prepare(actions);
//
//        SendTransactionResponse sendTransactionResponse = processor.signAndBroadcast();
//
//// Starting with EOSIO 2.1 actions can have return values associated with them.
//// If the actions have return values they can be accessed from the response.
//        ArrayList<Object> actionReturnValues = sendTransactionResponse.getActionValues();
//        System.out.println(actionReturnValues.size());
//    }
//    public static void main(String[] args) throws Exception {
//        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://172.17.0.9:8888");
//        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
//
//        GetRawAbiRequest rawAbiRequest = new GetRawAbiRequest("eosio");
//        System.out.println(rpcProvider.getRawAbi(rawAbiRequest).getAccountName());
//    }
//    public static void main(String[] args) throws Exception {
//        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://172.17.0.9:8888");
//        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
//        System.out.println("begin");
//        System.out.println(rpcProvider.getInfo());
//        System.out.println("end");
//    }

//    public static void main(String[] args) throws Exception {
//        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://172.17.0.9:8888");
//        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
//        IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
//        SoftKeySignatureProviderImpl signatureProvider = new SoftKeySignatureProviderImpl();
//
//        String privateKey = "5JnfgNKPFH6uprULNzE4Qj4rF4oY9n2PtppDKm7rLuqcGz8PQ3r";
//        signatureProvider.importKey(privateKey);
//
//        TransactionSession session = new TransactionSession(
//                serializationProvider,
//                rpcProvider,
//                abiProvider,
//                signatureProvider
//        );
//
//        TransactionProcessor processor = session.getTransactionProcessor();
//
//        // Now the TransactionConfig can be altered, if desired
//        TransactionConfig transactionConfig = processor.getTransactionConfig();
//
//        // Use blocksBehind (default 3) the current head block to calculate TAPOS
//        transactionConfig.setUseLastIrreversible(false);
//        // Set the expiration time of transactions 600 seconds later than the timestamp
//        // of the block used to calculate TAPOS
//        transactionConfig.setExpiresSeconds(600);
//
//        // Update the TransactionProcessor with the config changes
//        processor.setTransactionConfig(transactionConfig);
//
//        String jsonData = "{\n" +
//                "\"from\": \"cicotester12\",\n" +
//                "\"to\": \"eosbarcelona\",\n" +
//                "\"quantity\": \"1.0000 EOS\",\n" +
//                "\"memo\" : \"Test transfer using java sdk\"\n" +
//                "}";
//
//        List<Authorization> authorizations = new ArrayList<>();
//        authorizations.add(new Authorization("cicotester12", "owner"));
//        List<Action> actions = new ArrayList<>();
//        actions.add(new Action("eosio.token", "transfer", authorizations, jsonData));
//
//        processor.prepare(actions);
//
//        SendTransactionResponse sendTransactionResponse = processor.signAndBroadcast();
//        ArrayList<Object> actionReturnValues = sendTransactionResponse.getActionValues();
//
//        System.out.println("Transaction Completed");
//    }

