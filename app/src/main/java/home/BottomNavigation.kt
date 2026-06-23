package com.slangmap.app.presentation.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BottomNavigationBar() {

    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {

        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Text("🗺️")
            },
            label = {
                Text("지도")
            }
        )

        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = {
                Text("👤")
            },
            label = {
                Text("내정보")
            }
        )
    }
}