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
import androidx.compose.material.icons.filled.Tune
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
    query: String = "",
    onSearch: (String) -> Unit = {},
    onFilterClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    var queryState by remember { mutableStateOf(query) }
    var active by remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = paddingSmall),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchBar(
            modifier = modifier.padding(bottom = paddingSmall).fillMaxWidth().weight(1f, false),
            placeholder = { Text(text = stringResource(Res.string.search_hint)) },
            active = active,
            query = queryState,
            onQueryChange = {
                queryState = it
                onQueryChange(it.trim())
            },
            onSearch = {
                onSearch(it)
                active = false
            },
            leadingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable { active = false },
                        imageVector = Icons.Default.ArrowBackIosNew, contentDescription = null
                    )
                } else {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                }
            },
            trailingIcon = {
                if (queryState.isNotEmpty()) {
                    Icon(
                        modifier = Modifier.clickable {
                            queryState = ""
                            onSearch(queryState)
                        },
                        imageVector = Icons.Default.Close, contentDescription = null
                    )
                }
            },
            content = content,
            onActiveChange = {
                active = it
            },
        )
        IconButton(
            onClick = onFilterClick,
        ) {
            Icon(imageVector = Icons.Default.Tune, contentDescription = null)
        }
    }
}
