package club.zhcs.thunder.module.qa;

import org.nutz.http.Http;
import org.nutz.json.Json;
import org.nutz.lang.Strings;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

/**
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project thunder-web
 *
 * @file WechatQAModule.java
 *
 * @description WechatQAModule.java
 *
 * @time 2016年9月5日 上午9:45:45
 *
 */
@At("qa")
// @Filters({ @By(type = CheckSession.class, args = {
// SessionKeys.WECHAT_USER_KEY, "/qa/bind" }) })
@Filters
public class WechatQAModule extends AbstractBaseModule {

	@At
	@GET
	@Ok("beetl:pages/qa/topic.html")
	public Result topic(@Param(value = "page", df = "1") int page, @Param("tab") String tab, @Param("tag") String tag, @Param("search") String search,
			@Param(value = "limit", df = "15") int limit) {
		String topicApi = "https://nutz.cn/yvr/api/v1/topics?page=" + page + "&limit=" + limit;
		if (Strings.isNotBlank(tab)) {
			topicApi += "&tab=" + tab;
		}
		if (Strings.isNotBlank(tag)) {
			topicApi += "&tab=" + tag;
		}
		if (Strings.isNotBlank(search)) {
			topicApi += "&tab=" + search;
		}
		return Result.success().addData("topics", Json.fromJson(Http.get(topicApi).getContent())).addData("page", page).addData("tab", tab).addData("tag", tag).addData("search", search).addData("limit", limit);
	}
	
	@At("/topic/json")
	@POST
	public Result topicJson(@Param(value = "page", df = "1") int page, @Param("tab") String tab, @Param("tag") String tag, @Param("search") String search,
			@Param(value = "limit", df = "15") int limit) {
		String topicApi = "https://nutz.cn/yvr/api/v1/topics?page=" + page + "&limit=" + limit;
		if (Strings.isNotBlank(tab)) {
			topicApi += "&tab=" + tab;
		}
		if (Strings.isNotBlank(tag)) {
			topicApi += "&tab=" + tag;
		}
		if (Strings.isNotBlank(search)) {
			topicApi += "&tab=" + search;
		}
		return Result.success().addData("topics", Json.fromJson(Http.get(topicApi).getContent())).addData("page", page).addData("tab", tab).addData("tag", tag).addData("search", search).addData("limit", limit);
	}

	@At("/topic/detail/*")
	@Ok("beetl:pages/qa/topic_detail.html")
	public Result detail(String id) {
		return Result.success().addData("topic", Json.fromJson(Http.get("https://nutz.cn/yvr/api/v1/topic/" + id).getContent()));
	}

}
