/**
 * @author larry/larrylv@outlook.com
 * @date 创建时间 2015年5月8日 下午3:32:59
 * @version 1.0
 */
package com.infogen.example.dao;

import com.infogen.example.model.InfoGen_Demo_User;

/**
 * @author larry/larrylv@outlook.com/创建时间 2015年5月8日 下午3:32:59
 * @since 1.0
 * @version 1.0
 */
public interface User_DAO {
	public InfoGen_Demo_User get_byaccount(String account);

	public Boolean save(String account, String password) ;
}
