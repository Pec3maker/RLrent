package ru.rlrent.i_network.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Класс для описания базовой части Url сервера api
 */
public class BaseUrl {
    private static final String START_SLASH = "^/";
    private static final String END_SLASH = "/$";
    @NonNull
    private final String base;
    @Nullable
    private String apiVersion;

    public BaseUrl(@NonNull String base, @Nullable String apiVersion) {
        this.base = base.replaceFirst(END_SLASH, "");
        if (apiVersion != null) {
            this.apiVersion = apiVersion.replaceFirst(START_SLASH, "")
                    .replaceFirst(END_SLASH, "");
        }
    }

    @NonNull
    public String getBase() {
        return base;
    }

    @Nullable
    public String getApiVersion() {
        return apiVersion;
    }

    @NonNull
    @Override
    public String toString() {
        if (apiVersion != null) {
            //todo: проверить на реальном проекте:
            // если часть урла начинается с /, то после apiVersion добавлять его не нужно
            return base + "/" + apiVersion;
        }
        return base;
    }
}
