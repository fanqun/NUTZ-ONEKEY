package club.zhcs.thunder.ext.beetl.wechat;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.nutz.weixin.impl.WxApi2Impl;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @projectthunder-biz
 *
 * @file WechatAuthFun.java
 *
 * @description WechatAuthFun.java
 *
 * @time 2016年9月5日 下午1:08:37
 *
 */
public class WechatAuthFun implements Function {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.beetl.core.Function#call(java.lang.Object[],
	 * org.beetl.core.Context)
	 */
	@Override
	public Object call(Object[] paras, Context ctx) {
		WxApi2Impl wxApi = Mvcs.getIoc().get(WxApi2Impl.class, "wxApi");
		PropertiesProxy config = Mvcs.getIoc().get(PropertiesProxy.class, "config");
		if (paras == null) {
			return null;
		}
		String uri = paras[0].toString();
		String contextPath = Mvcs.getReq().getContextPath();
		return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect",
				wxApi.getAppid(), config.get("http.protocol") + "://" + config.get("base.domain") + (Strings.isBlank(contextPath) ? "" : "/" + contextPath)
						+ (Strings.startsWithChar(uri, '/') ? uri : "/" + uri));
	}

}
