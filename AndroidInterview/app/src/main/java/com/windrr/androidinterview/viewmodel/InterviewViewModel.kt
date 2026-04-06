package com.windrr.androidinterview.viewmodel

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.windrr.androidinterview.model.InterviewQuestion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InterviewViewModel(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    // Navigation 인자에서 파일명 수신
    private val fileName: String =
        savedStateHandle.get<String>("fileName") ?: "interview_questions.json"

    // SharedPreferences 키: 파트별로 독립 저장
    private val prefsName = "interview_progress"
    private val prefsKey = "checked_ids_$fileName"
    private val prefs = application.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    private val _questions = MutableStateFlow<List<InterviewQuestion>>(emptyList())
    val questions: StateFlow<List<InterviewQuestion>> = _questions.asStateFlow()

    private val _checkedIds = MutableStateFlow<Set<Int>>(emptySet())
    val checkedIds: StateFlow<Set<Int>> = _checkedIds.asStateFlow()

    init {
        loadQuestions(application)
        loadProgress()
    }

    private fun loadQuestions(application: Application) {
        val json = application.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<InterviewQuestion>>() {}.type
        _questions.value = Gson().fromJson(json, type)
    }

    /** SharedPreferences에서 저장된 체크 ID 목록 불러오기 */
    private fun loadProgress() {
        val saved = prefs.getStringSet(prefsKey, emptySet()) ?: emptySet()
        _checkedIds.value = saved.mapNotNull { it.toIntOrNull() }.toSet()
    }

    /** 체크 상태를 SharedPreferences에 저장 */
    private fun saveProgress(ids: Set<Int>) {
        prefs.edit {
            putStringSet(prefsKey, ids.map { it.toString() }.toSet())
        }
    }

    fun markAsChecked(id: Int) {
        val updated = _checkedIds.value + id
        _checkedIds.value = updated
        saveProgress(updated)
    }

    fun resetProgress() {
        _checkedIds.value = emptySet()
        prefs.edit { remove(prefsKey) }
    }
}

