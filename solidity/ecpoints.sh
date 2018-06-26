#!/bin/sh

solcjs Ownable.sol ECPoints.sol BasicToken.sol ERC20.sol SafeMath.sol StandardToken.sol ERC20Basic.sol StandardBurnableToken.sol BurnableToken.sol --bin --abi --optimise -o ecpointsjs
web3j solidity generate ecpointsjs/ECPoints_sol_ECPoints.bin ecpointsjs/ECPoints_sol_ECPoints.abi -o /Users/roc/Documents/workspace/echain/echain-services/src/main/java/ -p com.echain.solidity
