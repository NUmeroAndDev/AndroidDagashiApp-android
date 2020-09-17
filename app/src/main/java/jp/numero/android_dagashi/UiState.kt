package jp.numero.android_dagashi

import jp.numero.android_dagashi.model.Result

data class UiState<T>(
    val loading: Boolean = false,
    val exception: Exception? = null,
    val data: T? = null
)

fun <T> Result<T>.toState(): UiState<T> {
    return when (this) {
        is Result.Success -> {
            UiState(loading = false, data = value)
        }
        is Result.Failure -> {
            UiState(loading = false, exception = exception)
        }
    }
}