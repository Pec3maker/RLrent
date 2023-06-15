package ru.rlrent.i_network.network.etag.storage;

import javax.inject.Named;

import ru.surfstudio.android.filestorage.CacheConstant;
import ru.surfstudio.android.filestorage.naming.Sha256NamingProcessor;
import ru.surfstudio.android.filestorage.processor.FileProcessor;
import ru.surfstudio.android.filestorage.storage.BaseTextFileStorage;

final public class EtagCache extends BaseTextFileStorage {

    private static final int CACHE_SIZE = 5000;

    public EtagCache(@Named(CacheConstant.INTERNAL_CACHE_DIR_DAGGER_NAME) final String cacheDir) {
        super(new FileProcessor(cacheDir, "etag", CACHE_SIZE), new Sha256NamingProcessor());
    }
}
