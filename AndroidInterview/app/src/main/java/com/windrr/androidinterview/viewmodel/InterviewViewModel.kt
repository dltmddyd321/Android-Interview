package com.windrr.androidinterview.viewmodel

import android.app.Application
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

    private val _questions = MutableStateFlow<List<InterviewQuestion>>(emptyList())
    val questions: StateFlow<List<InterviewQuestion>> = _questions.asStateFlow()

    private val _checkedIds = MutableStateFlow<Set<Int>>(emptySet())
    val checkedIds: StateFlow<Set<Int>> = _checkedIds.asStateFlow()

    init {
        loadQuestions(application)
    }

    private fun loadQuestions(application: Application) {
        val json = application.assets.open(fileName)
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<InterviewQuestion>>() {}.type
        _questions.value = Gson().fromJson(json, type)
    }

    fun markAsChecked(id: Int) {
        _checkedIds.value = _checkedIds.value + id
    }

    fun resetProgress() {
        _checkedIds.value = emptySet()
    }
}
