package com.slangmap.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.invisibleToUser
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slangmap.app.R

/**
 * 로그인 화면.
 *
 * 의도적으로 스크롤을 막고 weight 기반 Spacer로 화면 크기에 비례하게 배치
 * 로그인 버튼과 약관 안내는 화면 어떤 크기/폰트 스케일에서도 잘리지 않도록
 * 하단 영역에 최소 weight를 보장하도록 함.
 */
@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onSearchNearbyClick: () -> Unit,
    onReviewClick: () -> Unit,
    onReportClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.12f))

        Image(
            painter = painterResource(id = R.drawable.slangmap_logo),
            contentDescription = stringResource(R.string.login_logo_content_description),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.weight(0.12f))

        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(0.08f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onSearchNearbyClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.login_action_search_nearby))
            }

            OutlinedButton(
                onClick = onReviewClick,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.login_action_review))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onReportClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.login_action_report))
        }

        Spacer(modifier = Modifier.weight(0.18f, fill = true).heightIn(min = 24.dp))

        Button(
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.login_google_cta))
        }

        Spacer(modifier = Modifier.weight(0.1f, fill = true).heightIn(min = 16.dp))

        Text(
            text = stringResource(R.string.login_terms_notice),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.login_terms_of_service),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable(onClick = onTermsClick)
                    .semantics { role = Role.Button }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "|",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.semantics { invisibleToUser() }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.login_privacy_policy),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .clickable(onClick = onPrivacyPolicyClick)
                    .semantics { role = Role.Button }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, fontScale = 1.5f, name = "Large font scale")
@Preview(showBackground = true, device = "spec:width=360dp,height=480dp", name = "Small screen")
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        onLoginClick = {},
        onSearchNearbyClick = {},
        onReviewClick = {},
        onReportClick = {},
        onTermsClick = {},
        onPrivacyPolicyClick = {}
    )
}