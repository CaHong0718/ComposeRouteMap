package com.example.composeroutemap.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.Place
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.ui.customwidget.DividerWidget
import com.example.composeroutemap.ui.customwidget.rememberHapticClick
import com.example.composeroutemap.ui.theme.gray_300
import com.example.composeroutemap.ui.theme.gray_700
import com.example.composeroutemap.ui.theme.gray_900
import com.example.composeroutemap.utils.formatMeter
import kotlin.reflect.KFunction1


@Composable
fun PlaceSearchScreen(
    navController: NavController,
    viewModel: PlaceSearchViewModel = viewModel(),
    onPlaceSelected: (Place) -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .pointerInput(Unit) {
                detectTapGestures { focusManager.clearFocus() }
            }
    ) {
        Column {
            Spacer(modifier = Modifier.height(Dimens.SmallPadding))

            SearchBar(
                navController = navController,
                query = uiState.query,
                onQueryChange = viewModel::onQueryChange,
                focusManager = focusManager
            )

            Spacer(modifier = Modifier.height(Dimens.SmallPadding))

            DividerWidget()

            SearchedList(
                items = uiState.items,
                isLoading = uiState.isLoading,
                error = uiState.error,
                onClickItem = { place ->
                    onPlaceSelected(place)
                    focusManager.clearFocus()
                    navController.navigateUp()
                },
                onUserInteract = { focusManager.clearFocus() },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    navController: NavController,
    query: String,
    onQueryChange: KFunction1<String, Unit>,
    focusManager: FocusManager
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackButton(
                onClick = { onClickBackButton(navController, focusManager) },
                modifier = Modifier.padding(start = Dimens.SmallPadding)
            )

            Spacer(Modifier.width(Dimens.SmallPadding))

            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier
                    .weight(Weights.Fill)
                    .padding(end = Dimens.NormalPadding),
                placeholder = { Text("장소를 입력하세요") },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedTextColor = gray_900,
                    unfocusedTextColor = gray_300,
                    cursorColor = gray_900,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = gray_300
                ),
            )
        }
    }
}

@Composable
fun BackButton(onClick: () -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .clickable(
                onClick = rememberHapticClick(onClick),
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .height(Dimens.LargeIconSize)
            .width(Dimens.LargeIconSize),
        contentAlignment = Alignment.Center,

        ) {
        Icon(
            Icons.Default.KeyboardArrowLeft,
            contentDescription = "BackButton",
            modifier = Modifier
                .height(Dimens.NormalIconSize)
                .width(Dimens.NormalIconSize)
        )
    }
}

@Composable
fun SearchedList(
    items: List<Place>,
    onUserInteract: () -> Unit,
    isLoading: Boolean,
    error: String?,
    onClickItem: (Place) -> Unit
) {
    val hideKeyboardOnDrag = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                if (source == NestedScrollSource.Drag) {
                    onUserInteract()
                }
                return Offset.Zero
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(hideKeyboardOnDrag)
            .pointerInput(Unit) { detectTapGestures { onUserInteract() } }
    ) {
        when {
            isLoading -> {
                /* 진행 중 – 센터 로딩 */
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            error != null -> {
                /* 실패 메시지 */
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(Dimens.NormalPadding)
                )
            }

            items.isEmpty() -> {
                /* 결과 없음 */
                Text(
                    text = "검색 결과가 없습니다",
                    style = MaterialTheme.typography.bodyMedium,
                    color = gray_700,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                /* 리스트 표시 */
                LazyColumn {
                    items(items) { place ->
                        PlaceListItem(place = place) { onClickItem(place) }
                        DividerWidget()
                    }
                }
            }
        }
    }
}

@Composable
private fun PlaceListItem(place: Place, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = rememberHapticClick(onClick = onClick),
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = Dimens.SmallPadding, horizontal = Dimens.LargePadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(Weights.Fill),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = place.name, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(Dimens.SmallSmallPadding))
            Text(
                text = place.roadAddress,
                style = MaterialTheme.typography.bodySmall.copy(color = gray_700)
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = place.category,
                style = MaterialTheme.typography.bodySmall,
                color = gray_700
            )

            Spacer(Modifier.height(Dimens.SmallSmallPadding))


            Text(
                text = place.distanceMeter.formatMeter(),
                style = MaterialTheme.typography.bodySmall
            )

        }
    }
}

private fun onClickBackButton(navController: NavController, focusManager: FocusManager) {
    focusManager.clearFocus()
    navController.navigateUp()
}
