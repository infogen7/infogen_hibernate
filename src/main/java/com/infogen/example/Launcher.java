package com.infogen.example;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogen.aop.AOP;
import com.infogen.core.tools.Tool_Core;
import com.infogen.core.util.NativePath;
import com.infogen.example.dao.User_DAO;
import com.infogen.hibernate.InfoGen_Hibernate;

public class Launcher {
	private static final Logger LOGGER = LogManager.getLogger(Launcher.class.getName());
	public User_DAO user_dao;

	public void save() throws NoSuchAlgorithmException {
		InfoGen_Hibernate.init_pool(NativePath.get("hibernate.juxinli.cfg.xml").toString(), "com.infogen.infogen_hibernate.example.model");

		Boolean save = user_dao.save("account", Tool_Core.MD5("password", new StringBuilder("account").append("8b3f3cf5-0d2b-4f29-8dd7-0b994e44d768").toString()));
		LOGGER.info(save);
		// 查看 com.infogen.hibernate.event_handle.InfoGen_AOP_Handle_AutoClose 中自动关闭连接的代码
	}

	public static void main(String[] args) throws Exception {
		// 注入 user_dao实现类
		AOP.getInstance().add_autowired_field("com.infogen.infogen_demo.infogen_hibernate.example.Launch", "user_dao", "new com.infogen.infogen_hibernate.example.dao.User_DAO_Impl();");
		AOP.getInstance().advice();

		new Launcher().save();
		Thread.currentThread().join();
	}
}
