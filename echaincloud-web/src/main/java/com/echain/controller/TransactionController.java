package com.echain.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.echain.entity.EcDapp;
import com.echain.entity.EcLogisticsCompany;
import com.echain.entity.EcLogisticsRecord;
import com.echain.entity.EcTransaction;
import com.echain.entity.EcUser;
import com.echain.entity.EcUserDapp;
import com.echain.entity.EcUserPoints;
import com.echain.service.LogisticsCompanyService;
import com.echain.service.LogisticsService;
import com.echain.service.PointService;
import com.echain.service.TaskService;
import com.echain.service.TransferDataService;
import com.echain.service.UserDappService;
import com.echain.service.UserService;
import com.echain.util.JsonUtil;
import com.echain.util.Md5Util;
import com.echain.vo.EcLogisticsRecordVo;
import com.echain.vo.EcTransactionVo;

@RequestMapping("/transaction")
@Controller
public class TransactionController {

	@Autowired
	private TransferDataService transferService;

	@Autowired
	private UserService userService;

	@Autowired
	private PointService pointService;

	@Autowired
	private LogisticsCompanyService logisticsCompanyService;

	@Autowired
	private UserDappService userDappService;

	@Autowired
	private TaskService taskService;

	@Autowired
	LogisticsService logisticsService;

	/**
	 * <p>
	 * 接受外部数据导入。
	 * 
	 * http://localhost:8080/transaction/receiveTransactions?phoneNumber=15026561611
	 * &dappName=dappName&isUploadSingle=1&status=1&transactionPlatform=transactionPlatform
	 * &transactionNo=transactionNo&logisticsCompanyName=logisticsCompanyName&logisticsNo=logisticsNo
	 * &describeText=describeText
	 * 
	 * @author roc
	 * @return
	 * @throws Exception
	 *             200 成功 400 请求失败，积分发送方，接受方都是平台 401 划转的积分数小于等于0 402 发送方的积分小于要划转的积分数
	 *             500 服务器内部错误
	 */
	@RequestMapping(value = "receiveTransactions", produces = "text/html;charset=UTF-8")
	public @ResponseBody String receiveTransactions(@RequestParam(required = true) String phoneNumber,
			@RequestParam(required = true) String dappName, @RequestParam(required = false) String isUploadSingle,
			@RequestParam(required = false) String transactionPlatform,
			@RequestParam(required = true) String transactionNo,
			@RequestParam(required = false) String logisticsCompanyName,
			@RequestParam(required = false) String logisticsNo, @RequestParam(required = true) String describeText,
			@RequestParam(required = true) String status, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		EcUser user = this.userService.getUserByPhoneNumber(phoneNumber);
		EcDapp dapp = this.userService.getDappByDappname(dappName);
		EcLogisticsCompany logisticsCompany = null;
		if (logisticsCompanyName != null && !"".equals(logisticsCompanyName))
			logisticsCompany = this.userService.getLogisticsCompanyByCompanyname(logisticsCompanyName);
		EcUserDapp userDapp = this.userService.getUserDappByUserIdAndDappId(user.getId(), dapp.getId(), isUploadSingle);

		EcTransaction transaction = new EcTransaction();
		transaction.setDappId(dapp.getId());
		transaction.setCreateTime(new Date());
		if (logisticsCompany != null)
			transaction.setLogisticsCompanyId(logisticsCompany.getId());
		if (logisticsNo != null && !"".equals(logisticsNo))
			transaction.setLogisticsNo(logisticsNo);
		transaction.setTransactionNo(transactionNo);
		if (transactionPlatform != null && !"".equals(transactionPlatform))
			transaction.setTransactionPlatform(transactionPlatform);
		transaction.setUserId(user.getId());
		transaction.setUserDappId(userDapp.getId());
		transaction.setStatus(status);
		transaction.setDescribeText(describeText);
		transaction.setDescribeMd5(Md5Util.encode(describeText));

		transaction = this.transferService.saveTransferData(transaction);
		if (transaction != null) {
			map.put("code", "200");
			Long transactionId = transaction.getId();
			map.put("transactionId", transactionId);
		} else {
			map.put("code", "500");
			map.put("transactionId", "0");
		}

		return JsonUtil.convertToString(map);

	}

