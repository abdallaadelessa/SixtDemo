package com.abdullahessa.sixtdemo.app.extensions

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
/**
 * @return T Enum value from string or null
 */
inline fun <reified T : Enum<*>> String.toEnumOrNull(): T? =
    T::class.java.enumConstants?.firstOrNull { it.name.equals(other = this, ignoreCase = true) }

