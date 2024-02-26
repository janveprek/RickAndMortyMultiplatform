package com.veprek.honza.rickandmorty.design.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.veprek.honza.rickandmorty.design.theme.paddingSmall
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import rickandmorty.composeapp.generated.resources.Res

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    goBack: (() -> Unit)? = null,
    actions: (
        @Composable()
        (
        RowScope.() -> Unit
    ))? = null,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = title)
            }
        },
        navigationIcon = {
            goBack?.let {
                IconButton(onClick = goBack) {
                    Icon(
                        painter = rememberVectorPainter(Icons.Default.ArrowBackIosNew),
                        contentDescription = null,
                    )
                }
            }
        },
        actions = {
            actions?.let {
                actions()
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    SearchBar(
        modifier = modifier.fillMaxWidth().padding(horizontal = paddingSmall),
        placeholder = { Text(text = stringResource(Res.string.search_hint)) },
        active = active,
        query = query,
        onQueryChange = {
            query = it
            onQueryChange(it.trim())
        },
        onSearch = {
            onSearch(it)
            active = false
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    modifier =
                        Modifier.clickable {
                            query = ""
                            onSearch(query)
                        },
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                )
            }
        },
        content = content,
        onActiveChange = {
            active = it
        },
    )
}
