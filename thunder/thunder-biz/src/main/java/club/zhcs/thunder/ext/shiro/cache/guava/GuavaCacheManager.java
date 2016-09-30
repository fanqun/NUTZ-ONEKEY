package club.zhcs.thunder.ext.shiro.cache.guava;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class GuavaCacheManager implements CacheManager {

	GuavaLoadingCacheManager cacheManager;

	@Override
	public <K, V> Cache<K, V> getCache(String arg0) throws CacheException {
		return new GuavaCache<K, V>(cacheManager);
	}

	public GuavaLoadingCacheManager getCacheManager() {
		return cacheManager;
	}

	public void setCacheManager(GuavaLoadingCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

}
