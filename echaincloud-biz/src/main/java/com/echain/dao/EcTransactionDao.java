package com.echain.dao;

import com.echain.common.util.MyMapper;
import com.echain.entity.EcTransaction;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EcTransactionDao extends MyMapper<EcTransaction> {

    Long selectMinIdNokeyAndChecked();

    //List<EcTransaction> selectListTransactionMD5s(@Param("minId") Long minId, @Param("upDataSize") Integer upDataSize);

    List<EcTransaction> selectEchainListByUserId(@Param("userId") Long userId);

    List<EcTransaction> selectListForCheck();

    List<EcTransaction> selectListForCheckWithLimit(@Param("m") Integer m, @Param("n") Integer n);

    //List<EcTransaction> selectListTransactionMD5sByUserId(@Param("userId") Long userId);

    List<EcTransaction> selectListTransactionMds5ByUserDappIds(@Param("userDappIds") String userDappIds);

    List<EcTransaction> selectListTransactionByUserDappId(@Param("userDappId") String userDappId);

    //List<EcTransaction> selectListTransactionsNotSingleUpload(@Param("userAppIds") String userAppIds);

    List<EcTransaction> selectListTransactionsNotSingleUploadDappId(@Param("userAppIds") String userAppIds,
                                                                    @Param("dappId") Long dappId);

    int batchUpdateTransaction(@Param("list") List<Long> list, @Param("transactionKey") String transactionKey,
                               @Param("transactionHash") String transactionHash,
                               @Param("blockNo") String blockNo, @Param("md5") String md5);
}