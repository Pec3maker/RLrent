package ru.rlrent.i_network.converter.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import ru.rlrent.i_network.converter.gson.safe.SafeConverter;
import ru.rlrent.i_network.network.error.ConversionException;
import ru.rlrent.i_network.network.response.BaseResponse;
import ru.surfstudio.android.logger.Logger;

/**
 * ResponseTypeAdapterFactory - кроме парсинга ответа,
 * конвертирует JsonSyntaxException -> ConversionException
 */
public class ResponseTypeAdapterFactory implements TypeAdapterFactory {

    public static final String PARSE_ERROR_MESSAGE_FORMAT = "Error when parse body: %s";
    private SafeConverterFactory safeConverterFactory;

    public ResponseTypeAdapterFactory(SafeConverterFactory safeConverterFactory) {
        this.safeConverterFactory = safeConverterFactory;
    }

    @Override
    @SuppressWarnings("squid:S1188")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
        return new TypeAdapter<T>() {

            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {
                JsonElement jsonElement = elementAdapter.read(in);
                //пытаемся применить безопасный парсинг для известных нарушений структуры Json
                SafeConverter<T> safeConverter = safeConverterFactory.getSafeConverter(type);
                if (safeConverter != null) {
                    return safeConverter.convert(ResponseTypeAdapterFactory.this, gson, jsonElement);
                }
                //если пытаемся распарсить элемент, производный от {@link BaseResponse} то в
                // случае ошибки эмитим ConversionException с текстом ответа
                if (BaseResponse.class.isAssignableFrom(type.getRawType())) {
                    try {
                        return parseElement(jsonElement, true);
                    } catch (JsonSyntaxException e) {
                        String body = jsonElement != null ? jsonElement.toString() : "";
                        String errorMessage = String.format(PARSE_ERROR_MESSAGE_FORMAT, body);
                        ConversionException conversionException = new ConversionException(errorMessage, e);
                        Logger.e(e, "parse error");
                        throw conversionException;

                    }
                }
                return parseElement(jsonElement, false);
            }

            /**
             * Метод для кастомизации парсинга элементов.
             *
             * @param isBaseResponse элемент, производный от {@link BaseResponse}
             */
            protected T parseElement(JsonElement jsonElement, boolean isBaseResponse) {
                return delegate.fromJsonTree(jsonElement);
            }
        }.nullSafe();
    }
}

