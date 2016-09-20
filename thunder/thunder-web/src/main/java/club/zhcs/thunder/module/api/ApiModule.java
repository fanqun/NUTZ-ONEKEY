package club.zhcs.thunder.module.api;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.plugins.apidoc.annotation.Api;
import org.nutz.plugins.apidoc.annotation.ApiMatchMode;
import org.nutz.plugins.apidoc.annotation.ReturnKey;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file ApiModule.java
 *
 * @description API
 *
 * @time 2016年9月20日 下午9:06:05
 *
 */
@Api(author = "kerbores", name = "Rest api", description = " 提供一组 rest api", match = ApiMatchMode.ONLY)
@At("/api")
@Filters
public class ApiModule extends AbstractBaseModule {

	@Inject
	Dao dao;

	@Api(author = "kerbores", name = "数据库", description = "获取数据库信息", match = ApiMatchMode.ONLY,
			ok = {
					@ReturnKey(key = "db", description = " 数据库的 meta 信息")
			},
			fail = {
					@ReturnKey(key = "reason", description = "失败原因")
			}
			)
			@At
			public Result db() {

		return Result.success().addData("db", dao.meta());
	}


	@At
	public Result sigar() {
		return Result.success();
	}
}
