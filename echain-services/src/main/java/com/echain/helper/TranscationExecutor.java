package com.echain.helper;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.echain.exception.LogicInTranscationException;
import com.echain.util.SpringBeanUtil;

public abstract class TranscationExecutor<T> {
	public static Logger LOGGER = Logger.getLogger(TranscationExecutor.class);
	private DataSourceTransactionManager transactionManager = SpringBeanUtil
			.getBean(DataSourceTransactionManager.class);

	public abstract T execute();

	public void afterException() {

	};

	public void always() {

	};

	public Connection getConnection() throws Exception {
		return this.transactionManager.getDataSource().getConnection();
	}

	public T run() {
		TransactionStatus transactionStatus = null;
		try {
			DefaultTransactionDefinition def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
			transactionStatus = transactionManager.getTransaction(def);
			T rs = execute();
			transactionManager.commit(transactionStatus);
			return rs;
		} catch (LogicInTranscationException e) {// 业务逻辑出现问题了 在事务提交前就被抛出来了
			if (transactionStatus != null && !transactionStatus.isCompleted()) {
				transactionManager.rollback(transactionStatus);
			}
			afterException();
			return null;
		} catch (Throwable e) {
			LOGGER.error(e.getMessage(), e);
			if (transactionStatus != null && !transactionStatus.isCompleted()) {
				transactionManager.rollback(transactionStatus);
			}
			afterException();
			return null;
		} finally {
			always();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
