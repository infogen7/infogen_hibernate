package com.infogen.hibernate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.infogen.aop.AOP;
import com.infogen.hibernate.annotation.AutoClose;
import com.infogen.hibernate.event_handle.InfoGen_AOP_Handle_AutoClose;

/**
 * @author larry
 * @email larry.lv.word@gmail.com
 * @version 创建时间 2013-4-7 下午1:09:21
 */
public abstract class InfoGen_Hibernate {
	private static final Logger LOGGER = LogManager.getLogger(InfoGen_Hibernate.class.getName());
	public static final ThreadLocal<List<Session>> list_session_thread_local = new ThreadLocal<>();
	public static final ThreadLocal<Session> current_session_thread_local = new ThreadLocal<Session>();
	private static SessionFactory sessionFactory = null;

	public static SessionFactory init_pool(String path, String base_package) {
		LOGGER.info("#创建 hibernate  连接池");
		if (sessionFactory == null) {
			try {
				AOP.getInstance().add_advice_method(AutoClose.class, new InfoGen_AOP_Handle_AutoClose());
				if (AOP.getInstance().isadvice) {
					LOGGER.error("AutoClose注解不可用,请将InfoGen_Hibernate初始化代码放在启动infogen之前");
				}

				Configuration cfg = new Configuration().configure(new File(path));
				LOGGER.info(base_package);
				cfg = autoScanAnnotatedEntityClass(base_package, cfg);
				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
				sessionFactory = cfg.buildSessionFactory(serviceRegistry);
			} catch (Throwable e) {
				throw new ExceptionInInitializerError(e);
			}
		}
		LOGGER.info("#创建 hibernate  连接池成功");
		return sessionFactory;
	}

	public InfoGen_Hibernate() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 动态加载hibernate 映射 实体类 取代在 xml 中配置 <mapping class="com.model.modelPo"/>
	 * 
	 * @param scanRootPackage
	 * @param cfg
	 * @return {@linkplain Configuration}
	 * @throws IOException
	 */
	public static Configuration autoScanAnnotatedEntityClass(String scanRootPackage, Configuration cfg) throws IOException {
		Set<Class<?>> classes = AOP.getInstance().getClasses();
		for (Class<?> clazz : classes) {
			if (clazz.getName().startsWith(scanRootPackage)) {
				Entity[] annotationsByType = clazz.getAnnotationsByType(Entity.class);
				if (annotationsByType.length > 0) {
					cfg = cfg.addAnnotatedClass(clazz);
					LOGGER.info("Hibernate 动态加载类：".concat(clazz.getName()));
				}
			}
		}
		return cfg;
	}

	/**
	 * 需要自行关闭 session
	 * 
	 * @return
	 * @throws ErrorLogException
	 */
	public static Session createSession() {
		Session session = sessionFactory.openSession();
		List<Session> list = list_session_thread_local.get();
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(session);
		list_session_thread_local.set(list);
		return session;
	}

	/** 慎重使用:!!! 当前线程从发起到结束都只有一个不能执行session.close,或rollback后就不能再次进行调用 且mysql支持有问题 */
	@SuppressWarnings("unused")
	private static Session getCurrentSession() {
		// jta 只有分布式事务处理才用 获取当前线程的 session 用与在 service 层控制事务
		Session session = sessionFactory.getCurrentSession();
		current_session_thread_local.set(session);
		return session;
	}
}
