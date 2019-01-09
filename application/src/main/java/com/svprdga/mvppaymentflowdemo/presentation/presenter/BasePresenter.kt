package com.svprdga.mvppaymentflowdemo.presentation.presenter

import com.svprdga.mvppaymentflowdemo.util.Logger

/**
 * Class responsible for the base methods that a presenter must have.
 */
abstract class BasePresenter(protected var log: Logger) {

    /**
     * Trace a [Exception].
     * @param useCase Use case whose caused the exception.
     * @param exception [Exception] to log.
     */
    protected fun logException(useCase: String, exception: Exception) {
        log.error(
            "ConnectivityException while executing " + useCase + ":" +
                    exception.message
        )
    }

    /**
     * Trace a [Exception].
     *
     * @param useCase   Use case whose caused the exception.
     * @param throwable [Throwable] to log.
     */
    protected fun logException(useCase: String, throwable: Throwable) {
        log.error(
            "ConnectivityException while executing " + useCase + ":" +
                    throwable.message
        )
    }

}
