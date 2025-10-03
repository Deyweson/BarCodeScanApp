package com.example.barcodescanapp.ui.screens

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.barcodescanapp.CaptureActivityPortrait
import com.example.barcodescanapp.ui.topbar.TopBarYou
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.example.barcodescanapp.R
import com.example.barcodescanapp.data.ItemModel
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import com.simonsickle.compose.barcodes.Barcode
import com.simonsickle.compose.barcodes.BarcodeType
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel()
) {
    val uiState = mainViewModel.uiState.collectAsState()

    var resultScan = remember { mutableStateOf("") }

    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = {result ->
            resultScan.value = result.contents ?: ""
            if(resultScan.value != ""){
                mainViewModel.AddNewItem(resultScan.value)
            }
        }
    )


     fun Scan(){
        val scanOption = ScanOptions()
        scanOption.setBeepEnabled(true)
        scanOption.setPrompt(
            "Escaneie o código de barras."
        )
        scanOption.setCaptureActivity(
            CaptureActivityPortrait::class.java
        )
        scanOption.setOrientationLocked(false)
        scanLauncher.launch(scanOption)
    }


    Scaffold(
        topBar = { TopBarYou() },
        floatingActionButton = {
            ElevatedCard{
                IconButton(onClick = {
                    Scan()
                }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier
                            .padding(12.dp)
                    )
                }
            }
        }
    ) { innerPadding ->

        LazyColumn(contentPadding = innerPadding) {
            items(uiState.value.items)
            { it ->
                ItemCard(
                    item = it,
                    modifier = modifier,
                    bitmap = mainViewModel.generateBarcode(it.code)
                )
            }
        }
    }
}

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    item: ItemModel,
    bitmap: Bitmap
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(
            bottomEnd = 4.dp,
            bottomStart = 4.dp,
            topEnd = 4.dp,
            topStart = 4.dp
        )

    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(item.code)
                BarcodeView(item.code, bitmap)
                Text(item.code)
            }
            Spacer(Modifier.weight(1f))
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {}) {
                    Text("+")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = item.quant.toString())
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = {}) {
                    Text("-")
                }
            }
        }
    }
}

@Composable
fun BarcodeView(content: String, bitmap: Bitmap) {
    Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Barcode")
}



@Composable
fun BarcodeDisplay(
    barcodeValue: String,
) {
    // Ensure the value is valid for the selected barcode type
    if (BarcodeType.QR_CODE.isValueValid(barcodeValue)) {
        Barcode(
            modifier = Modifier
                .width(100.dp)
                .height(100.dp), // Adjust size as needed
            resolutionFactor = 10, // Optional: Increase resolution for better quality
            type = BarcodeType.EAN_13, // Choose your barcode type (e.g., QR_CODE, CODE_128)
            value = barcodeValue // The data to encode in the barcode
        )
    } else {
        // Handle invalid barcode data (e.g., display an error message)
        // The library might show an infinite spinner if data is invalid
    }
}





