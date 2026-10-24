# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Folio is a local-first Android notes app whose AI features (summaries, key points, tasks, suggested titles/notes) run entirely on-device via Gemini Nano (ML Kit GenAI) — no cloud LLMs. The app must degrade gracefully on devices that can't run the model. The product plan (features, tech stack, DB shape) lives in `documents/plan/app_info.md`.

The project is at an early scaffold stage: only the Room database skeleton and the AI contract exist; there is no UI, repository, ViewModel, or DI module yet.

## Build & test

The Gradle project root is `FolioApp/`, not the repo root. Run all Gradle commands from there:

```bash
cd FolioApp
./gradlew assembleDebug                 # build debug APK
./gradlew installDebug                  # install on connected device/emulator
./gradlew testDebugUnitTest             # JVM unit tests
./gradlew testDebugUnitTest --tests "com.folio.notesapp.SomeTest.someMethod"   # single test
./gradlew connectedDebugAndroidTest     # instrumented / Compose UI tests (needs device)
```

Note: `gradlew`, `gradlew.bat`, and `FolioApp/gradle/` (including the wrapper) are gitignored, so a fresh clone has no wrapper — regenerate it with `gradle wrapper` if missing.

## Build configuration gotchas

- **AGP 9 with built-in Kotlin**: there is no `org.jetbrains.kotlin.android` plugin applied; Kotlin compilation comes from AGP itself. Don't add the Kotlin Android plugin.
- `android.disallowKotlinSourceSets=false` in `gradle.properties` is required because KSP (used for Room and Hilt) still registers generated sources via `kotlin.sourceSets`, which AGP 9 rejects by default. Don't remove it.
- Dependencies are declared inline in `app/build.gradle.kts` with explicit versions (no version catalog). Plugin versions live in the root `FolioApp/build.gradle.kts`.
- compileSdk/targetSdk 37, minSdk 29, JVM toolchain 17.

## Architecture

Package root: `com.folio.notesapp`. Intended stack: Jetpack Compose UI, MVVM, Hilt DI, Room + DataStore, WorkManager, Coil.

- `core/database/` — Room. `FolioAppDatabase` (version 1) registers entities and exposes DAOs. The `Notes` entity stores `folder` and `tags` as plain strings and timestamps as strings (`created_time`, `modified_time` columns). Any schema change requires bumping the DB version and adding a migration.
- `core/ai/AIContract.kt` — the provider-agnostic AI abstraction. Feature code should depend on `AiProvider`, never on ML Kit directly:
  - `AiProvider` declares its `capabilities` (`AiCapability` set) so callers can hide features a provider doesn't support.
  - `availability()` / `prepare()` model the on-device model lifecycle (`Available`, `Downloadable`, `Downloading`, `Unavailable`) — `prepare()` triggers the model download and emits progress.
  - Generation calls return `AiResult<T>` (`Success` / `Failure(AiError)`) rather than throwing; `generateTitle` streams via `Flow<String>`.
  - This is the seam for the "graceful fallback" requirement: a Gemini Nano implementation backed by `com.google.mlkit:genai-summarization` / `genai-prompt`, plus a fallback/no-op provider for unsupported devices.
- `FolioApplication` is the `@HiltAndroidApp`; `MainActivity` is the `@AndroidEntryPoint`.
