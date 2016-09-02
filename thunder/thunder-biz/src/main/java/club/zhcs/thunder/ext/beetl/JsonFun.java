package club.zhcs.thunder.ext.beetl;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nutz.json.Json;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project platform-service
 *
 * @file JsonFun.java
 *
 * @description Json 函数
 *
 * @time 2016年8月4日 上午9:13:11
 *
 */
public class JsonFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] paras, Context arg1) {
		if (paras == null) {
			return null;
		}
		return Json.toJson(paras[0]);
	}

}
