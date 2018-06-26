#!/bin/sh

solcjs TokenTimelock.sol BasicToken.sol ERC20.sol SafeMath.sol SafeERC20.sol ERC20Basic.sol --bin --abi --optimise -o tokenlockjs
web3j solidity generate tokenlockjs/TokenTimelock_sol_TokenTimelock.bin tokenlockjs/TokenTimelock_sol_TokenTimelock.abi -o /Users/roc/Documents/workspace/echain/echain-services/src/main/java/ -p com.echain.solidity
