package com.infogen.hibernate.event_handle;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import com.infogen.aop.advice.event_handle.AOP_Handle;
import com.infogen.aop.agent.Agent_Advice_Method;
import com.infogen.hibernate.InfoGen_Hibernate;

/**
 * 自动关闭hibernate session的处理器
 * 
 * @author larry/larrylv@outlook.com/创建时间 2015年2月27日 下午6:11:09
 * @since 1.0
 * @version 1.0
 */
public class InfoGen_AOP_Handle_AutoClose extends AOP_Handle {
	private static final Logger LOGGER = LogManager.getLogger(InfoGen_AOP_Handle_AutoClose.class.getName());

	@Override
	public Agent_Advice_Method attach_method(String class_name, Method method, Annotation annotation) {
		String method_name = method.getName();
		Agent_Advice_Method advice_method = new Agent_Advice_Method();
		advice_method.setMethod_name(method_name);
		StringBuilder sbd = new StringBuilder();
		sbd.append("com.infogen.hibernate.event_handle.InfoGen_AOP_Handle_AutoClose.insert_after_call_back();");
		advice_method.setInsert_after(sbd.toString());

		sbd.setLength(0);
		sbd.append("com.infogen.hibernate.event_handle.InfoGen_AOP_Handle_AutoClose.add_catch_call_back();throw $e;");
		advice_method.setAdd_catch(sbd.toString());

		return advice_method;
	}

	public static void insert_after_call_back() {
		check_session();
	}

	public static void add_catch_call_back() {
		check_session();
	}

	private static void check_session() {
		try {
			// 必须将list session中未关闭的session关掉，并且回滚、报错
			ThreadLocal<List<Session>> list_session_thread_local = InfoGen_Hibernate.list_session_thread_local;
			List<Session> list = list_session_thread_local.get();
			if (list != null) {
				for (Session session : list) {
					if (session != null && session.isOpen()) {
						Transaction transaction = session.getTransaction();
						if (transaction.getStatus().name().equals(TransactionStatus.ACTIVE.name())) {
							transaction.rollback();
						}
						session.close();
					}
				}
				list.clear();
				list = null;
				list_session_thread_local.remove();
			}
		} catch (Exception e) {
			LOGGER.error("回滚 mysql 事务失败", e);
		}
	}
}
