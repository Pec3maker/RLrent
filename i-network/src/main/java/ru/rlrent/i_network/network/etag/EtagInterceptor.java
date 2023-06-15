package ru.rlrent.i_network.network.etag;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import ru.rlrent.i_network.network.BaseServerConstants;
import ru.rlrent.i_network.network.etag.storage.EtagStorage;
import ru.surfstudio.android.logger.Logger;

/**
 * добавляет etag в header запроса и запоминает etag из ответа. Etag получают и сохраняют с помощью
 * EtagStorage. Только если в запросе присутствует header queryMode: ServerConstant.QUERY_MODE_ONLY_IF_CHANGED, в запрос будет
 * добавлен header c etag, header queryMode будет удален.
 */
public class EtagInterceptor implements Interceptor {

    private final EtagStorage etagStorage;

    public EtagInterceptor(EtagStorage etagStorage) {
        this.etagStorage = etagStorage;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final HttpUrl url = originalRequest.url();
        final String headerQueryMode = originalRequest.header(BaseServerConstants.HEADER_QUERY_MODE);
        boolean supportEtag = headerQueryMode != null;

        if (supportEtag) {
            Headers.Builder newHeadersBuilder = originalRequest.headers().newBuilder();
            newHeadersBuilder.removeAll(BaseServerConstants.HEADER_QUERY_MODE);
            int queryMode = Integer.parseInt(headerQueryMode);

            if (queryMode == BaseServerConstants.QUERY_MODE_ONLY_IF_CHANGED) {
                String outputEtag = etagStorage.getEtag(url.toString());
                newHeadersBuilder.add(EtagConstants.HEADER_REQUEST_ETAG, outputEtag);
            }
            final Request request = originalRequest.newBuilder()
                    .headers(newHeadersBuilder.build())
                    .build();

            final Response response = chain.proceed(request);

            String inputEtag = response.header(EtagConstants.HEADER_RESPONSE_ETAG);
            if (response.isSuccessful()) {
                if (inputEtag != null) {
                    etagStorage.putEtag(url.toString(), inputEtag);
                } else {
                    Logger.e("missing etag in response, request url: %s", url.toString());
                }
            }
            return response;
        } else {
            return chain.proceed(originalRequest);
        }
    }
}
