package com.tengyeekong.githubusers.ui.userdetails

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.tengyeekong.githubusers.domain.entity.User
import com.tengyeekong.githubusers.ui.R
import com.tengyeekong.githubusers.ui.common.theme.ComposeTheme
import com.tengyeekong.githubusers.ui.common.theme.colorShimmerDark
import com.tengyeekong.githubusers.ui.common.theme.colorShimmerLight
import com.tengyeekong.githubusers.ui.common.theme.shimmerDarkTheme
import com.tengyeekong.githubusers.ui.common.theme.shimmerTheme
import com.tengyeekong.githubusers.ui.common.utils.NetworkUtils
import com.tengyeekong.githubusers.ui.userlist.NetworkStatus
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch

@Composable
fun UserDetailsScreen(
    username: String,
    onBack: () -> Unit
) {
    val viewModel = hiltViewModel<UserDetailsViewModel>()
    val scaffoldState = rememberScaffoldState()

    var user = viewModel.getUserDetails(username).collectAsState(null)
    val snackbarHostState = remember { SnackbarHostState() }
    val networkStatus by NetworkUtils.getNetworkStateFlow().collectAsState()

    if (networkStatus == true) {
        user = viewModel.getUserDetails(username).collectAsState(null)
    }

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(snackbarHostState) {
                Snackbar(
                    backgroundColor = Color.DarkGray,
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.note_saved),
                        style = MaterialTheme.typography.subtitle1.copy(color = Color.White),
                    )
                }
            }
        },
        topBar = { AppBar(username, onBack) },
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    ) { contentPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            NetworkStatus(networkStatus)
            if (user.value?.name != null) {
                user.value?.let { user ->
                    UserDetailsContent(
                        viewModel,
                        contentPadding,
                        user,
                        username,
                        snackbarHostState,
                    )
                }
            } else ShimmerContent(contentPadding)
        }
    }

    BackHandler { onBack() }
}

@Composable
fun AppBar(
    username: String,
    onBack: () -> Unit
) {
    val appBarHorizontalPadding = 4.dp
    val titleIconModifier = Modifier
        .fillMaxHeight()
        .width(72.dp - appBarHorizontalPadding)

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        elevation = 10.dp
    ) {
        Box(Modifier.fillMaxSize()) {
            Row(titleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 50.dp),
            ) {
                Text(
                    text = username,
                    style = MaterialTheme.typography.h1,
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun ShimmerContent(contentPadding: PaddingValues) {
    val isLightMode = MaterialTheme.colors.secondary == Color.White
    val shimmerColor = if (isLightMode) colorShimmerLight else colorShimmerDark
    val shimmerTheme = if (isLightMode) shimmerTheme else shimmerDarkTheme

    CompositionLocalProvider(LocalShimmerTheme provides shimmerTheme) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 2f)
                    .shimmer()
                    .background(shimmerColor)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp, 20.dp)
                        .shimmer()
                        .background(shimmerColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp, 20.dp)
                        .shimmer()
                        .background(shimmerColor)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(horizontal = 12.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmer()
                    .background(shimmerColor)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 14.dp)
                    .size(50.dp, 20.dp)
                    .align(Alignment.Start)
                    .shimmer()
                    .background(shimmerColor)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .height(50.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmer()
                    .background(shimmerColor)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = Modifier
                    .size(50.dp, 30.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmer()
                    .background(shimmerColor)
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun UserDetailsContent(
    viewModel: UserDetailsViewModel,
    contentPadding: PaddingValues,
    user: User,
    username: String,
    snackbarHostState: SnackbarHostState,
) {
    val coroutineScope = rememberCoroutineScope()
    val imgRequestBuilder = ImageRequest.Builder(LocalContext.current)
        .crossfade(true)
        .diskCachePolicy(CachePolicy.ENABLED)

    var note by rememberSaveable { mutableStateOf(user.note ?: "") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                imgRequestBuilder.data(user.avatarUrl).build()
            ),
            contentDescription = stringResource(R.string.profile_picture),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(R.string.followers) + ": ${user.followers}",
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = stringResource(R.string.following) + ": ${user.following}",
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        Card(
            backgroundColor = MaterialTheme.colors.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            val details = """
                            ${stringResource(R.string.name)}: ${user.name ?: ""}
                            ${stringResource(R.string.company)}: ${user.company ?: ""}
                            ${stringResource(R.string.bio)}: ${user.bio ?: ""}
                            ${stringResource(R.string.email)}: ${user.email ?: ""}
                            ${stringResource(R.string.blog)}: ${user.blog ?: ""}
                            ${stringResource(R.string.html)}: ${user.htmlUrl ?: ""}
                        """.trimIndent()
            Text(
                text = details,
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.notes) + ":",
            style = MaterialTheme.typography.subtitle1,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 14.dp)
        )
        TextField(
            value = note,
            onValueChange = { value ->
                note = value
            },
            placeholder = {
                Text(
                    text = stringResource(R.string.notes_desc),
                    style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            shape = MaterialTheme.shapes.small,
            textStyle = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.typography.body1.color,
                backgroundColor = MaterialTheme.colors.surface,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Button(onClick = {
            viewModel.updateUserNote(username, note)
            coroutineScope.launch {
                snackbarHostState.showSnackbar("")
            }
        }) {
            Text(
                text = stringResource(R.string.save),
                style = MaterialTheme.typography.button.copy(
                    color = Color.White,
                ),
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun UserDetailsScreenPreview() {
    val enableDarkMode = remember { mutableStateOf(false) }
    ComposeTheme(enableDarkMode) {
        UserDetailsScreen("") {}
    }
}