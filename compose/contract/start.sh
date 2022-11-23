#!/bin/bash
eosio-cpp shamir12.cpp -o shamir12.wasm
sleep 60
cleos wallet unlock PW5KKGxDWAxZHUhPVChVNADXgNk2MP1LcfNkbgNj9bxTsHNNt2Txg
cleos create account eosio shamir12 EOS6MRyAjQq8ud7hVNYcfnVPJqcVpscN5So8BhtHuGYqET5GDW5CV -p eosio@active
cleos set contract shamir12 /root/shamir12/ -p shamir12@active