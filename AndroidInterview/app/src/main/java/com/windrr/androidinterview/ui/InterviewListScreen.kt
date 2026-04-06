package com.windrr.androidinterview.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.res.painterResource
import com.windrr.androidinterview.R
import androidx.compose.material3.*
import androidx.compose.material3.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.windrr.androidinterview.model.InterviewQuestion
import com.windrr.androidinterview.ui.theme.*
import com.windrr.androidinterview.viewmodel.InterviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewListScreen(
    partTitle: String = "면접 질문 복습",
    onBack: () -> Unit = {},
    viewModel: InterviewViewModel = viewModel()
) {
    val questions by viewModel.questions.collectAsStateWithLifecycle()
    val checkedIds by viewModel.checkedIds.collectAsStateWithLifecycle()
    var selectedQuestion by remember { mutableStateOf<InterviewQuestion?>(null) }
    var showResetDialog by remember { mutableStateOf(false) }

    val checkedCount = checkedIds.size
    val totalCount = questions.size
    val progress = if (totalCount > 0) checkedCount.toFloat() / totalCount else 0f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // ─── 헤더 ────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(SurfaceDark, DarkBg)
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 20.dp)
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Android Interview",
                                color = IndigoLight,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.5.sp
                            )
                            Text(
                                text = partTitle,
                                color = TextPrimary,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                        // 버튼 영역
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // 초기화 버튼
                            if (checkedCount > 0) {
                                IconButton(
                                    onClick = { showResetDialog = true },
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape)
                                        .background(CardDark)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = "초기화",
                                        tint = TextSecondary,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            // 뒤로가기 버튼
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(CardDark)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "뒤로가기",
                                    tint = TextSecondary,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    // 진행률 카드
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(CardDark)
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "학습 진행률",
                                    color = TextSecondary,
                                    fontSize = 13.sp
                                )
                                Text(
                                    text = "$checkedCount / $totalCount",
                                    color = TextPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(Modifier.height(10.dp))

                            // 진행률 바
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(CardElevated)
                            ) {
                                val animated by animateFloatAsState(
                                    targetValue = progress,
                                    animationSpec = tween(600),
                                    label = "progress"
                                )
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(animated)
                                        .height(6.dp)
                                        .clip(CircleShape)
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(Indigo700, Teal400)
                                            )
                                        )
                                )
                            }

                            if (checkedCount == totalCount && totalCount > 0) {
                                Spacer(Modifier.height(10.dp))
                                Text(
                                    text = "🎉 모든 항목 학습 완료!",
                                    color = Teal400,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }

            // ─── 질문 리스트 ─────────────────────────────────────────
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 24.dp + WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = questions,
                    key = { _, q -> q.id }
                ) { index, question ->
                    QuestionCard(
                        index = index + 1,
                        question = question,
                        isChecked = question.id in checkedIds,
                        onClick = { selectedQuestion = question }
                    )
                }
            }
        }

        // ─── 바텀시트 ─────────────────────────────────────────────
        selectedQuestion?.let { q ->
            AnswerBottomSheet(
                question = q,
                isChecked = q.id in checkedIds,
                onDismiss = { selectedQuestion = null },
                onMarkChecked = { viewModel.markAsChecked(q.id) }
            )
        }

        // ─── 초기화 확인 다이얼로그 ───────────────────────────────────
        if (showResetDialog) {
            AlertDialog(
                onDismissRequest = { showResetDialog = false },
                containerColor = SurfaceDark,
                titleContentColor = TextPrimary,
                textContentColor = TextSecondary,
                title = {
                    Text(
                        text = "학습 기록 초기화",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                },
                text = {
                    Text(
                        text = "${checkedCount}개의 학습 완료 기록이 모두 삭제됩니다.\n초기화하시겠습니까?",
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.resetProgress()
                            showResetDialog = false
                        }
                    ) {
                        Text(
                            text = "초기화",
                            color = Color(0xFFFF5252),
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showResetDialog = false }) {
                        Text(
                            text = "취소",
                            color = TextSecondary
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun QuestionCard(
    index: Int,
    question: InterviewQuestion,
    isChecked: Boolean,
    onClick: () -> Unit
) {
    val cardBg by animateColorAsState(
        targetValue = if (isChecked) Color(0xFF1A2B1E) else CardDark,
        animationSpec = tween(400),
        label = "cardBg"
    )
    val borderAlpha by animateFloatAsState(
        targetValue = if (isChecked) 1f else 0f,
        animationSpec = tween(400),
        label = "borderAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = if (isChecked) 0.dp else 4.dp,
                shape = RoundedCornerShape(18.dp),
                ambientColor = Indigo700.copy(alpha = 0.2f),
                spotColor = Indigo700.copy(alpha = 0.15f)
            )
            .clip(RoundedCornerShape(18.dp))
            .background(cardBg)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = Indigo500)
            ) { onClick() }
            .padding(horizontal = 18.dp, vertical = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 번호 배지
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (isChecked)
                            Green400.copy(alpha = 0.18f)
                        else
                            CardElevated
                    )
            ) {
                Text(
                    text = index.toString(),
                    color = if (isChecked) Green400 else TextSecondary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // 질문 텍스트
            Text(
                text = question.question,
                color = if (isChecked) TextSecondary else TextPrimary,
                fontSize = 14.sp,
                fontWeight = if (isChecked) FontWeight.Normal else FontWeight.Medium,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            // 체크 아이콘
            if (isChecked) {
                Icon(
                    imageVector = Icons.Filled.CheckCircle,
                    contentDescription = null,
                    tint = Green400,
                    modifier = Modifier.size(22.dp)
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_circle_outline),
                    contentDescription = null,
                    tint = TextHint,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
