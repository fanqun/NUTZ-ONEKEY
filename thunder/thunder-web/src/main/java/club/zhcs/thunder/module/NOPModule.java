package club.zhcs.thunder.module;

import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.plugins.nop.NOPConfig;
import org.nutz.plugins.nop.core.NOPData;
import org.nutz.plugins.nop.server.NOPSignFilter;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;

@At("test")
@Filters(@By(type = NOPSignFilter.class))
public class NOPModule extends AbstractBaseModule {

	@At
	public NOPData hello() {
		return NOPData.success().addData("msg", "hello nop");
	}

	@At
	public NOPData calc(@Attr(NOPConfig.parasKey) NutMap data) {

		return NOPData.success().addData("r", data);
	}

	@At
	public NOPData file(@Attr(NOPConfig.parasKey) NutMap data) {

		return NOPData.success().addData("r", data);
	}

}
