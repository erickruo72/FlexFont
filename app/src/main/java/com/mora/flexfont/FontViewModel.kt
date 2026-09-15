package com.mora.flexfont

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class FontViewModel : ViewModel() {

    val inputText = MutableStateFlow("")
    val selectedCategory = MutableStateFlow("All")

    val categories = listOf("All", "Popular", "Gamer Tags", "Decorations")

    val fontList: StateFlow<List<FontItem>> = combine(inputText, selectedCategory) { text, category ->
        val fullList = UnicodeMapper.getAllStyles(text)
        filterByCategory(fullList, category)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onInputChanged(newText: String) {
        inputText.value = newText
    }

    fun onCategorySelected(category: String) {
        selectedCategory.value = category
    }

    fun clearText() {
        inputText.value = ""
    }

    private fun filterByCategory(list: List<FontItem>, category: String): List<FontItem> {
        return when (category) {
            "Popular" -> list.take(5)
            "Gamer Tags" -> list.filter { it.styleName in listOf("Gothic", "Bold Serif", "Monospace") }
            "Decorations" -> list.filter { it.styleName in listOf("Bubbles", "Squared", "Bracketed") }
            else -> list
        }
    }
}
