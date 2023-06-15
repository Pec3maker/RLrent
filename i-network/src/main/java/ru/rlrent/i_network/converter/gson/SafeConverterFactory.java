package ru.rlrent.i_network.converter.gson;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import ru.rlrent.i_network.converter.gson.safe.SafeConverter;

/**
 * предоставляет доступ к безопасным парсерам, которые предназначены для правильного парсинга ошибочных
 * ответов сервера
 */
public class SafeConverterFactory {

    private Map<Class, Function<TypeToken, SafeConverter>> safeConverterCreators = new HashMap<>();
    private Map<Class, SafeConverter> initializedSafeConverters = new HashMap<>();

    public SafeConverterFactory() {
        //inject constructor
    }

    @Nullable
    <T> SafeConverter<T> getSafeConverter(TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        SafeConverter<T> result = initializedSafeConverters.get(rawType);
        if (result == null) {
            result = tryCreateSafeConverter(type);
            if (result != null) {
                initializedSafeConverters.put(rawType, result);
            }
        }
        return result;
    }

    public void putSafeConverter(@NonNull Class clazz, @NonNull Function<TypeToken, SafeConverter> converter) {
        safeConverterCreators.put(clazz, converter);
    }

    @Nullable
    private <T> SafeConverter<T> tryCreateSafeConverter(TypeToken<T> type) {
        Class<? super T> rawType = type.getRawType();
        Function<TypeToken, SafeConverter> safeConverterCreator = safeConverterCreators.get(rawType);
        if (safeConverterCreator != null) {
            return safeConverterCreator.apply(type);
        }
        return null;
    }

    public interface Function<T, R> {
        R apply(T value);
    }
}
