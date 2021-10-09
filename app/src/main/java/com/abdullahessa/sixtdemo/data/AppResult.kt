package com.abdullahessa.sixtdemo.data

/**
 * @author Created by Abdullah Essa
 */
sealed class AppResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : AppResult<T>()
    data class Error(val throwable: Throwable) : AppResult<Nothing>()

    /**
     * Returns `true` if this instance represents a successful outcome.
     * In this case [isError] returns `false`.
     */
    val isSuccess: Boolean get() = this is Success

    /**
     * Returns `true` if this instance represents a failed outcome.
     * In this case [isSuccess] returns `false`.
     */
    val isError: Boolean get() = this is Error

    /**
     * Returns the encapsulated value if this instance represents [Success] or `null`
     * if it is [Error].
     *
     */
    fun getOrNull(): T? = when (this) {
        is Error -> null
        is Success -> this.data
    }

    /**
     * Returns the encapsulated [Throwable] exception if this instance represents [Error] or `null`
     * if it is [Success].
     */
    fun exceptionOrNull(): Throwable? = when (this) {
        is Error -> this.throwable
        else -> null
    }

    /**
     * Callback for success
     */
    inline fun onSuccess(onSuccessBlock: (T) -> Unit) = apply { getOrNull()?.also(onSuccessBlock) }

    /**
     * Callback for Error
     */
    inline fun onError(onErrorBlock: (Throwable) -> Unit) =
        apply { exceptionOrNull()?.also(onErrorBlock) }

    /**
     * Map the success data to another model
     */
    inline fun <R : Any> mapSuccess(block: (T) -> R): AppResult<R> = when (this) {
        is Success -> Success(block(data))
        is Error -> Error(throwable)
    }

    /**
     * Map the error
     */
    inline fun mapError(block: (Throwable) -> Throwable): AppResult<T> = when (this) {
        is Success -> Success(data)
        is Error -> Error(block(throwable))
    }

    companion object {
        /**
         * Wrap any call in a [AppResult] object
         */
        inline fun <T : Any> wrap(block: () -> T): AppResult<T> = kotlin.runCatching { block() }
            .fold({
                Success(it)
            }, {
                Error(it)
            })
    }
}
