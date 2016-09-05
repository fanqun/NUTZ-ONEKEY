var ioc = {
	wechat : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		fields : {
			ignoreResourceNotFound : true,
			utf8 : false,
			paths : 'wechat'
		}
	},
	wxJsapiTicketStore : {
		type : "org.nutz.weixin.at.impl.MemoryJsapiTicketStore"
	},
	wxApi : {
		type : "org.nutz.weixin.impl.WxApi2Impl",
		fields : {
			token : {
				java : "$wechat.get('token')"
			},
			appid : {
				java : "$wechat.get('appid')"
			},
			appsecret : {
				java : "$wechat.get('appsecret')"
			},
			encodingAesKey : {
				java : "$wechat.get('encodingAesKey')"
			},
			jsapiTicketStore : {
				refer : 'wxJsapiTicketStore'
			}
		}
	}
}