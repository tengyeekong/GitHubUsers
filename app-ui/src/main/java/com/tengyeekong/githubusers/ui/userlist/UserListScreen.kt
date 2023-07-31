package com.tengyeekong.githubusers.ui.userlist

import android.graphics.drawable.ColorDrawable
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.tengyeekong.githubusers.domain.entity.User
import com.tengyeekong.githubusers.ui.MainActivity
import com.tengyeekong.githubusers.ui.R
import com.tengyeekong.githubusers.ui.common.theme.ComposeTheme
import com.tengyeekong.githubusers.ui.common.theme.colorPrimary
import com.tengyeekong.githubusers.ui.common.theme.colorShimmerDark
import com.tengyeekong.githubusers.ui.common.theme.colorShimmerLight
import com.tengyeekong.githubusers.ui.common.theme.shimmerDarkTheme
import com.tengyeekong.githubusers.ui.common.theme.shimmerTheme
import com.tengyeekong.githubusers.ui.common.utils.NetworkUtils
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UserListScreen(
    enableDarkMode: MutableState<Boolean>,
    onTapUser: (username: String) -> Unit
) {
    val viewModel = hiltViewModel<UsersViewModel>()
    val activity = LocalContext.current as MainActivity
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val lazyListState = rememberLazyListState()

    val users = viewModel.usersPagingFlow.collectAsLazyPagingItems()
    val query = viewModel.queryFlow.collectAsState()
    val networkStatus by NetworkUtils.getNetworkStateFlow().collectAsState()

    if (networkStatus == true) {
        users.retry()
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                viewModel,
                query,
                lazyListState,
                enableDarkMode,
            )
        },
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    ) { contentPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            NetworkStatus(networkStatus)
            if (users.itemCount == 0 && query.value.isEmpty()) {
                ShimmerListItems(enableDarkMode, contentPadding)
            } else {
                UserList(
                    lazyListState,
                    contentPadding,
                    users,
                    onTapUser,
                    enableDarkMode
                )
            }
        }
    }

    // Kill app on back action
    BackHandler {
        if (lazyListState.firstVisibleItemIndex > 1) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(0)
            }
        } else activity.finish()
    }
}

@Composable
fun TopBar(
    viewModel: UsersViewModel,
    query: State<String>,
    lazyListState: LazyListState,
    enableDarkMode: MutableState<Boolean>,
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchView(
                modifier = Modifier.weight(1f),
                viewModel = viewModel,
                lazyListState = lazyListState,
                query = query
            )
            IconButton(
                onClick = {
                    enableDarkMode.value = !enableDarkMode.value
                }
            ) {
                Icon(
                    Icons.Default.DarkMode,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel,
    lazyListState: LazyListState,
    query: State<String>,
) {
    val coroutineScope = rememberCoroutineScope()

    TextField(
        value = query.value,
        onValueChange = { value ->
            viewModel.updateQuery(value)
            coroutineScope.launch {
                delay(350)
                withContext(Dispatchers.Main) {
                    lazyListState.scrollToItem(0)
                }
            }
        },
        modifier = modifier,
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (query.value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        // Remove query from TextField when the 'X' icon is pressed
                        viewModel.updateQuery("")
                        coroutineScope.launch {
                            delay(350)
                            withContext(Dispatchers.Main) {
                                lazyListState.scrollToItem(0)
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = colorPrimary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun NetworkStatus(networkStatus: Boolean?) {
    AnimatedVisibility(
        visible = networkStatus == false,
        modifier = Modifier
            .fillMaxWidth()
            .background(if (networkStatus == false) Color.Red else Color.Green)
    ) {
        Text(
            text = stringResource(if (networkStatus == false) R.string.no_connection else R.string.back_online),
            style = MaterialTheme.typography.subtitle1.copy(color = Color.White),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp)
        )
    }
}

@Composable
fun ShimmerListItems(
    enableDarkMode: MutableState<Boolean>,
    contentPadding: PaddingValues,
) {
    val shimmerColor = if (!enableDarkMode.value) colorShimmerLight else colorShimmerDark
    val shimmerTheme = if (!enableDarkMode.value) shimmerTheme else shimmerDarkTheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        CompositionLocalProvider(LocalShimmerTheme provides shimmerTheme) {
            repeat(10) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(95.dp)
                        .padding(horizontal = 8.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .shimmer()
                        .background(shimmerColor)
                )
            }
        }
    }
}

@Composable
fun UserList(
    lazyListState: LazyListState,
    contentPadding: PaddingValues,
    users: LazyPagingItems<User>,
    onTapUser: (username: String) -> Unit,
    enableDarkMode: MutableState<Boolean>
) {
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = spacedBy(8.dp, Alignment.Top),
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize()
    ) {
        items(users.itemCount, key = users.itemKey { it.id }) { index ->
            val user = users[index] ?: return@items
            UserCard(
                user,
                index,
                onTapUser,
                enableDarkMode
            )
        }

        if (users.loadState.append is LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    color = colorPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserCard(
    user: User,
    index: Int,
    onTapUser: (username: String) -> Unit,
    enableDarkMode: MutableState<Boolean>,
) {
    val imgRequestBuilder = ImageRequest.Builder(LocalContext.current)
        .crossfade(true)
        .placeholder(ColorDrawable(Color.LightGray.toArgb()))
        .diskCachePolicy(CachePolicy.ENABLED)
    // Color matrix to invert the color
    val colorMatrix = floatArrayOf(
        -1f, 0f, 0f, 0f, 255f,
        0f, -1f, 0f, 0f, 255f,
        0f, 0f, -1f, 0f, 255f,
        0f, 0f, 0f, 1f, 0f
    )
    val invertColorFilter = ColorFilter.colorMatrix(ColorMatrix(colorMatrix))

    Card(
        onClick = {
            val username = user.login
            if (!username.isNullOrBlank()) {
                onTapUser(username)
            }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    imgRequestBuilder.data(user.avatarUrl).build()
                ),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.Crop,
                colorFilter = if ((index + 1) % 4 == 0) invertColorFilter else null,
                modifier = Modifier
                    .height(72.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 20.dp, end = 8.dp)
                    .weight(1f)
            ) {
                Text(
                    text = user.login ?: stringResource(R.string.user),
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = user.htmlUrl ?: stringResource(R.string.details),
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                painter = painterResource(R.drawable.ic_note),
                contentDescription = stringResource(R.string.notes),
                colorFilter = ColorFilter.tint(color = if (enableDarkMode.value) Color.White else Color.DarkGray),
                modifier = Modifier
                    .height(24.dp)
                    .padding(end = 8.dp)
                    .alpha(if (!user.note.isNullOrBlank()) 1f else 0f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserListScreenPreview() {
    val enableDarkMode = remember { mutableStateOf(false) }
    ComposeTheme(enableDarkMode) {
        UserListScreen(enableDarkMode) {}
    }
}