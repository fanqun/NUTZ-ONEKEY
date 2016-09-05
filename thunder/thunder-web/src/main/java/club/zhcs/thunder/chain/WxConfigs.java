package club.zhcs.thunder.chain;

import java.util.concurrent.TimeUnit;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.weixin.spi.WxApi2;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * 
 * @author Kerbores(kerbores@gmail.com)
 *
 * @project platform-web-customer
 *
 * @file WxConfigs.java
 *
 * @description 微信 jssdk 的配置信息获取
 *
 * @time 2016年7月27日 下午12:52:59
 *
 */
@IocBean
public class WxConfigs {
	@Inject("wxCustomerApi")
	WxApi2 api;

	/**
	 * url 和配置的一个缓冲
	 */
	LoadingCache<String, NutMap> cache;

	private String[] apis = "onMenuShareTimeline,onMenuShareAppMessage,onMenuShareQQ,onMenuShareWeibo,onMenuShareQZone,startRecord,stopRecord,onVoiceRecordEnd,playVoice,pauseVoice,stopVoice,onVoicePlayEnd,uploadVoice,downloadVoice,chooseImage,previewImage,uploadImage,downloadImage,translateVoice,getNetworkType,openLocation,getLocation,hideOptionMenu,showOptionMenu,hideMenuItems,showMenuItems,hideAllNonBaseMenuItem,showAllNonBaseMenuItem,closeWindow,scanQRCode,chooseWXPay,openProductSpecificView,addCard,chooseCard,openCard"
			.split(",");

	Log log = Logs.get();

	public LoadingCache<String, NutMap> getCache() {
		if (cache == null) {
			cache = get();
		}
		return cache;
	}

	/**
	 * @return
	 *
	 * @author 王贵源
	 */
	private LoadingCache<String, NutMap> get() {
		// 比微信的缓存时间短那么一点点儿
		return CacheBuilder.newBuilder().maximumSize(2000).expireAfterAccess(7000, TimeUnit.SECONDS).removalListener(new RemovalListener<String, NutMap>() {

			@Override
			public void onRemoval(RemovalNotification<String, NutMap> notification) {
				log.debug(notification.getKey() + " removed....");
			}
		}).build(new CacheLoader<String, NutMap>() {

			@Override
			public NutMap load(String key) throws Exception {
				log.debug(key + " loading.... ");
				return loadConfig(key);
			}
		});
	}

	/**
	 * @param key
	 * @return
	 *
	 * @author 王贵源
	 */
	protected NutMap loadConfig(String key) {
		log.debug("Generating WX JsSDKConfig using the key url -> " + key);
		return api.genJsSDKConfig(key, apis);
	}
}
