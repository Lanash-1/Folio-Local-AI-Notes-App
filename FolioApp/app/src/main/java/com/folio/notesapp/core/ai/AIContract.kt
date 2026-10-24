package com.folio.notesapp.core.ai

import kotlinx.coroutines.flow.Flow

enum class AiCapability { GENERATE_SUMMARY, GENERATE_TITLE, GENERATE_TASKS, SUGGESTION }

sealed interface AiAvailability {
    data object Available : AiAvailability
    data object Downloadable : AiAvailability
    data object Downloading : AiAvailability
    data class Unavailable(val reason: String? = null) : AiAvailability
}

sealed interface AiResult<out T> {
    data class Success<T>(val value: T) : AiResult<T>
    data class Failure(val error: AiError) : AiResult<Nothing>
}

sealed interface AiError {
    data object NotAvailable : AiError
    data object Unsupported : AiError // capability not offered
    data object InputTooLong : AiError
    data object QuotaExceeded : AiError
    data class Unknown(val cause: Throwable?) : AiError
}

interface AiProvider {
    val id: String
    val capabilities: Set<AiCapability>

    suspend fun availability(): AiAvailability
    fun prepare(): Flow<AiAvailability>        // triggers model download, emits progress states

    suspend fun generateSummary(prompt: String): AiResult<String>
    fun generateTitle(prompt: String): Flow<String>
    suspend fun generateTask(prompt: String): AiResult<String>
    suspend fun suggestion(prompt: String): AiResult<String>
}