	/**
	 * 根据交易ID获取交易信息
	 * http://echain.echain.one/transaction/getTransactionsById?transactionId=99
	 */
	@RequestMapping(value = "/getTransactionsById", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getTransactionsById(@RequestParam(required = true) Long transactionId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		EcTransaction transaction = this.transferService.selectTransactionById(transactionId);

		Map<String, Object> map = new HashMap<String, Object>();
		if (transaction != null) {
			map.put("code", 200);
			map.put("transaction", transaction.getDescribeText());
			return JsonUtil.convertToString(map);
		} else {
			map.put("code", 500);
			map.put("transaction", "");
			return JsonUtil.convertToString(map);
		}
	}

	/**
	 * 
	 * @param logisticsCompanyName
	 * @param logisticsNo
	 * @param logisticsText
	 * @param request
	 * @param response
	 * @return 200 成功 400 请求失败，积分发送方，接受方都是平台 401 划转的积分数小于等于0 402 发送方的积分小于要划转的积分数 500
	 *         服务器内部错误
	 * @throws Exception
	 */
	@RequestMapping(value = "receiveLogistics", produces = "text/html;charset=UTF-8")
	public @ResponseBody String receiveLogistics(@RequestParam(required = false) String logisticsCompanyName,
			@RequestParam(required = false) String logisticsNo, @RequestParam(required = true) String logisticsText,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		EcLogisticsCompany logisticsCompany = this.userService.getLogisticsCompanyByCompanyname(logisticsCompanyName);

		EcLogisticsRecord logistics = new EcLogisticsRecord();
		logistics.setCreateTime(new Date());
		logistics.setLogisticsNo(logisticsNo);
		logistics.setLogisticsText(logisticsText);
		logistics.setLogisticsMd5(Md5Util.encode(logisticsText));
		logistics.setLogisticsCompanyId(logisticsCompany.getId());

		logistics = logisticsService.saveLogistics(logistics);

		Map<String, Object> map = new HashMap<String, Object>();
		if (logistics != null) {
			map.put("code", "200");
			Long logisticsId = logistics.getId();
			map.put("logisticsId", logisticsId);
		} else {
			map.put("code", "500");
			map.put("logisticsId", "0");
		}

		return JsonUtil.convertToString(map);
	}

	/**
	 * 根据交易ID获取交易信息
	 * http://echain.echain.one/transaction/getLogisticsById?logisticsId=67
	 */
	@RequestMapping(value = "/getLogisticsById", produces = "text/html;charset=UTF-8")
	public @ResponseBody String getLogisticsById(@RequestParam(required = true) Long logisticsId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		EcLogisticsRecord logistic = this.logisticsService.selectLogisticById(logisticsId);

		Map<String, Object> map = new HashMap<String, Object>();
		if (logistic != null) {
			map.put("code", 200);
			map.put("logistic", logistic.getLogisticsText());
			return JsonUtil.convertToString(map);
		} else {
			map.put("code", 500);
			map.put("logistic", "");
			return JsonUtil.convertToString(map);
		}
	}

	/**
	 * 积分划转接口 返回值 0--积分发送方，接受方都是平台 1--划转的积分数小于等于0 2--发送方的积分小于要划转的积分数 4--成功
	 * 
	 * @param sendPhoneNumber
	 * @param receivePhoneNumber
	 * @param dappName
	 * @param points
	 * @param request
	 * @param response
	 * @return 200 成功 400 请求失败，积分发送方，接受方都是平台 401 划转的积分数小于等于0 402 发送方的积分小于要划转的积分数 500
	 *         服务器内部错误
	 * @throws Exception
	 *             http://localhost:8080/transaction/receiveTransactionsPoints?
	 *             phoneNumber=15026561611&dappName=dappName&isUploadSingle=1&status=1
	 *             &transactionPlatform=transactionPlatform
	 *             &transactionNo=transactionNo&logisticsCompanyName=logisticsCompanyName
	 *             &logisticsNo=logisticsNo&describeText=describeText
	 *             &sendPhoneNumber
	 *             =15026561611&receivePhoneNumber=15026561961&points=100
	 */
	@RequestMapping(value = "receiveTransactionsPoints", produces = "text/html;charset=UTF-8")
	public @ResponseBody String receiveTransactionsPoints(@RequestParam(required = true) String phoneNumber,
			@RequestParam(required = true) String dappName, @RequestParam(required = false) String isUploadSingle,
			@RequestParam(required = false) String transactionPlatform,
			@RequestParam(required = true) String transactionNo,
			@RequestParam(required = false) String logisticsCompanyName,
			@RequestParam(required = false) String logisticsNo, @RequestParam(required = true) String describeText,
			@RequestParam(required = true) String status, @RequestParam(required = false) String sendPhoneNumber,
			@RequestParam(required = false) String receivePhoneNumber, @RequestParam(required = false) Long points,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("transactionId", 0);
		if (sendPhoneNumber == null && receivePhoneNumber == null) {
			map.put("code", 400);
			return JsonUtil.convertToString(map);
		}
		if (points <= 0) {
			map.put("code", 401);
			return JsonUtil.convertToString(map);
		}
		if (sendPhoneNumber == null)
			sendPhoneNumber = "0";
		if (receivePhoneNumber == null)
			receivePhoneNumber = "0";
		if ("0".equals(sendPhoneNumber) && ("0".equals(receivePhoneNumber))) {
			map.put("code", 400);
			return JsonUtil.convertToString(map);
		}
		EcUserPoints userSendPoint = null;
		EcUserPoints userReceivePoint = null;

		EcUser userSend = this.userService.getUserByPhoneNumber(sendPhoneNumber);
		if (userSend != null) {
			userSendPoint = this.userService.getUserPointByUserId(userSend.getId());
		}

		EcUser userReceive = this.userService.getUserByPhoneNumber(receivePhoneNumber);
		if (userReceive != null) {
			userReceivePoint = this.userService.getUserPointByUserId(userReceive.getId());
		}

		// if (userSendPoint.getNowPoints() < points) {
		// map.put("code", 402);
		// return JsonUtil.convertToString(map);
		// }

		EcUser user = this.userService.getUserByPhoneNumber(phoneNumber);
		EcDapp dapp = this.userService.getDappByDappname(dappName);

		EcLogisticsCompany logisticsCompany = null;
		if (logisticsCompanyName != null && !"".equals(logisticsCompanyName))
			logisticsCompany = this.userService.getLogisticsCompanyByCompanyname(logisticsCompanyName);
		EcUserDapp userDapp = this.userService.getUserDappByUserIdAndDappId(user.getId(), dapp.getId(), isUploadSingle);

		EcTransaction transaction = new EcTransaction();
		transaction.setDappId(dapp.getId());
		transaction.setCreateTime(new Date());
		if (logisticsCompany != null)
			transaction.setLogisticsCompanyId(logisticsCompany.getId());
		if (logisticsNo != null && !"".equals(logisticsNo))
			transaction.setLogisticsNo(logisticsNo);
		transaction.setTransactionNo(transactionNo);
		if (transactionPlatform != null && !"".equals(transactionPlatform))
			transaction.setTransactionPlatform(transactionPlatform);
		transaction.setUserId(user.getId());
		transaction.setUserDappId(userDapp.getId());
		transaction.setStatus(status);
		transaction.setDescribeText(describeText);
		transaction.setDescribeMd5(Md5Util.encode(describeText));

		EcUserDapp userSendDapp = this.userService.getUserDappByUserIdAndDappId(userSend.getId(), dapp.getId(),
				isUploadSingle);
		EcUserDapp userRecevieDapp = this.userService.getUserDappByUserIdAndDappId(userReceive.getId(), dapp.getId(),
				isUploadSingle);

		String transactionId = this.pointService.changeTransferPoints(transaction, userSendPoint, userReceivePoint,
				points, dapp, userSendDapp, userRecevieDapp);
		if (transactionId != null && !"".equals(transactionId)) {
			map.put("code", 200);
			map.put("transactionId", transactionId);
			return JsonUtil.convertToString(map);
		} else {
			map.put("code", 500);
			return JsonUtil.convertToString(map);
		}

	}

	/**
	 * 订单图片上传
	 */
	@RequestMapping(value = "/upload.do")
	public String uploadImag(@RequestParam("userId") Long userId, HttpServletRequest request,
			HttpServletResponse response, @RequestParam("file") MultipartFile file) throws Exception {

		String separator = File.separator;// 分隔符
		// 获取当前日期
		Calendar now = Calendar.getInstance();
		String time = "" + now.get(Calendar.YEAR) + (now.get(Calendar.MONTH) + 1) + now.get(Calendar.DAY_OF_MONTH);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");

		// String realPathStar =
		// request.getSession().getServletContext().getRealPath("/static/upload");
		String realPathStar = "/var/www/echain/static/upload";
		String realPathEnd = separator + time + separator + userId + separator + uuid + "."
				+ file.getOriginalFilename().split("\\.")[1];
		transferService.saveTransferData(userId, file, realPathStar, realPathEnd);

		EcUser user = userService.selectUserByPrimaryKey(userId);
		return "redirect:/user/" + userId + "/upload.do?phoneNumber=" + user.getPhoneNumber() + "&type=emall";
	}

	/**
	 * 订单列表
	 */
	@RequestMapping(value = "{userId}/uploadList.do", produces = "text/html;charset=UTF-8")
	public String uploadList(@PathVariable("userId") Long userId, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List<EcTransaction> transfers = transferService.selectEchainListByUserId(userId);
		ArrayList<EcTransaction> succesList = new ArrayList<>();
		ArrayList<EcTransaction> faildedList = new ArrayList<>();
		ArrayList<EcTransaction> checkingList = new ArrayList<>();
		for (EcTransaction ecTransaction : transfers) {
			String status = ecTransaction.getStatus();
			if (status.equals("1")) {
				succesList.add(ecTransaction);
			} else if (status.equals("2")) {
				faildedList.add(ecTransaction);
			} else {
				checkingList.add(ecTransaction);
			}
		}
		modelMap.put("succesList", succesList);
		modelMap.put("faildedList", faildedList);
		modelMap.put("checkingList", checkingList);
		// 查询物流公司
		List<EcLogisticsCompany> lcList = logisticsCompanyService.selectAll();
		modelMap.put("lcList", lcList);
		return "echain/orderUpload";
	}

	/**
	 * 订单更新物流信息
	 */
	@RequestMapping(value = "/matche.do")
	public @ResponseBody String matche(@RequestParam("companyId") Long companyId,
			@RequestParam("logisticsNo") String logisticsNo, @RequestParam("transactionId") Long transactionId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EcTransaction ecTransaction = new EcTransaction();
		ecTransaction.setId(transactionId);
		ecTransaction.setLogisticsCompanyId(companyId);
		ecTransaction.setLogisticsNo(logisticsNo);
		boolean flag = transferService.updateEcTransaction(ecTransaction);
		if (!flag) {
			return "0";
		}
		return "1";
	}

	/**
	 * 获取交易上链hash
	 */
	@RequestMapping(value = "/getTransactionHash.action")
	public @ResponseBody String getTransactionHash(@RequestParam("transactionId") Long transactionId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		EcTransaction transaction = transferService.selectTransactionById(transactionId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (transaction != null) {
			map.put("code", 200);
			map.put("transaction_hash", transaction.getTransactionHash());
			map.put("block_no", transaction.getBlockNo());
		} else {
			map.put("code", 500);
		}
		return JsonUtil.convertToString(map);
	}

	/**
	 * 订单审核页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/upload_list.do", produces = "text/html;charset=UTF-8")
	public String uploadListForCheck(@RequestParam(value = "index", defaultValue = "1") Integer index,
			ModelMap modelMap) {
		if (index <= 0)
			index = 1;
		List<EcTransaction> list = transferService.selectListForCheckWithLimit(index);
		HashMap<Long, EcUser> userMap = new HashMap<Long, EcUser>();
		list.forEach((tr) -> {
			EcUser user = userService.selectUserByPrimaryKey(tr.getUserId());
			userMap.put(user.getId(), user);
		});
		modelMap.put("transaction", list);
		modelMap.put("index", index);
		modelMap.put("users", userMap);
		return "echain/checkTransaction";
	}

	/**
	 * 用户上传订单审核方法
	 * 
	 * @return
	 */
	@RequestMapping("/check_upload.do")
	public @ResponseBody String checkUpload(@RequestParam(value = "id") Long id,
			@RequestParam(value = "check") boolean check, ModelMap modelMap) {
		boolean flag = transferService.updateEcTransaction(id, check);
		if (!flag) {
			return "0";
		}
		return "1";
	}

	/**
	 * 根据userId 和 DappId 获取交易信息列表
	 */
	@RequestMapping(value = "/transaction_list.do", produces = "text/html;charset=UTF-8")
	public String getTransactionList(@RequestParam("userId") Long userId, @RequestParam("dappId") Long dappId,
			HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {

		EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);

		if (userDapp == null) {
			return "0";
		}

		List<EcTransaction> list = taskService.selectListTransactionByUserDappId(Long.toString(userDapp.getId()));

		if (list != null && list.size() > 0) {
			List<EcTransactionVo> voList = new ArrayList<>();

			String transactionKey = "0";
			for (EcTransaction transaction : list) {
				EcTransactionVo vo = new EcTransactionVo(transaction);
				voList.add(vo);

				if ("0".equals(transactionKey) && !"0".equals(transaction.getTransactionKey())) {
					transactionKey = transaction.getTransactionKey();
				}
			}

			modelMap.put("transactionList", voList);
			modelMap.put("transactionKey", transactionKey);
		} else {
			modelMap.put("transactionList", new ArrayList<>());
		}

		modelMap.put("userId", userId);
		modelMap.put("dappId", dappId);

		return "echain/transaction";
	}

	/**
	 * 根据logisticsCompanyId 和 logisticsNo 获取物流信息列表
	 */
	@RequestMapping(value = "/logistics_list.do", produces = "text/html;charset=UTF-8")
	public String getLogisticsList(@RequestParam("logisticsCompanyId") Long logisticsCompanyId,
			@RequestParam("logisticsNo") String logisticsNo, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		List<EcLogisticsRecord> records = logisticsService.selectByCompanyIdAndNo(logisticsCompanyId, logisticsNo);

		if (records != null && records.size() > 0) {
			List<EcLogisticsRecordVo> voList = records.stream().map(m -> new EcLogisticsRecordVo(m))
					.collect(Collectors.toList());
			modelMap.put("recordList", voList);
		} else {
			modelMap.put("recordList", new ArrayList<>());
		}

		return "echain/record";
	}

	/**
	 * 上链数据的验证
	 */
	@RequestMapping(value = "/check.do", produces = "text/html;charset=UTF-8")
	public @ResponseBody String matche(@RequestParam("userId") Long userId, @RequestParam("dappId") Long dappId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);

		if (userDapp == null) {
			return "0";
		}

		List<EcTransaction> list = taskService.selectListTransactionMds5ByUserDappIds(Long.toString(userDapp.getId()));

		if (list == null || list.size() == 0) {
			return "1";
		}

		Map<String, Object> map = new HashMap<>();
		list.stream().forEach(r -> {
			List<String> recoreds = new ArrayList<>();

			List<EcLogisticsRecord> recordList = r.getRecords();
			if (recordList != null && recordList.size() > 0) {
				recordList.stream().forEach(m -> {
					recoreds.add(m.getLogisticsMd5());
				});
			}

			map.put(r.getDescribeMd5(), recoreds);
		});

		if (map != null && map.size() > 0) {
			return Md5Util.encode(JsonUtil.convertToString(map));
		} else {
			return "1";
		}
	}

	/**
	 * 验证是否存在交易记录
	 */
	@RequestMapping(value = "/check_order.do", produces = "text/html;charset=UTF-8")
	public @ResponseBody String checkOrder(@RequestParam("userId") Long userId, @RequestParam("dappId") Long dappId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		EcUserDapp userDapp = userDappService.selectByUserIdAndDappId(userId, dappId);

		if (userDapp == null) {
			return "0";
		}

		List<EcTransaction> list = taskService.selectListTransactionMds5ByUserDappIds(Long.toString(userDapp.getId()));

		if (list == null || list.size() == 0) {
			return "1";
		}

		return "2";
	}
}
