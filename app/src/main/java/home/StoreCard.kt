package com.slangmap.app.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.slangmap.app.home.model.StoreUiModel

@Composable
fun StoreCard(
    store: StoreUiModel
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(store.category)

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(store.name)

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(store.distance)
        }
    }
}