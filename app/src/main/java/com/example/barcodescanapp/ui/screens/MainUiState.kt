package com.example.barcodescanapp.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.barcodescanapp.data.ItemModel

data class MainUiState (
    val items: SnapshotStateList<ItemModel> = mutableStateListOf()
)