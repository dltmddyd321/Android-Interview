package com.windrr.androidinterview

import android.os.Build
import androidx.annotation.RequiresApi
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun first(participant: List<String>, completion: List<String>) {

        min(participant.distinct().count(), participant.count() /2 )

        val part = participant.toSet()
        val common = part.intersect(completion.toSet())
        common.forEach {
            println(it)
        }
    }

    fun solution(clothes: Array<Array<String>>): Int {
        return clothes
            .groupingBy { it[1] } // 1. 옷의 종류(인덱스 1)를 Key로 묶음
            .eachCount()          // 2. 종류별 개수를 셈 -> Map<String, Int> 반환 (예: {headgear=2, eyewear=1})
            .values               // 3. 개수(Value)들만 컬렉션으로 추출 -> [2, 1]
            .fold(1) { acc, count ->
                acc * (count + 1) // 4. 초기값 1에 각 (개수 + 1)을 누적해서 곱함
            } - 1                 // 5. 아무것도 입지 않은 경우 1가지 제외
    }
}