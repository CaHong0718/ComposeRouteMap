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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.ui.customwidget.DividerWidget
import com.example.composeroutemap.ui.customwidget.rememberHapticClick
import com.example.composeroutemap.ui.theme.gray_300
import com.example.composeroutemap.ui.theme.gray_700
import com.example.composeroutemap.ui.theme.gray_900
import kotlin.reflect.KFunction1


/* ---------- Ui State & Dummy ----------- */

data class PlaceItem(
    val name: String,
    val address: String,
    val category: String = "",
    val distanceMeter: Int = 0
)

private val dummyPlaces = listOf(
    PlaceItem("스타벅스 판교아브뉴프랑점", "경기 성남시 분당구 삼평동 740", "카페 • 커피", 120),
    PlaceItem("이마트 성수점", "서울 성동구 뚝섬로 379", "대형마트", 310),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
    PlaceItem("도토리숲 어린이집", "서울 송파구 위례성대로 51", "유아교육", 420),
)


@Composable
fun PlaceSearchScreen(navController: NavController, viewModel: PlaceSearchViewModel = viewModel()) {
    val focusManager = LocalFocusManager.current

    val query by viewModel::query

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
                query = query,
                onQueryChange = viewModel::onQueryChange
            )
            Spacer(modifier = Modifier.height(Dimens.SmallPadding))
            DividerWidget()
            SearchedList(items = dummyPlaces, onUserInteract = { focusManager.clearFocus() })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    navController: NavController,
    query: String,
    onQueryChange: KFunction1<String, Unit>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BackButton(
                onClick = { onClickBackButton(navController) },
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
fun SearchedList(items: List<PlaceItem>, onUserInteract: () -> Unit) {
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
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(hideKeyboardOnDrag)
            .pointerInput(Unit) {
                detectTapGestures { onUserInteract() }
            }
    ) {
        items(items) { place ->
            PlaceListItem(place)
            DividerWidget()
        }
    }
}

@Composable
private fun PlaceListItem(place: PlaceItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.SmallPadding, horizontal = Dimens.LargePadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = place.name, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(Dimens.SmallSmallPadding))
            Text(
                text = place.address,
                style = MaterialTheme.typography.bodyMedium.copy(color = gray_700)
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
            if (place.distanceMeter > 0) {
                Spacer(Modifier.height(Dimens.SmallSmallPadding))
                Text("${place.distanceMeter}m", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

private fun onClickBackButton(navController: NavController) {
    navController.navigateUp()
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    PlaceSearchScreen(
        navController = rememberNavController()
    )
}