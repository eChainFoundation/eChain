package com.echain.solidity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.3.1.
 */
public class SaveData_sol_SaveData extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b33600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506106528061005f6000396000f300606060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680638da5cb5b1461005c5780639c981fcb146100b1578063d2e1d20114610187575b600080fd5b341561006757600080fd5b61006f6102a0565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34156100bc57600080fd5b61010c600480803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506102c6565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561014c578082015181840152602081019050610131565b50505050905090810190601f1680156101795780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561019257600080fd5b610225600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919050506103d9565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561026557808201518184015260208101905061024a565b50505050905090810190601f1680156102925780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6102ce61056d565b6000826040518082805190602001908083835b60208310151561030657805182526020820191506020810190506020830392506102e1565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156103cd5780601f106103a2576101008083540402835291602001916103cd565b820191906000526020600020905b8154815290600101906020018083116103b057829003601f168201915b50505050509050919050565b6103e161056d565b816000846040518082805190602001908083835b60208310151561041a57805182526020820191506020810190506020830392506103f5565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390209080519060200190610460929190610581565b506000836040518082805190602001908083835b6020831015156104995780518252602082019150602081019050602083039250610474565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390208054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156105605780601f1061053557610100808354040283529160200191610560565b820191906000526020600020905b81548152906001019060200180831161054357829003601f168201915b5050505050905092915050565b602060405190810160405280600081525090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106105c257805160ff19168380011785556105f0565b828001600101855582156105f0579182015b828111156105ef5782518255916020019190600101906105d4565b5b5090506105fd9190610601565b5090565b61062391905b8082111561061f576000816000905550600101610607565b5090565b905600a165627a7a723058208ba0fbb1cdb6b1f9fc29804252b1c356283dd24b3ecf591a8ea3a2c5027754e90029";

    protected SaveData_sol_SaveData(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected SaveData_sol_SaveData(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<SetStringEventResponse> getSetStringEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SetString", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<SetStringEventResponse> responses = new ArrayList<SetStringEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SetStringEventResponse typedResponse = new SetStringEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.types = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetStringEventResponse> setStringEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SetString", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SetStringEventResponse>() {
            @Override
            public SetStringEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                SetStringEventResponse typedResponse = new SetStringEventResponse();
                typedResponse.log = log;
                typedResponse.key = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.types = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getString(String key) {
        final Function function = new Function("getString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setstring(String key, String md5) {
        final Function function = new Function(
                "setstring", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(key), 
                new org.web3j.abi.datatypes.Utf8String(md5)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<SaveData_sol_SaveData> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SaveData_sol_SaveData.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<SaveData_sol_SaveData> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(SaveData_sol_SaveData.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static SaveData_sol_SaveData load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SaveData_sol_SaveData(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static SaveData_sol_SaveData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SaveData_sol_SaveData(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SetStringEventResponse {
        public Log log;

        public String key;

        public String types;
    }
}
