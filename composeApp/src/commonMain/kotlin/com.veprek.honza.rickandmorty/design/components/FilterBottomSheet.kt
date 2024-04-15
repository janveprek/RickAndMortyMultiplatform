package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.veprek.honza.rickandmorty.character.model.StatusFilter
import com.veprek.honza.rickandmorty.design.theme.paddingExtraSmall
import com.veprek.honza.rickandmorty.design.theme.paddingMedium
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import rickandmorty.composeapp.generated.resources.Res


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    open: Boolean,
    sheetState: SheetState = rememberModalBottomSheetState(),
    selected: StatusFilter,
    onSubmitClick: (StatusFilter) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    if (open) {
        ModalBottomSheet(
            modifier = modifier,
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
        ) {
            Contents(
                modifier = modifier.navigationBarsPadding(),
                selected = selected,
                onSubmitClick = onSubmitClick,
                onDismissRequest = onDismissRequest,
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Contents(
    modifier: Modifier = Modifier,
    selected: StatusFilter,
    onSubmitClick: (StatusFilter) -> Unit,
    onDismissRequest: () -> Unit = {},
) {
    val radioOptions = listOf(StatusFilter.All, StatusFilter.Alive, StatusFilter.Dead, StatusFilter.Unknown)
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(selected ) }
    Column(
        modifier = modifier.navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.status),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = paddingMedium)
        )
        radioOptions.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = paddingMedium,
                        vertical = paddingExtraSmall,
                    ).clickable { onOptionSelected(option) },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = { onOptionSelected(option) }
                )
                Text(
                    text = stringResource(option.nameResource),
                    modifier = Modifier.padding(start = paddingMedium)
                )
            }
        }
        Button(
            onClick = {
                onSubmitClick(selectedOption)
                onDismissRequest()
            }
        ) {
            Text(stringResource(Res.string.submit))
        }
    }
}