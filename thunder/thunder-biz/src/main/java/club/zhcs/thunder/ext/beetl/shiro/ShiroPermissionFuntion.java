package club.zhcs.thunder.ext.beetl.shiro;

import org.apache.shiro.SecurityUtils;
import org.beetl.core.Context;
import org.beetl.core.Function;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description shiro的权限检查函数
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月23日 上午8:49:43
 */
public class ShiroPermissionFuntion implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] permission, Context context) {
		if (permission == null || permission.length < 1) {
			return false;
		}
		return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(permission[0].toString());
	}

}
