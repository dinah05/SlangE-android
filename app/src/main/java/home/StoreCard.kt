package com.slangmap.app.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.slangmap.app.presentation.home.model.StoreUiModel

@Composable
fun StoreCard(
    store: StoreUiModel,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text("📷")
            }

            Column {

                AssistChip(
                    onClick = {},
                    label = {
                        Text(store.category)
                    }
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = store.name
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "현위치에서 약 ${store.distance}"
                )
            }
        }
    }
}