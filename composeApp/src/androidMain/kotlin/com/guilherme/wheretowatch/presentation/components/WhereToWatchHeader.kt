package com.guilherme.wheretowatch.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.guilherme.wheretowatch.R

@Composable
fun WhereToWatchHeader() {
    Row {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = stringResource(R.string.where_to_watch),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.Bottom),
            text = stringResource(R.string.info_provided_by_justwatch),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            textAlign = TextAlign.End
        )
    }
}