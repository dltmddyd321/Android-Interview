package com.windrr.androidinterview.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.ripple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.FlowRow
import com.windrr.androidinterview.model.InterviewPart
import com.windrr.androidinterview.model.interviewParts
import com.windrr.androidinterview.ui.theme.*

@Composable
fun PartSelectionScreen(
    onPartSelected: (InterviewPart) -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        // 배경 장식 원
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-80).dp, y = (-60).dp)
                .background(
                    Brush.radialGradient(
                        listOf(Indigo700.copy(alpha = 0.18f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )
        Box(
            modifier = Modifier
                .size(250.dp)
                .align(Alignment.BottomEnd)
                .offset(x = 60.dp, y = 60.dp)
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF7C4DFF).copy(alpha = 0.14f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(56.dp))

            // 헤더
            Text(
                text = "Android Interview",
                color = IndigoLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 2.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "파트를 선택하세요",
                color = TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "원하는 주제를 골라 면접 준비를 시작하세요",
                color = TextSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(48.dp))

            // 파트 카드들
            interviewParts.forEach { part ->
                PartCard(
                    part = part,
                    onClick = { onPartSelected(part) }
                )
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(32.dp))

            // 하단 힌트
            Text(
                text = "총 ${interviewParts.sumOf { it.totalQuestions }}개의 면접 질문",
                color = TextHint,
                fontSize = 13.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(14.dp))

            Text(
                text = "틀린 문제나 추가 되었으면 하는 문제를 요청해주세요!",
                color = IndigoLight,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple()
                    ) {
                        val emailIntent = Intent(
                            Intent.ACTION_SENDTO,
                            Uri.parse("mailto:dltmddyd321@naver.com")
                        ).apply {
                            putExtra(Intent.EXTRA_SUBJECT, "AndroidInterview 문제 요청")
                            putExtra(Intent.EXTRA_TEXT, "추가/수정 요청 내용:\n\n")
                        }

                        try {
                            context.startActivity(emailIntent)
                        } catch (_: ActivityNotFoundException) {
                        }
                    }
                    .padding(horizontal = 14.dp, vertical = 10.dp)
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PartCard(
    part: InterviewPart,
    onClick: () -> Unit
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.97f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "cardScale"
    )

    val gradStart = Color(part.gradientStart)
    val gradEnd = Color(part.gradientEnd)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(24.dp))
            .background(CardDark)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = gradStart)
            ) {
                pressed = true
                onClick()
            }
    ) {
        // 좌측 컬러 사이드바
        Box(
            modifier = Modifier
                .width(6.dp)
                .fillMaxHeight()
                .align(Alignment.CenterStart)
                .background(
                    Brush.verticalGradient(listOf(gradStart, gradEnd))
                )
        )

        // 우상단 장식 원
        Box(
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = (-40).dp)
                .background(
                    Brush.radialGradient(listOf(gradStart.copy(alpha = 0.12f), Color.Transparent)),
                    shape = CircleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp, end = 20.dp, top = 24.dp, bottom = 24.dp)
        ) {
            // 파트 번호 배지
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.horizontalGradient(listOf(gradStart, gradEnd))
                    )
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                Text(
                    text = part.title,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }

            Spacer(Modifier.height(14.dp))

            Text(
                text = part.subtitle,
                color = TextPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "${part.totalQuestions}개 질문",
                color = TextSecondary,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(16.dp))

            // 토픽 태그들 — FlowRow로 자연스럽게 줄바꿈
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                part.topics.forEach { topic ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(gradStart.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = topic,
                            color = gradStart,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // 시작 버튼
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.horizontalGradient(listOf(gradStart, gradEnd))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "학습 시작  →",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
