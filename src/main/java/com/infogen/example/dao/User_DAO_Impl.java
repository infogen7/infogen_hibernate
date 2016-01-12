package com.infogen.example.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.infogen.example.model.InfoGen_Demo_User;
import com.infogen.hibernate.InfoGen_Hibernate;
import com.infogen.hibernate.annotation.AutoClose;

/**
 * @author larry/larrylv@outlook.com/创建时间 2015年5月8日 下午3:47:28
 * @since 1.0
 * @version 1.0
 */
public class User_DAO_Impl implements User_DAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infogen.infogen_demo.dao.User_DAO#get_byaccount(java.lang.String)
	 */
	@AutoClose
	@Override
	public InfoGen_Demo_User get_byaccount(String account) {
		InfoGen_Demo_User object = null;
		Session session = InfoGen_Hibernate.createSession();
		Criteria criteria = session.createCriteria(InfoGen_Demo_User.class);
		criteria.add(Restrictions.eq("account", account));
		Object uniqueResult = criteria.uniqueResult();
		session.close();

		if (uniqueResult != null) {
			object = (InfoGen_Demo_User) uniqueResult;
		}
		return object;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.infogen.infogen_demo.dao.User_DAO#save(java.lang.String, java.lang.String)
	 */
	@AutoClose
	@Override
	public Boolean save(String account, String password) {
		Session createSession = InfoGen_Hibernate.createSession();
		InfoGen_Demo_User user = new InfoGen_Demo_User();
		user.setAccount(account);
		user.setPassword(password);
		Transaction transaction = createSession.beginTransaction();
		createSession.save(user);
		transaction.commit();
		createSession.close();
		return true;
	}
}
