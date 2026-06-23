package com.slangmap.app.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp

@Composable
fun CategoryChipRow() {

    val categories = listOf(
        "전체",
        "말랑이",
        "슬랑이",
        "슬라임",
        "퍼티"
    )

    var selected by remember {
        mutableStateOf("전체")
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(categories) { category ->

            FilterChip(
                selected = selected == category,
                onClick = {
                    selected = category
                },
                label = {
                    Text(category)
                }
            )
        }
    }
}