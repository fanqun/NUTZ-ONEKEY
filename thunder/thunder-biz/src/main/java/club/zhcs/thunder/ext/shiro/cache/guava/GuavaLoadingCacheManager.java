package club.zhcs.thunder.ext.shiro.cache.guava;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

public class GuavaLoadingCacheManager {

	private LoadingCache<byte[], byte[]> cache;

	private Map<byte[], byte[]> cacheMap = Maps.newConcurrentMap();

	private long size = 100;

	private long cacheSeconds = 300;

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getCacheSeconds() {
		return cacheSeconds;
	}

	public void setCacheSeconds(long cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}

	public LoadingCache<byte[], byte[]> getCache() {
		if (cache == null) {
			return CacheBuilder.newBuilder().maximumSize(size).weakKeys().softValues().expireAfterAccess(cacheSeconds, TimeUnit.SECONDS).build(new CacheLoader<byte[], byte[]>() {

				@Override
				public byte[] load(byte[] arg0) throws Exception {
					return cacheMap.get(arg0);
				}
			});
		}
		return cache;
	}
}
