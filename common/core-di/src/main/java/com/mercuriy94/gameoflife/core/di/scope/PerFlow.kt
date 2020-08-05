package com.mercuriy94.gameoflife.core.di.scope

import javax.inject.Scope

/**
 * Скуоп жизненого цикла для флоу
 * (Fow) - набор фич (Feature) которые могут решить какую то одну задачу.
 * Базовым примеров флоу может быть авторизация пользователя (LoginFlow):
 *  - Фича ввода емаила, номера телефона и пароля
 *  - Фича проверка подлинности пользователя вводом выссланного пароля на почту или номер телефона
 *
 * @author Nikita Marsyukov
 */
@Scope
annotation class PerFlow