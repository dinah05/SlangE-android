package com.slangmap.app.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.slangmap.app.R

@Composable
fun SplashScreen(
    onStartClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onStartClick()
            }
    ) {

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.slangmap_logo),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "슬랑이맵",
                fontSize = 32.sp
            )

            Text(
                text = "우리 동네 말랑이 찾기"
            )
        }

        Text(
            text = "화면을 터치해 시작하기",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        )
    }
}