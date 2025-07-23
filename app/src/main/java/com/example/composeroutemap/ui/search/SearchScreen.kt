package com.example.composeroutemap.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.FontSize
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.ui.customwidget.NextButton
import com.example.composeroutemap.ui.customwidget.ScrollColumnWithEdgeLine
import com.example.composeroutemap.ui.customwidget.rememberHapticClick
import com.example.composeroutemap.ui.theme.gray_500
import com.example.composeroutemap.ui.theme.gray_700
import com.example.composeroutemap.ui.theme.gray_800
import kotlin.random.Random

/** 데이터 모델 (로직 연결 전까지는 화면 전용 더미) */
data class PlaceUiModel(
    val id: Long = Random.nextLong(),
    val name: String,
    val address: String
)

/** ----------------------------------------------------------------------------------*/


@Composable
fun SearchScreen(navController: NavController) {
    val places = remember { mutableStateListOf<PlaceUiModel>() }
    places.add(PlaceUiModel(name = "첫번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "두번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "세번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "네번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))
    places.add(PlaceUiModel(name = "5번째 장소", address = "여기는 주소가 1999.000"))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        MainContent(places)
    }
}

@Composable
fun MainContent(places: SnapshotStateList<PlaceUiModel>) {
    Column {
        TitleView("최적의 경로를 알려드릴게요!")
        SubTitleView("장소를 추가해 주세요.")
        Spacer(Modifier.height(Dimens.LargePadding))
        ScrollColumnWithEdgeLine(
            modifier = Modifier.weight(Weights.Fill),
            contentPadding = PaddingValues(horizontal = Dimens.NormalPadding)
        ){
            PlaceContent(places)
        }
        NextButton(
            onClick = rememberHapticClick({ onClickNextButton() }),
            modifier = Modifier.padding(
                start = Dimens.NormalPadding,
                end = Dimens.NormalPadding,
                top = Dimens.NormalPadding,
                bottom = Dimens.LargePadding
            )
        )
    }
}

@Composable
fun TitleView(text: String) {
    Text(
        modifier = Modifier
            .padding(
                top = Dimens.LargePadding,
                start = Dimens.LargePadding,
                end = Dimens.LargePadding,
            ),
        text = text,
        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
        color = Color.Black,
    )
}

@Composable
fun SubTitleView(text: String) {
    Text(
        modifier = Modifier
            .padding(
                top = Dimens.SmallPadding,
                start = Dimens.LargePadding
            ),
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = FontSize.SemiSmallFontSize),
        color = gray_800,
    )
}

@Composable
fun PlaceContent(places: SnapshotStateList<PlaceUiModel>) {
    AddedPlaceListView(places, modifier = Modifier.padding(horizontal = Dimens.NormalPadding))
    NextButton(
        onClick = rememberHapticClick({onClickAddButton()}),
        modifier = Modifier.padding(
            horizontal = Dimens.NormalPadding,
            vertical = Dimens.LargePadding
        ),
        text = "추가하기"
    )
}

@Composable
fun AddedPlaceListView(places: SnapshotStateList<PlaceUiModel>, modifier: Modifier = Modifier) {
    if (places.isEmpty()) {
        Text(
            "추가한 장소가 없습니다...",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = modifier.padding(start = Dimens.NormalPadding),
            color = gray_500
        )
    } else {
        /*LazyColumn(
            modifier = modifier,
            //verticalArrangement = Arrangement.spacedBy(Dimens.SmallSmallPadding),
            contentPadding = PaddingValues(vertical = Dimens.SmallPadding)
        ) {
            items(places) { place ->
                PlaceItem(place = place, onDelete = { places.remove(place) })
            }
        }*/

        places.forEach { place ->
            PlaceItem(
                place = place,
                onDelete = rememberHapticClick({ places.remove(place) })
            )
        }
    }
}

@Composable
private fun PlaceItem(place: PlaceUiModel, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.NormalPadding, vertical = Dimens.SmallPadding)
    ) {
        Column(modifier = Modifier.weight(Weights.Fill)) {
            Text(place.name, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(Dimens.SmallSmallPadding))
            Text(
                place.address,
                style = MaterialTheme.typography.bodyMedium,
                color = gray_700
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Place")
        }
    }
}

private fun onClickAddButton(){

}

private fun onClickNextButton(){

}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        navController = rememberNavController()
    )
}