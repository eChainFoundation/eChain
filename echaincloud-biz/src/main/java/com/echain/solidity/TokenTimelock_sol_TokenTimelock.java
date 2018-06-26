package com.echain.solidity;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class TokenTimelock_sol_TokenTimelock extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b6040516060806104de83398101604052808051906020019091908051906020019091908051906020019091905050428111151561004b57600080fd5b826000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555081600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550806002819055505050506103f9806100e56000396000f300606060405260043610610062576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806338af3eed1461006757806386d1a69f146100bc578063b91d4001146100d1578063fc0c546a146100fa575b600080fd5b341561007257600080fd5b61007a61014f565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100c757600080fd5b6100cf610175565b005b34156100dc57600080fd5b6100e46102dc565b6040518082815260200191505060405180910390f35b341561010557600080fd5b61010d6102e2565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000600254421015151561018857600080fd5b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166370a08231306040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050602060405180830381600087803b151561024357600080fd5b5af1151561025057600080fd5b50505060405180519050905060008111151561026b57600080fd5b6102d9600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16826000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166103079092919063ffffffff16565b50565b60025481565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b8273ffffffffffffffffffffffffffffffffffffffff1663a9059cbb83836040518363ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200182815260200192505050602060405180830381600087803b15156103a957600080fd5b5af115156103b657600080fd5b5050506040518051905015156103c857fe5b5050505600a165627a7a723058201e895f2b376d60d6892066b9f99b9ff752596a472efa9314f326a87cb9f6e7820029";

    protected TokenTimelock_sol_TokenTimelock(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TokenTimelock_sol_TokenTimelock(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<String> beneficiary() {
        final Function function = new Function("beneficiary", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> release() {
        final Function function = new Function(
                "release", 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> releaseTime() {
        final Function function = new Function("releaseTime", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> token() {
        final Function function = new Function("token", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<TokenTimelock_sol_TokenTimelock> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _token, String _beneficiary, BigInteger _releaseTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(
        		new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.Address(_beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(_releaseTime)));
        return deployRemoteCall(TokenTimelock_sol_TokenTimelock.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<TokenTimelock_sol_TokenTimelock> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _token, String _beneficiary, BigInteger _releaseTime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.Address(_beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(_releaseTime)));
        return deployRemoteCall(TokenTimelock_sol_TokenTimelock.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static TokenTimelock_sol_TokenTimelock load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenTimelock_sol_TokenTimelock(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static TokenTimelock_sol_TokenTimelock load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TokenTimelock_sol_TokenTimelock(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
