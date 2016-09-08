package club.zhcs.thunder.module.admin.settings;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.GET;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;
import org.nutz.weixin.bean.WxMenu;
import org.nutz.weixin.impl.WxApi2Impl;
import org.nutz.weixin.spi.WXAccountApi.Type;
import org.nutz.weixin.spi.WxResp;

import club.zhcs.thunder.bean.config.WechatMenu;
import club.zhcs.thunder.bean.config.WxConfig;
import club.zhcs.thunder.biz.config.WechatMenuService;
import club.zhcs.thunder.biz.config.WxConfigService;
import club.zhcs.thunder.ext.shiro.anno.ThunderRequiresPermissions;
import club.zhcs.thunder.vo.InstallPermission;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;
import club.zhcs.titans.utils.db.Result;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description 微信配置
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月3日 上午8:45:36
 */
@At("/setting/wechat")
public class WechatSettingModule extends AbstractBaseModule {

	@Inject
	WxConfigService wxConfigService;

	@Inject
	PropertiesProxy config;

	@Inject
	WxApi2Impl wxApi;

	@Inject
	WechatMenuService wechatMenuService;

	@At("/menu/add")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	@GET
	@Ok("beetl:pages/admin/setting/menu_add.html")
	public Result addMenu() {
		return Result.success().addData("types", WechatMenu.Type.values()).addData("menus",
				wechatMenuService.query(Cnd.where("parentId", "=", 0)));
	}

	@At("/menu/add")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	@POST
	public Result addMenu(@Param("..") WechatMenu menu) {

		return wechatMenuService.save(menu) != null ? Result.success() : Result.fail("添加菜单失败!");
	}

	@At
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	public Result addOrUpdate(@Param("..") WxConfig config) {
		if (wxConfigService.queryAll().size() == 0) {
			config = wxConfigService.save(config);
			if (config != null) {
				modifyApi(config);
			}
			return config == null ? Result.fail("配置失败!<br>失败原因:添加失败") : Result.success();
		} else {
			int r = wxConfigService.update(config, Cnd.where("1", "=", 1), "appid", "appsecret", "token",
					"encodingAesKey");
			if (r == 1) {
				modifyApi(config);
			}
			return r == 1 ? Result.success() : Result.fail("配置失败!<br>失败原因:修改失败");
		}
	}

	@At("/menu/asyn")
	@GET
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	@Ok("beetl:pages/admin/setting/menu_asyn.html")
	public Result asynMenu() {
		List<WechatMenu> menus = wechatMenuService.query(Cnd.where("parentId", "=", 0).orderBy("index", "ASC"));
		Lang.each(menus, new Each<WechatMenu>() {

			@Override
			public void invoke(int index, WechatMenu menu, int length) throws ExitLoop, ContinueLoop, LoopException {
				menu.setSubMenus(
						wechatMenuService.query(Cnd.where("parentId", "=", menu.getId()).orderBy("index", "ASC")));
			}
		});

		return Result.success().addData("menus", menus).addData("wxMenu", wechatMenuService.exchange(menus));
	}

	@At("/menu/asyn")
	@POST
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	public Result asynMenuToWechat() {
		List<WxMenu> menus = wechatMenuService.getWxMenus();
		WxResp resp = wxApi.menu_create(menus);
		if (resp.ok()) {
			return Result.success();
		}
		return Result.fail(resp.errmsg());
	}

	@At("/menu/delete")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	public Result deleteMenu(@Param("id") int id) {
		return wechatMenuService.delete(id) == 1 ? Result.success() : Result.fail("删除菜单失败!");
	}

	@At("/menu/edit/*")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	@GET
	@Ok("beetl:pages/admin/setting/menu_add.html")
	public Result editMenu(int id) {
		return Result.success().addData("types", WechatMenu.Type.values())
				.addData("menus", wechatMenuService.query(Cnd.where("parentId", "=", 0)))
				.addData("menu", wechatMenuService.fetch(id));
	}

	@At("/menu/edit")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	@POST
	public Result editMenu(@Param("..") WechatMenu menu) {
		return wechatMenuService.updateIgnoreNull(menu) == 1 ? Result.success() : Result.fail("添加菜单失败!");
	}

	@At("/")
	@Ok("beetl:pages/admin/setting/wechat.html")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	public Result index(HttpServletRequest request) {
		return Result.success().addData("wechat", wxApi)
				// .addData("wxConfig",
				// wxConfigService.fetch(Cnd.orderBy().asc("id")))
				.addData("config", config)
				.addData("context", config.get("client.context", Mvcs.getReq().getContextPath()));
	}

	@At
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	public Result menu() {
		return Result.success().addData("menus", wechatMenuService.queryAll());// 一共最多也就15条数据,全部显示了就得了
	}

	protected void modifyApi(WxConfig config) {
		wxApi.setAppid(config.getAppid());
		wxApi.setAppsecret(config.getAppsecret());
		wxApi.setEncodingAesKey(config.getEncodingAesKey());
		wxApi.setToken(config.getToken());
		wxApi.getAccessTokenStore().save(null, 0, System.currentTimeMillis());// 这个代码可以过期当前
																				// token
																				// 吧
		wxApi.getJsapiTicketStore().save(null, 0, System.currentTimeMillis());// token
																				// 都过期一下
	}

	@At("/qr/*")
	@Ok(">>:${obj}")
	@ThunderRequiresPermissions(InstallPermission.CONFIG_WECHAT)
	public String qr() {
		WxResp resp = wxApi.createQRTicket(0, Type.EVERARGS, "test config");
		return wxApi.qrURL(resp.getString("ticket"));
	}
}
