package com.abdullahessa.sixtdemo.ui.screen.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
@SuppressLint("StaticFieldLeak")
abstract class BaseViewModel<T : BaseState>(
    savedStateHandle: SavedStateHandle,
    private val appContext: Context
) : ViewModel() {

    //region Base state

    /**
     *
     */
    abstract val initialState: T

    private val mutableStateFlow: MutableStateFlow<T> = savedStateHandle.getStateFlow(
        scope = viewModelScope,
        key = "key_state",
        initialValue = initialState
    )

    private val state: T
        get() = mutableStateFlow.value

    /**
     *
     */
    val stateFlow: Flow<T>
        get() = mutableStateFlow


    protected fun publishState(updateStateBlock: (T) -> T = { it }): T {
        val oldState = state
        val newState = updateStateBlock(oldState)
        // skip if the state is the same
        if (oldState == newState) return newState
        mutableStateFlow.tryEmit(newState)
        return newState
    }

    private fun <T> SavedStateHandle.getStateFlow(
        scope: CoroutineScope,
        key: String,
        initialValue: T
    ): MutableStateFlow<T> {
        val liveData = getLiveData(key, initialValue)
        val stateFlow = MutableStateFlow(initialValue)

        val observer = Observer<T> { value ->
            if (value != stateFlow.value) {
                stateFlow.value = value
            }
        }
        liveData.observeForever(observer)

        stateFlow.onCompletion {
            withContext(Dispatchers.Main.immediate) {
                liveData.removeObserver(observer)
            }
        }.onEach { value ->
            withContext(Dispatchers.Main.immediate) {
                if (liveData.value != value) {
                    liveData.value = value
                }
            }
        }.launchIn(scope)

        return stateFlow
    }

    //endregion

    //region String Resources

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

    //endregion

}


/**
 * @author Created by Abdullah Essa on 09.10.21.
 */
interface BaseState
