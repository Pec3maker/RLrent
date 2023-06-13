package ru.rlrent.base.scope

import javax.inject.Scope

/**
 * Скоуп для интеракторов для работы с сетью при наличии сессии, кроме авторизации и регистрации.
 *
 * Необходимо для реализации ssl-пиннинга, так как после авторизации и пиннинга
 * получает fingerprint, который затем будет использован.
 *
 * Аннотация обусловлена тем, что при получении нового fingerprint от сервера необходимо
 * сделать ребилд для OkHttpClient.
 *
 * Данный скоуп инициализируется после успешной авторизации и получения fingerprint'a от сервера.
 *
 * Подробности в технической документации: docs/common/persession.md
 */
@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class PerSession