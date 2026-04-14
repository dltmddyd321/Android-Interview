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
        totalQuestions = 42,
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
    ),
    InterviewPart(
        partNumber = 3,
        title = "Part 3",
        subtitle = "Java 핵심",
        fileName = "interview_questions_part3.json",
        totalQuestions = 54,
        topics = listOf("OOP", "JVM", "Collection", "Thread", "Algorithm"),
        gradientStart = 0xFFFF6F00,
        gradientEnd = 0xFFFFCA28
    ),
    InterviewPart(
        partNumber = 4,
        title = "Part 4",
        subtitle = "Kotlin 기초",
        fileName = "interview_questions_part4.json",
        totalQuestions = 30,
        topics = listOf("val/var", "Extension", "Scope fn", "Sealed", "Generic"),
        gradientStart = 0xFF00897B,
        gradientEnd = 0xFF4CAF50
    ),
    InterviewPart(
        partNumber = 5,
        title = "Part 5",
        subtitle = "Coroutines",
        fileName = "interview_questions_part5.json",
        totalQuestions = 22,
        topics = listOf("Suspend", "Scope", "Dispatcher", "Channel", "Job"),
        gradientStart = 0xFFAD1457,
        gradientEnd = 0xFF7B1FA2
    ),
    InterviewPart(
        partNumber = 6,
        title = "Part 6",
        subtitle = "Kotlin Flow",
        fileName = "interview_questions_part6.json",
        totalQuestions = 16,
        topics = listOf("Cold/Hot", "StateFlow", "SharedFlow", "Operator", "Backpressure"),
        gradientStart = 0xFF0288D1,
        gradientEnd = 0xFF00BCD4
    ),
    InterviewPart(
        partNumber = 7,
        title = "Part 7",
        subtitle = "Jetpack Compose",
        fileName = "interview_questions_part7.json",
        totalQuestions = 19,
        topics = listOf("Composable", "Modifier", "State", "Recomposition", "SideEffect"),
        gradientStart = 0xFF558B2F,
        gradientEnd = 0xFF8BC34A
    ),
    InterviewPart(
        partNumber = 8,
        title = "Part 8",
        subtitle = "아키텍처 패턴",
        fileName = "interview_questions_part8.json",
        totalQuestions = 10,
        topics = listOf("MVVM", "MVI", "Clean Arch", "Repository", "UseCase"),
        gradientStart = 0xFFC62828,
        gradientEnd = 0xFFFF7043
    ),
    InterviewPart(
        partNumber = 9,
        title = "Part 9",
        subtitle = "네트워크",
        fileName = "interview_questions_part9.json",
        totalQuestions = 10,
        topics = listOf("TCP/UDP", "HTTP/HTTPS", "REST", "Socket", "SSL/TLS"),
        gradientStart = 0xFF006064,
        gradientEnd = 0xFF00ACC1
    ),
    InterviewPart(
        partNumber = 10,
        title = "Part 10",
        subtitle = "운영체제",
        fileName = "interview_questions_part10.json",
        totalQuestions = 16,
        topics = listOf("Process", "Thread", "Interrupt", "Memory", "Scheduling"),
        gradientStart = 0xFF4527A0,
        gradientEnd = 0xFF7B1FA2
    ),
    InterviewPart(
        partNumber = 11,
        title = "Part 11",
        subtitle = "데이터베이스",
        fileName = "interview_questions_part11.json",
        totalQuestions = 8,
        topics = listOf("ACID", "Index", "Transaction", "Foreign Key", "Room"),
        gradientStart = 0xFF1B5E20,
        gradientEnd = 0xFF43A047
    ),
    InterviewPart(
        partNumber = 12,
        title = "Part 12",
        subtitle = "심화",
        fileName = "interview_questions_part12.json",
        totalQuestions = 23,
        topics = listOf("Lifecycle", "Architecture", "Kotlin", "Performance", "Compose"),
        gradientStart = 0xFF0D47A1,
        gradientEnd = 0xFF1976D2
    )
)
