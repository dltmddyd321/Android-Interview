package com.windrr.androidinterview.model

data class InterviewPart(
    val partNumber: Int,
    val title: String,
    val subtitle: String,
    val fileName: String,
    val totalQuestions: Int,
    val topics: List<String>,
    val gradientStart: Long,
    val gradientEnd: Long
)

val interviewParts = listOf(
    InterviewPart(
        partNumber = 1,
        title = "Part 1",
        subtitle = "안드로이드 기초",
        fileName = "interview_questions.json",
        totalQuestions = 43,
        topics = listOf("Activity", "Fragment", "Service", "Intent", "Layout"),
        gradientStart = 0xFF3D5AFE,
        gradientEnd = 0xFF00BCD4
    ),
    InterviewPart(
        partNumber = 2,
        title = "Part 2",
        subtitle = "심화 & 아키텍처",
        fileName = "interview_questions_part2.json",
        totalQuestions = 77,
        topics = listOf("AAC", "Coroutine", "Flow", "Compose", "Architecture"),
        gradientStart = 0xFF7C4DFF,
        gradientEnd = 0xFFE91E63
    )
)
