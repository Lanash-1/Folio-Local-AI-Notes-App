# Folio - On-Device AI powered :Notes App.
---

## Table of contents
1. About App
2. Features
3. Tech Stack
4. DB Structure
5. Flow Diagram

### 1. About App

Folio Notes App - with local/On-device AI on supported devices to assist in generating summaries, smart tags to organize, etc.

### 2. Features

- Notes - Title & Content
- Organise Notes - Tags & Folders
- Search Notes
- Light / Dark Theme
- Import / Export Notes

AI Features
- Generate Summary / KeyPoints / Tasks from Note
- Suggested Notes and Title

### 3. Tech Stack

Language - Kotlin
UI - Jetpack Compose
Architecture - MVVM
DI - Hilt
Storage - Room (SQLite), DataStore
Background Work - WorkManager
Tests - JUnit (Unit test), Turbine (Flow test), Compose UI tests
On-device AI - Gemini Nano
Images - Coil
Audio - MediaRecorder

### 4. DB Structure

Note - id, title, content, folder, tags

### 5. Flow Diagram

https://claude.ai/design/p/17a7c6fd-04ac-45a6-bf07-ffd422765215?file=Folio+Prototype.dc.html
