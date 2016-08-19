var ioc = {
	config : {
		type : "org.nutz.ioc.impl.PropertiesProxy",
		// args :[false,'conf'],
		fields : {
			ignoreResourceNotFound : true,
			utf8 : false,
			paths : 'conf'
		}
	}
}