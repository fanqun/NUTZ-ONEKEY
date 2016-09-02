package club.zhcs.thunder.chain;

import javax.servlet.http.HttpServletRequest;

import org.nutz.json.Json;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.impl.processor.AbstractProcessor;

import club.zhcs.thunder.Application;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project platform-web-customer
 *
 * @file WxJsSdkConfigProcessor.java
 *
 * @description JSSDK 注入
 *
 * @time 2016年7月27日 下午12:53:27
 *
 */
public class WxJsSdkConfigProcessor extends AbstractProcessor {
	Log log = Logs.get();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.nutz.mvc.Processor#process(org.nutz.mvc.ActionContext)
	 */
	@Override
	public void process(ActionContext ac) throws Throwable {

		HttpServletRequest request = ac.getRequest();
		if (request.getMethod().equalsIgnoreCase("POST")) {// 屏蔽掉POST请求
			doNext(ac);
			return;
		}
		log.debug("inject wechat jssdk config....");
		String uri = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
		String url = "http://thunder.kerbores.com"  + uri;
		WxConfigs configs = ac.getIoc().get(WxConfigs.class);
		NutMap jsConfig = configs.loadConfig(url);
		Mvcs.getReq().setAttribute("appid", jsConfig.get("appId"));// 给Velocity模板引用一下
		Mvcs.getReq().setAttribute("jsConfig", Json.toJson(jsConfig));
		doNext(ac);
	}

}
