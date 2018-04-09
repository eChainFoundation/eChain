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
import org.web3j.abi.datatypes.generated.Uint256;
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
public class Save_sol_Save extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b33600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506105d88061005f6000396000f300606060405260043610610057576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635537f99e1461005c5780638da5cb5b14610139578063b7c763b51461018e575b600080fd5b341561006757600080fd5b6100b7600480803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509190505061022a565b6040518083815260200180602001828103825283818151815260200191508051906020019080838360005b838110156100fd5780820151818401526020810190506100e2565b50505050905090810190601f16801561012a5780820380516001836020036101000a031916815260200191505b50935050505060405180910390f35b341561014457600080fd5b61014c61036b565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b341561019957600080fd5b6101af6004808035906020019091905050610391565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156101ef5780820151818401526020810190506101d4565b50505050905090810190601f16801561021c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6000610234610453565b60008080548060010182816102499190610467565b91600052602060002090016000869091909150908051906020019061026f929190610493565b50905060008110156102f1577f8b93e3994ef40cd94a097b196f9db7894cc54c008dc3c68a8dd04afd099d258c60006040518082815260200180602001828103825260058152602001807f6572726f720000000000000000000000000000000000000000000000000000008152506020019250505060405180910390a1610365565b7f8b93e3994ef40cd94a097b196f9db7894cc54c008dc3c68a8dd04afd099d258c600182036040518082815260200180602001828103825260068152602001807f73757373657200000000000000000000000000000000000000000000000000008152506020019250505060405180910390a15b50915091565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b610399610453565b6000828154811015156103a857fe5b90600052602060002090018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156104475780601f1061041c57610100808354040283529160200191610447565b820191906000526020600020905b81548152906001019060200180831161042a57829003601f168201915b50505050509050919050565b602060405190810160405280600081525090565b81548183558181151161048e5781836000526020600020918201910161048d9190610513565b5b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104d457805160ff1916838001178555610502565b82800160010185558215610502579182015b828111156105015782518255916020019190600101906104e6565b5b50905061050f919061053f565b5090565b61053c91905b80821115610538576000818161052f9190610564565b50600101610519565b5090565b90565b61056191905b8082111561055d576000816000905550600101610545565b5090565b90565b50805460018160011615610100020316600290046000825580601f1061058a57506105a9565b601f0160209004906000526020600020908101906105a8919061053f565b5b505600a165627a7a72305820a812ca5cf48e250e7a31ee0e38b6b13bc8f7d346e3e28b7192f55837efa1c0310029";

    protected Save_sol_Save(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Save_sol_Save(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public List<SetStringEventResponse> getSetStringEvents(TransactionReceipt transactionReceipt) {
        final Event event = new Event("SetString", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(event, transactionReceipt);
        ArrayList<SetStringEventResponse> responses = new ArrayList<SetStringEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            SetStringEventResponse typedResponse = new SetStringEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.key = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.types = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<SetStringEventResponse> setStringEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        final Event event = new Event("SetString", 
                Arrays.<TypeReference<?>>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(event));
        return web3j.ethLogObservable(filter).map(new Func1<Log, SetStringEventResponse>() {
            @Override
            public SetStringEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(event, log);
                SetStringEventResponse typedResponse = new SetStringEventResponse();
                typedResponse.log = log;
                typedResponse.key = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.types = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public RemoteCall<TransactionReceipt> setstring(String md5) {
        final Function function = new Function(
                "setstring", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(md5)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function("owner", 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> getString(BigInteger key) {
        final Function function = new Function("getString", 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(key)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Save_sol_Save> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Save_sol_Save.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Save_sol_Save> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Save_sol_Save.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static Save_sol_Save load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Save_sol_Save(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Save_sol_Save load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Save_sol_Save(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class SetStringEventResponse {
        public Log log;

        public BigInteger key;

        public String types;
    }
}
