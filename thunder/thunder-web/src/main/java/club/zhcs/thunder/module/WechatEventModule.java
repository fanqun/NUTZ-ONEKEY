package club.zhcs.thunder.module;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.nutz.dao.Cnd;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.mvc.View;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.weixin.bean.WxInMsg;
import org.nutz.weixin.bean.WxOutMsg;
import org.nutz.weixin.impl.AbstractWxHandler;
import org.nutz.weixin.impl.WxApi2Impl;
import org.nutz.weixin.repo.com.qq.weixin.mp.aes.AesException;
import org.nutz.weixin.repo.com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.nutz.weixin.spi.WxHandler;
import org.nutz.weixin.spi.WxResp;
import org.nutz.weixin.util.Wxs;

import club.zhcs.thunder.bean.qa.Nutzer;
import club.zhcs.thunder.biz.qa.NutzerService;
import club.zhcs.titans.nutz.module.base.AbstractBaseModule;

/**
 * @author 王贵源
 * @description 绑定
 * @Copyright 内部代码,禁止转发
 * @date 2016年1月7日 下午9:17:12
 *
 */
@Filters
public class WechatEventModule extends AbstractBaseModule {

	@Inject("wxApi")
	WxApi2Impl api;

	@Inject
	PropertiesProxy config;
	
	@Inject
	private NutzerService nutzerService;

	{
		Wxs.enableDevMode();
	}

	/**
	 * 只注册我们关心的事件（视频，语音等消息暂时一概忽略）
	 */
	protected WxHandler wxHandler = new AbstractWxHandler() {

		/**
		 * 微信服务号的验证
		 * 
		 * @see http://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login
		 */
		@Override
		public boolean check(String signature, String timestamp, String nonce, String key) {
			return Wxs.check(api.getToken(), signature, timestamp, nonce);
		};

		/**
		 * 默认返回的消息，应该是一个帮助类的提示文本
		 */
		@Override
		public WxOutMsg defaultMsg(WxInMsg msg) {
			return null;
		}

		/*
		 * 什么都不做，免得点菜单时各种触发defaultMsg by SK.Loda
		 * 
		 * @see
		 * com.kerbores.wx.api.impl.AbstractWxHandler#eventClick(com.kerbores
		 * .wx.bean.WxInMsg)
		 */
		@Override
		public WxOutMsg eventClick(WxInMsg msg) {
			return defaultMsg(msg);
		}

		/**
		 * 微信关注事件 1. 根据openid看看是谁,存入用户数据库 2. 首次关注，如有推荐人同时记录推荐人 3. 响应欢迎页面
		 */
		@Override
		public WxOutMsg eventSubscribe(WxInMsg msg) {
			WxResp resp = api.user_info(msg.getFromUserName(), "zh_CN");
			Nutzer nutzer = nutzerService.fetch(Cnd.where("openid", "=", msg.getFromUserName()));
			if (nutzer == null) {
				nutzer = new Nutzer();
				nutzer.setOpenid(msg.getFromUserName());
				String nickName  = resp.getString("nickname").replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
				nutzer.setCity(resp.getString("city"));
				nutzer.setCountry(resp.getString("country"));
				nutzer.setProvince(resp.getString("province"));
				nutzer.setNickName(nickName);
				nutzer.setHeadImgUrl(resp.getString("headimgurl"));
				nutzerService.save(nutzer);
				return Wxs.respText(null, "欢迎关注!");
			}else {
				return Wxs.respText(null, "欢迎回来!");
			}
			
		}

		/**
		 * 取消关注事件
		 */
		@Override
		public WxOutMsg eventUnsubscribe(WxInMsg msg) {
			// 将客户状态设置为禁用
			return defaultMsg(msg);
		}

		@Override
		public WxOutMsg eventView(WxInMsg msg) {
			return defaultMsg(msg);
		}

		@Override
		public WXBizMsgCrypt getMsgCrypt() {
			try {
				return new WXBizMsgCrypt(api.getAccessToken(), api.getEncodingAesKey(), api.getAppid());
			} catch (AesException e) {
				throw new RuntimeException(e);
			}
		};

		/**
		 * 处理文字消息
		 */
		@Override
		public WxOutMsg text(WxInMsg msg) {
			if (msg.getContent().startsWith("天气")) {
				
			}	
			return defaultMsg(msg);
		}

		@Override
		public WxOutMsg voice(WxInMsg msg) {
			return Wxs.respVoice(null, msg.getRecognition());
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.kerbores.nutz.module.base.AbstractBaseModule#_getNameSpace()
	 */
	@Override
	public String _getNameSpace() {
		return null;
	}

	/**
	 * 微信消息回调入口
	 * 
	 * @param key
	 * @param req
	 * @return
	 * @throws IOException
	 */
	@At({ "/wechat", "/wechat/?" })
	@Fail("http:200")
	public View msgIn(String key, HttpServletRequest req) throws IOException {
		return Wxs.handle(wxHandler, req, key);
	}

}