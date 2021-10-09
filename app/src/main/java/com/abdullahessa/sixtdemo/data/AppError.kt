package com.abdullahessa.sixtdemo.data

/**
 * Used to wrap error message and cause so that the presentation
 * layer can handle it
 * @author Abdullah Essa
 */
open class AppError(message: String, cause: Throwable? = null) : Exception(message, cause)

/**
 * Used to inform the presentation layer about connection error
 * @author Abdullah Essa
 */
class AppConnectionError(cause: Throwable) : AppError("No Internet Connection", cause)


/**
 * Used to wrap the http error code, message and cause so that the presentation
 * layer can handle it
 * @author Abdullah Essa
 */
class AppHttpError(val code: Int, message: String, cause: Throwable) : AppError(message, cause)
