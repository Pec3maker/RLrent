package ru.rlrent.i_network.network.etag.storage;

public final class EtagStorage {

    private EtagCache etagCache;

    public EtagStorage(final EtagCache etagCache) {
        this.etagCache = etagCache;
    }

    /**
     * Метод, возвращающий e-tag по необходимому url или пустую строку в случае, если e-tag не найден
     *
     * @param url - ключ
     * @return - уникальный e-tag для конкретного {@param url}
     */
    public String getEtag(final String url) {
        final String etag = etagCache.get(url);
        return etag != null ? etag : "";
    }

    /**
     * Метод, сохраняющий e-tag по конкретному ключу или перезаписывает текущий e-tag
     *
     * @param url  - ключ
     * @param etag - уникальный e-tag для конкретного {@param url}
     */
    public void putEtag(final String url, final String etag) {
        etagCache.put(url, etag);
    }
}
