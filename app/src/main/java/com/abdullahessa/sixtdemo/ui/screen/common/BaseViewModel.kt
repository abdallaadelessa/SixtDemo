package com.abdullahessa.sixtdemo.ui.screen.common

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
abstract class BaseViewModel(private val appContext: Context) : ViewModel() {

    /**
     * Returns a localized string from the application's package's
     * default string table.
     *
     * @return The string data associated with the resource, stripped of styled
     *         text information.
     */
    protected fun @receiver:StringRes Int.get(): String = appContext.getString(this)

    /**
     * Returns a localized formatted string from the application's package's
     * default string table, substituting the format arguments as defined in
     * {@link java.util.Formatter} and {@link java.lang.String#format}.
     *
     * @param formatArgsResId The format arguments that will be used for
     *                   substitution.
     * @return The string data associated with the resource, formatted and
     *         stripped of styled text information.
     */
    protected fun @receiver:StringRes Int.get(@StringRes vararg formatArgsResId: Int): String {
        val formatArgs: Array<String> =
            formatArgsResId.map { appContext.getString(it) }.toTypedArray()
        return appContext.getString(this, *formatArgs)
    }

    /**
     * Returns a localized formatted string from the application's package's
     * default string table, substituting the format arguments as defined in
     * {@link java.util.Formatter} and {@link java.lang.String#format}.
     *
     * @param formatArgs The format arguments that will be used for substitution.
     * @return The string data associated with the resource, formatted and
     *         stripped of styled text information.
     */
    protected fun @receiver:StringRes Int.get(
        vararg formatArgs: String
    ): String = appContext.getString(this, *formatArgs)

}
