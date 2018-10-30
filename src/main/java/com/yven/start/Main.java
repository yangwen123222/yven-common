package com.yven.start;

import com.yven.dao.ControllerDaoJdbc;
import com.yven.domain.Imuser;
import com.yven.domain.Spool;
import com.yven.mongodb.entity.ClientInfoEntity;
import com.yven.mongodb.service.ClientInfoMongoService;
import com.yven.service.ControlService;
import com.yven.threads.CountAccountTypeThread;
import com.yven.utils.CountAccountType;
import com.yven.utils.conf.Config;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yven.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 测试启动类
 * @author Administrator
 *
 */
public class Main {

	private static Logger logger = Logger.getLogger(Main.class);

	/**
	 * poi excepl导入导出( 多线程处理数据)
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// 计数器为4
		CountDownLatch latch = new CountDownLatch(4);
		//导入
		List<Imuser> imusers = CountAccountType.ImportExcel("C:\\Users\\yangwen\\Desktop\\imuser_formal0.xls");
		System.out.println("excel导入成功===============");
		// 直接处理
//		System.out.println("begin===================");
//		for (Imuser imuser : imusers) {
//			imuser.setTclAccount(CountAccountType.isTclAccount(imuser.getMobilePhone()));
//			System.out.println(imuser);
//		}
//		System.out.println("end===================");

		// 多线程处理
		CountAccountTypeThread test = new CountAccountTypeThread();
		test.handleList(imusers,4,latch);

		latch.await();// 等待线程结束

		System.out.println("处理完成===============");
		// 数据库中取数据
//		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath*:conf/applicationContext.xml"});
//		ControlService controlService = (ControlService) ac.getBean("controlService");
//		List<Imuser> imusers = controlService.getInfoFromImuser();
//		for (Imuser imuser : imusers) {
//			imuser.setTclAccount(CountAccountType.isTclAccount(imuser.getMobilePhone()));
//			System.out.println(imuser);
//		}

		// 导出
//		CountAccountType.exportExcel(imusers,"C:\\Users\\yangwen\\Desktop\\imuser_export_dev.xls");
//		CountAccountType.exportExcel(imusers,"C:\\Users\\yangwen\\Desktop\\imuser_export_test.xls");
		CountAccountType.exportExcel(imusers,"C:\\Users\\yangwen\\Desktop\\imuser_export_formal0.xls");
	}

	/**
	 * get info form imuser
	 * @param args
	 */
	public static void main3(String[] args){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{"classpath*:conf/applicationContext.xml"});
		ControlService controlService = (ControlService) ac.getBean("controlService");
		List<Imuser> imusers = controlService.getInfoFromImuser();
		for (Imuser imuser : imusers) {
			imuser.setTclAccount(CountAccountType.isTclAccount(imuser.getMobilePhone()));
			System.out.println(imuser);
		}

	}

	/**
	 * mongodb测试
	 * @param args
	 */
	public static void main2(String[] args){

	    // 测试读取配置文件的信息
	    String test = Config.getString("yven");
        System.out.println(test);

        // spring context 加载
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[]{"classpath*:conf/applicationContext.xml"});


		// mongodb 测试
		ClientInfoMongoService clientInfoMongoService = (ClientInfoMongoService) ac.getBean("clientInfoMongoService");
		List<ClientInfoEntity> clientInfoEntities = clientInfoMongoService.getByUserId("1000001");
		if (clientInfoEntities != null){
			System.out.println(clientInfoEntities.get(0).toString());
		}
	}



	public static void main1(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[]{"classpath*:conf/applicationContext.xml"});
		 UserService userService = (UserService) ac.getBean("userService");
		ControlService controlService = (ControlService) ac.getBean("controlService");

		ControllerDaoJdbc controllerDaoJdbc = (ControllerDaoJdbc)ac.getBean("controllerDaoJdbc");

		//int countThread = 1500;// 在服务器上能正常运行24小时
		 int countThread = 1000;
		 long startTime = System.currentTimeMillis();
		logger.info("程序启动时间：" + startTime);

		//testControllerDaoJdbc(controllerDaoJdbc);

		for (int i = 0; i < countThread; i++) {
			new Thread() {
				long threadStartTime = 0;
				public void run() {
					while (true) {
						try {
							threadStartTime = System.currentTimeMillis();
							//User user = (User) userService.findUserByUsername("abc");// 执行查询操作
							//logger.info(user.toString());

							/**测试233*/
							List<String> userIds =userService.findListByUsername2("abc");
							logger.info("查询结果为："+userIds.toString());

							/**测试233*/
							/*List<User> users = userService.findListByUsername4("abc");
							for (User user :users) {
								logger.info(user.toString());
							}*/

							/**测试72*/
//							List<String> results = controlService.getThirdCtrlorList2(1041938L);
//							if(null != results || results.size() > 0){
//								logger.info("查询结果为："+results.toString());
//							}else{
//								logger.info("查询结果为：null");
//							}
							logger.info("current thread:" + Thread.currentThread().getName() + " ==run time:" + (System.currentTimeMillis() - threadStartTime));

							//Thread.sleep(1000);// 休眠1s
							//Thread.sleep(500);// 休眠500ms
						} catch (Exception e) {
							logger.error(e.getMessage());
							logger.info("程序运行时间：" + (System.currentTimeMillis() - startTime) / 1000 + " 秒");
							e.printStackTrace();
							System.exit(1);// 退出程序
							
						}
					}
				}
			}.start();
		}

	}

	private static void testControllerDaoJdbc(ControllerDaoJdbc controllerDaoJdbc) {
		Spool spool = new Spool();
		spool.setUsername("123456");
		spool.setMsgid("20033931531903118672");
		spool.setXml("<message type=\"chat\" id=\"20033931531903118672\" from=\"2003393@tcl.com\" to=\"2003413@tcl.com\">\n" +
				"  <body>用户【cubix】在场景【关灯关空调】中对此设备执行了以下控制操作:电源开关关机!</body>\n" +
				"  <x xmlns=\"tcl:im:attribute\">\n" +
				"    <apptype>0</apptype>\n" +
				"    <sendtime>2018-07-18 16:38:38</sendtime>\n" +
				"    <delay stamp=\"2018-07-18 17:12:50\">Offline Storage</delay>\n" +
				"  </x>\n" +
				"</message>");
		spool.setType(0);
		spool.setCreateAt(new Date());
		spool.setGroupId("");
		spool.setFromid("2003393");

		try {
			controllerDaoJdbc.addOffLineMessage(spool);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

