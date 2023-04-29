package com.cruning.presentation.screens.list

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.cruning.domain.model.ImageUrl
import com.cruning.presentation.R
import retrofit2.HttpException
import java.io.IOException


@Composable
fun ListScreen(
    click: (painter: String) -> Unit,
) {
    val viewModel = hiltViewModel<ListViewModel>()
    val urls by viewModel.urls.collectAsStateWithLifecycle()

    ListCardImage(
        click = click,
        data = urls.collectAsLazyPagingItems()
    )
}

@Composable
fun ListCardImage(
    click: (painter: String) -> Unit,
    data: LazyPagingItems<ImageUrl>,
) {
    val loadState = data.loadState.refresh
    if (loadState is LoadState.Error) {
        when ((loadState).error) {
            is HttpException -> {
                Text(
                    text = stringResource(R.string.error_server, loadState.error.message ?: ""),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is IOException -> {
                Text(
                    text = stringResource(R.string.error_network),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(data.itemCount) {
                CardImage(click = click, url = data[it]?.url)
            }

            when (data.loadState.append) {
                is LoadState.Error -> {
                    item {
                        Text(
                            text = (data.loadState.append as LoadState.Error).error.message?.let {
                                stringResource(R.string.error_pagination, it)
                            } ?: stringResource(R.string.error_unknown)
                        )
                    }
                }

                is LoadState.Loading -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(text = stringResource(R.string.loading_content))
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun CardImage(
    click: (painter: String) -> Unit,
    url: String?,
) {
    SubcomposeAsyncImage(
        model = url,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        loading = {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp,
                modifier = Modifier
                    .padding(PaddingValues(64.dp))
            )
        },
        modifier = Modifier
            .height(200.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16))
            .clip(RoundedCornerShape(16))
            .fillMaxWidth()
            .clickable {
                click(url ?: "")
            }
    )
}