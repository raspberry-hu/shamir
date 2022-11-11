package com.share.shamir.util.eosio.java.abieos.serialization;

import client.EosApiClientFactory;
import client.EosApiRestClient;
import client.domain.response.chain.Block;
import one.block.eosiojava.implementations.ABIProviderImpl;
import one.block.eosiojava.interfaces.IABIProvider;
import one.block.eosiojava.interfaces.IRPCProvider;
import one.block.eosiojava.interfaces.ISerializationProvider;
import one.block.eosiojava.interfaces.ISignatureProvider;
import one.block.eosiojavaabieosserializationprovider.AbiEosSerializationProviderImpl;
import one.block.eosiojavarpcprovider.implementations.EosioJavaRpcProviderImpl;
import one.block.eosiosoftkeysignatureprovider.SoftKeySignatureProviderImpl;

public class provider {

    public static void main(String[] args) throws Exception {
        IRPCProvider rpcProvider = new EosioJavaRpcProviderImpl("http://10.112.228.2:8888");
        ISerializationProvider serializationProvider = new AbiEosSerializationProviderImpl();
//        IABIProvider abiProvider = new ABIProviderImpl(rpcProvider, serializationProvider);
//        ISignatureProvider signatureProvider = new SoftKeySignatureProviderImpl();
    }
}
