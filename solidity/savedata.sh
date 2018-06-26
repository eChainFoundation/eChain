#!/bin/sh

solcjs SaveData.sol --bin --abi --optimise -o savejs
web3j solidity generate savejs/SaveData_sol_SaveData.bin savejs/SaveData_sol_SaveData.abi -o /Users/roc/Documents/workspace/echain/echain-services/src/main/java/ -p com.echain.solidity
