package com.infogen.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.infogen.aop.AOP;
import com.infogen.hibernate.annotation.AutoClose;
import com.infogen.hibernate.event_handle.InfoGen_AOP_Handle_AutoClose;

/**
 * 
 * @author larry/larrylv@outlook.com/创建时间 2015年11月20日 下午7:01:37
 * @since 1.0
 * @version 1.0
 */
public class InfoGen_Hibernate {
	private static final Logger LOGGER = LogManager.getLogger(InfoGen_Hibernate.class.getName());
	public static final ThreadLocal<List<Session>> list_session_thread_local = new ThreadLocal<>();
	private SessionFactory sessionFactory = null;

	public InfoGen_Hibernate(String path) {
		super();
		LOGGER.info("#创建 hibernate  连接池:" + path);
		if (sessionFactory == null) {
			AOP.getInstance().add_advice_method(AutoClose.class, new InfoGen_AOP_Handle_AutoClose());
			if (AOP.getInstance().isadvice) {
				LOGGER.error("AutoClose注解不可用,请将 InfoGen_Hibernate 初始化代码放在服务启动之前");
			}
			// 1. 配置类型安全的准服务注册类，这是当前应用的单例对象，不作修改，所以声明为final
			final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure(path).build();
			try {
				// 2. 根据服务注册类创建一个元数据资源集，同时构建元数据并生成应用一般唯一的的session工厂
				sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
			} catch (Throwable e) {
				StandardServiceRegistryBuilder.destroy(registry);
				throw new ExceptionInInitializerError(e);
			}
		}
	}

	public InfoGen_Hibernate() {
		super();
	}

	// 需要自行关闭 session
	public Session createSession() {
		Session session = sessionFactory.openSession();
		List<Session> list = list_session_thread_local.get();
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(session);
		list_session_thread_local.set(list);
		return session;
	}

	/**
	 * 慎重使用:!!! 当前线程从发起到结束都只有一个不能执行session.close,或rollback后就不能再次进行调用 且mysql支持有问题
	 */
	@SuppressWarnings("unused")
	private Session getCurrentSession() {
		// jta 只有分布式事务处理才用 获取当前线程的 session 用与在 service 层控制事务
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
}
