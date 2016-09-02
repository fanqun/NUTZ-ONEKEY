package club.zhcs.thunder.module.nop;

import java.io.IOException;

import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.plugins.nop.NOPConfig;
import org.nutz.plugins.nop.core.NOPData;
import org.nutz.plugins.nop.server.NOPSignFilter;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file NOPModule.java
 *
 * @description
 *
 * @time 2016年8月31日 下午8:40:23
 *
 */
@At("test")
@Filters(@By(type = NOPSignFilter.class))
public class NOPModule extends AbstractBaseModule {

	@At
	public NOPData hello() {
		return NOPData.success().addData("msg", "hello nop");
	}

	@At
	public NOPData calc(@Attr(NOPConfig.parasKey) NutMap data) throws IOException {
		return NOPData.success().addData("r", data);
	}

	@At
	public NOPData file(@Attr(NOPConfig.parasKey) NutMap data) throws IOException {
		return NOPData.success().addData("r", data);
	}
}
