package ru.rlrent.i_network.network.cache;

import com.annimon.stream.Stream;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Named;

import ru.surfstudio.android.filestorage.CacheConstant;
import ru.surfstudio.android.filestorage.encryptor.EmptyEncryptor;
import ru.surfstudio.android.filestorage.encryptor.Encryptor;

/**
 * фабрика простых кешей
 */
public class SimpleCacheFactory {

    private final String cacheDir;
    private final SimpleCacheUrlConnector cacheUrlConnector;
    private final Encryptor encryptor;
    private Map<SimpleCacheInfo, SimpleCache> caches = new HashMap<>();

    public SimpleCacheFactory(@Named(CacheConstant.EXTERNAL_CACHE_DIR_DAGGER_NAME) final String cacheDir,
                              SimpleCacheUrlConnector cacheUrlConnector) {
        this.cacheDir = cacheDir;
        this.cacheUrlConnector = cacheUrlConnector;
        this.encryptor = new EmptyEncryptor();
    }

    public SimpleCacheFactory(@Named(CacheConstant.EXTERNAL_CACHE_DIR_DAGGER_NAME) final String cacheDir,
                              SimpleCacheUrlConnector cacheUrlConnector,
                              Encryptor encryptor) {
        this.cacheDir = cacheDir;
        this.cacheUrlConnector = cacheUrlConnector;
        this.encryptor = encryptor;
    }

    public SimpleCache getSimpleCache(SimpleCacheInfo simpleCacheInfo) {
        SimpleCache cache = caches.get(simpleCacheInfo);
        if (cache == null) {
            cache = new SimpleCache(
                    cacheDir,
                    simpleCacheInfo.getCacheName(),
                    simpleCacheInfo.getMaxSize(),
                    encryptor);
            caches.put(simpleCacheInfo, cache);
        }
        return cache;
    }

    public void clearAllCache() {
        Stream.of(cacheUrlConnector.getSimpleCacheInfo())
                .map(this::getSimpleCache)
                .forEach(SimpleCache::clear);
    }
}
