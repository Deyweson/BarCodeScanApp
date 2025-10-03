package com.example.barcodescanapp.ui.screens

import androidx.lifecycle.ViewModel
import com.example.barcodescanapp.data.ItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()


    fun AddNewItem(code: String){
        val newItem = ItemModel(
            id = code,
            code = code,
            quant = 0
        )
        _uiState.value.items.add(newItem)
        _uiState.value = _uiState.value
    }



    fun generateBarcode(content: String, width: Int = 600, height: Int = 250): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(content, BarcodeFormat.CODE_128, width, height)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) Color.BLACK else Color.TRANSPARENT
                )
            }
        }

        return bitmap
    }

}