package club.zhcs.thunder.ext.shiro.cache.guava;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.nutz.lang.ContinueLoop;
import org.nutz.lang.Each;
import org.nutz.lang.ExitLoop;
import org.nutz.lang.Lang;
import org.nutz.lang.LoopException;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import club.zhcs.thunder.ext.shiro.cache.SerializeUtils;

import com.google.common.collect.Sets;

public class GuavaCache<K, V> implements Cache<K, V> {

	Log log = Logs.get();

	GuavaLoadingCacheManager cacheManager;

	public GuavaCache(GuavaLoadingCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void clear() throws CacheException {
		// TODO Auto-generated method stub

	}

	@Override
	public V get(K k) throws CacheException {
		try {
			return (V) SerializeUtils.deserialize(cacheManager.getCache().get(SerializeUtils.serialize(k)));
		} catch (ExecutionException e) {
			log.debug(e);
			return null;
		}
	}

	@Override
	public Set<K> keys() {
		Set<K> target = Sets.newConcurrentHashSet();
		Lang.each(cacheManager.getCache().asMap().keySet(), new Each<byte[]>() {

			@Override
			public void invoke(int index, byte[] ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				target.add((K) SerializeUtils.deserialize(ele));
			}
		});
		return target;
	}

	@Override
	public V put(K k, V v) throws CacheException {
		cacheManager.getCache().put(SerializeUtils.serialize(k), SerializeUtils.serialize(v));
		return v;
	}

	@Override
	public V remove(K k) throws CacheException {
		V v = get(k);
		cacheManager.getCache().invalidate(SerializeUtils.serialize(k));
		return v;
	}

	@Override
	public int size() {
		return cacheManager.getCache().asMap().size();
	}

	@Override
	public Collection<V> values() {
		Collection<V> target = Sets.newConcurrentHashSet();
		Lang.each(cacheManager.getCache().asMap().values(), new Each<byte[]>() {

			@Override
			public void invoke(int index, byte[] ele, int length) throws ExitLoop, ContinueLoop, LoopException {
				target.add((V) SerializeUtils.deserialize(ele));
			}
		});
		return target;
	}

}
