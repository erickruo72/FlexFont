package com.mora.flexfont

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner

class FontifyKeyboardService : InputMethodService(), LifecycleOwner, ViewModelStoreOwner, SavedStateRegistryOwner {

    private var selectedStyle by mutableStateOf("Cursive")
    private var isShifted by mutableStateOf(false)
    private var keyboardMode by mutableStateOf(KeyboardMode.LETTERS)
    private var selectedEmojiCategory by mutableStateOf(0)

    enum class KeyboardMode { LETTERS, NUMBERS, SYMBOLS, EMOJI }
    
    private val _lifecycleRegistry = LifecycleRegistry(this)
    private val _viewModelStore = ViewModelStore()
    private val _savedStateRegistryController = SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle get() = _lifecycleRegistry
    override val viewModelStore: ViewModelStore get() = _viewModelStore
    override val savedStateRegistry: SavedStateRegistry get() = _savedStateRegistryController.savedStateRegistry

    override fun onCreate() {
        super.onCreate()
        _savedStateRegistryController.performRestore(null)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }

    override fun onCreateInputView(): View {
        window?.window?.decorView?.let { decorView ->
            decorView.setViewTreeLifecycleOwner(this)
            decorView.setViewTreeViewModelStoreOwner(this)
            decorView.setViewTreeSavedStateRegistryOwner(this)
        }

        val composeView = ComposeView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        }
        
        composeView.setContent {
            MaterialTheme {
                KeyboardLayout(
                    onKeyClick = { char ->
                        val transformed = UnicodeMapper.mapChar(char, selectedStyle)
                        currentInputConnection?.commitText(transformed, 1)
                        if (isShifted) isShifted = false
                    },
                    onEmojiClick = { emoji ->
                        currentInputConnection?.commitText(emoji, 1)
                    },
                    onDelete = {
                        val connection = currentInputConnection
                        if (connection != null) {
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                                connection.deleteSurroundingTextInCodePoints(1, 0)
                            } else {
                                connection.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_DEL))
                                connection.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_DEL))
                            }
                        }
                    },
                    onSpace = {
                        currentInputConnection?.commitText(" ", 1)
                    },
                    onEnter = {
                        currentInputConnection?.sendKeyEvent(
                            android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)
                        )
                    },
                    isShifted = isShifted,
                    onShiftToggle = { isShifted = !isShifted },
                    keyboardMode = keyboardMode,
                    onModeChange = { keyboardMode = it },
                    selectedStyle = selectedStyle,
                    onStyleChange = { selectedStyle = it },
                    selectedEmojiCategory = selectedEmojiCategory,
                    onEmojiCategoryChange = { selectedEmojiCategory = it }
                )
            }
        }
        return composeView
    }

    override fun onStartInputView(info: android.view.inputmethod.EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        _lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

@Composable
fun KeyboardLayout(
    onKeyClick: (Char) -> Unit,
    onEmojiClick: (String) -> Unit,
    onDelete: () -> Unit,
    onSpace: () -> Unit,
    onEnter: () -> Unit,
    isShifted: Boolean,
    onShiftToggle: () -> Unit,
    keyboardMode: FontifyKeyboardService.KeyboardMode,
    onModeChange: (FontifyKeyboardService.KeyboardMode) -> Unit,
    selectedStyle: String,
    onStyleChange: (String) -> Unit,
    selectedEmojiCategory: Int,
    onEmojiCategoryChange: (Int) -> Unit
) {
    val emojiCategories = remember {
        listOf(
            EmojiCategory("😀", listOf(
                "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "😊", "😇", "🙂", "🙃", "😉", "😌", "😍", "🥰", "😘", "😗", "😙", "😚",
                "😋", "😛", "😝", "😜", "🤪", "🤨", "🧐", "🤓", "😎", "🤩", "🥳", "😏", "😒", "😞", "😔", "😟", "😕", "🙁", "☹️", "😣",
                "😖", "😫", "😩", "🥺", "😢", "😭", "😤", "😠", "😡", "🤬", "🤯", "😳", "🥵", "🥶", "😱", "😨", "😰", "😥", "😓", "🤗",
                "🤔", "🤭", "🤫", "🤥", "😶", "😐", "😑", "😬", "🙄", "😯", "😦", "😧", "😮", "😲", "🥱", "😴", "🤤", "😪", "😵", "🤐",
                "🥴", "🤢", "🤮", "🤧", "😷", "🤒", "🤕", "🤑", "🤠", "😈", "👿", "👹", "👺", "🤡", "💩", "👻", "💀", "☠️", "👽", "👾"
            )),
            EmojiCategory("👋", listOf(
                "👋", "🤚", "🖐️", "✋", "🖖", "👌", "🤏", "✌️", "🤞", "🤟", "🤘", "🤙", "👈", "👉", "👆", "🖕", "👇", "☝️", "👍", "👎",
                "✊", "👊", "🤛", "🤜", "👏", "🙌", "👐", "🤲", "🤝", "🙏", "✍️", "💅", "🤳", "💪", "🦾", "🦿", "🦵", "🦶", "👂", "🦻",
                "👃", "🧠", "🫀", "🫁", "🦷", "🦴", "👀", "👁️", "👅", "👄", "💋", "🩸"
            )),
            EmojiCategory("🐱", listOf(
                "🙈", "🙉", "🙊", "💥", "💫", "💦", "💨", "🐵", "🐒", "🦍", "🦧", "🐶", "🐕", "🦮", "🐕‍🦺", "🐩", "🐺", "🦊", "🦝", "🐱",
                "猫", "🦁", "🐯", "🐅", "🐆", "🐴", "🐎", "🦄", "🦓", "🦌", "🦬", "🐮", "🐂", "🐃", "🐄", "🐷", "🐖", "🐗", "🐽", "🐏",
                "🐑", "🐐", "🐪", "🐫", "🦙", "🦒", "🐘", "🦣", "🦏", "🦛", "🐭", "🐁", "🐀", "🐹", "🐰", "🐇", "🐿️", "🦫", "🦔", "🦇"
            )),
            EmojiCategory("🍏", listOf(
                "🍏", "🍎", "🍐", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🫐", " melon", "🍒", "🍑", "🥭", "🍍", "🥥", "🥝", "🍅", "🍆", "🥑",
                "🥦", "🥬", "🥒", "🌶️", "🫑", "🌽", "🥕", "🫒", "🧄", "🧅", "🥔", "🍠", "🥐", "🥯", "🍞", "🥖", "🥨", "🧀", "🥞", "🧇",
                "🥓", "🥩", "🍗", "🍖", "🌭", "🍔", "🍟", "🍕", "🫓", "🥪", "🌮", "🌯", "🫔", "🥙", "🧆", "🥚", "🍳", "🥘", "🍲", "🥣"
            )),
            EmojiCategory("⚽", listOf(
                "⚽", "🏀", "🏈", "⚾", "🥎", "🎾", "🏐", "🏉", "🥏", "🎱", "🪀", "🏓", "🏸", "🏒", "🏑", "🥍", "🏏", "🪃", "🥅", "⛳",
                "🪁", "🏹", "🎣", "🤿", "🥊", "🥋", "🎽", "🛹", "🛼", "🛷", "⛸️", "🥌", "🎿", "⛷️", "🏂", "🪂", "🏋️", "🤼", "🤸", "⛹️"
            )),
            EmojiCategory("🚗", listOf(
                "🚗", "🚕", "🚙", "🚌", "🚎", "🏎️", "🚓", "🚑", "🚒", "🚐", "🛻", "🚚", "🚛", "🚜", "🦯", "🦽", "🦼", "🛴", "🚲", "🛵",
                "🏍️", "🛺", "🚨", "🚔", "🚍", "🚘", "🚖", "🚡", "🚠", "🚟", "🚃", "🚋", "🚞", "🦝", "✈️", "🛫", "🛬", "🪂", "🚁", "🛶"
            )),
            EmojiCategory("💡", listOf(
                "⌚", "📱", "📲", "💻", "⌨️", "🖥️", "🖨️", "🖱️", "🖲️", "🕹️", "🗜️", "💽", "💾", "💿", "DVD", "📼", "📷", "📸", "📹", "🎥",
                "📽️", "🎞️", "📞", "📟", "📠", "📺", "📻", "🎙️", "🎚️", "🎛️", "🧭", "⏱️", "⏲️", "⏰", "⏳", "⏳", "⏳", "💡", "🔦", "🏮"
            )),
            EmojiCategory("❤️", listOf(
                "💘", "💝", "💖", "💗", "💓", "💞", "💕", "💟", "❣️", "💔", "❤️", "🧡", "💛", "💚", "💙", "💜", "🖤", "🤍", "🤎", "💯",
                "💢", "💬", "👁️‍🗨️", "🗯️", "💭", "💤", "🌐", "♠️", "♥️", "♦️", "♣️", "🃏", "🀄", "🎴", "🎭", "🖼️", "🎨", "🧵", "🪡", "🧶"
            ))
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1C1B1F))
            .padding(vertical = 8.dp)
    ) {
        // Style Selector
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(UnicodeMapper.styles.map { it.name }) { styleName ->
                val isSelected = styleName == selectedStyle
                val sampleText = UnicodeMapper.mapChar('A', styleName) + UnicodeMapper.mapChar('b', styleName)
                
                Surface(
                    onClick = { onStyleChange(styleName) },
                    shape = RoundedCornerShape(16.dp),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFF322F37),
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.White
                ) {
                    Text(
                        text = "$styleName ($sampleText)",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Box(modifier = Modifier.height(210.dp)) {
            when (keyboardMode) {
                FontifyKeyboardService.KeyboardMode.LETTERS -> {
                    Column {
                        val rows = listOf(
                            "QWERTYUIOP".toCharArray(),
                            "ASDFGHJKL".toCharArray(),
                            "ZXCVBNM".toCharArray()
                        )
                        rows.forEachIndexed { index, row ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (index == 2) {
                                    KeyButton(
                                        text = if (isShifted) "⬆" else "⇧",
                                        onClick = onShiftToggle,
                                        modifier = Modifier.weight(1.5f),
                                        color = if (isShifted) MaterialTheme.colorScheme.primary else Color(0xFF49454F)
                                    )
                                }
                                row.forEach { char ->
                                    val displayChar = if (isShifted) char.uppercaseChar() else char.lowercaseChar()
                                    KeyButton(
                                        text = UnicodeMapper.mapChar(displayChar, selectedStyle),
                                        onClick = { onKeyClick(displayChar) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (index == 2) {
                                    KeyButton(text = "⌫", onClick = onDelete, modifier = Modifier.weight(1.5f), color = Color(0xFF49454F))
                                }
                            }
                        }
                    }
                }
                FontifyKeyboardService.KeyboardMode.NUMBERS -> {
                    Column {
                        val rows = listOf(
                            "1234567890".toCharArray(),
                            "-/:;()$&@\"".toCharArray(),
                            ".,?!'".toCharArray()
                        )
                        rows.forEachIndexed { index, row ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (index == 2) {
                                    KeyButton(text = "#+=", onClick = { onModeChange(FontifyKeyboardService.KeyboardMode.SYMBOLS) }, modifier = Modifier.weight(1.5f), color = Color(0xFF49454F))
                                }
                                row.forEach { char ->
                                    KeyButton(text = char.toString(), onClick = { onKeyClick(char) }, modifier = Modifier.weight(1f))
                                }
                                if (index == 2) {
                                    KeyButton(text = "⌫", onClick = onDelete, modifier = Modifier.weight(1.5f), color = Color(0xFF49454F))
                                }
                            }
                        }
                    }
                }
                FontifyKeyboardService.KeyboardMode.SYMBOLS -> {
                    Column {
                        val rows = listOf(
                            "[]{}#%^*+=".toCharArray(),
                            "_\\|~<>€£¥•".toCharArray(),
                            ".,?!'".toCharArray()
                        )
                        rows.forEachIndexed { index, row ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                if (index == 2) {
                                    KeyButton(text = "123", onClick = { onModeChange(FontifyKeyboardService.KeyboardMode.NUMBERS) }, modifier = Modifier.weight(1.5f), color = Color(0xFF49454F))
                                }
                                row.forEach { char ->
                                    KeyButton(text = char.toString(), onClick = { onKeyClick(char) }, modifier = Modifier.weight(1f))
                                }
                                if (index == 2) {
                                    KeyButton(text = "⌫", onClick = onDelete, modifier = Modifier.weight(1.5f), color = Color(0xFF49454F))
                                }
                            }
                        }
                    }
                }
                FontifyKeyboardService.KeyboardMode.EMOJI -> {
                    Column {
                        // Emoji Category Selector Row
                        LazyRow(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items(emojiCategories.size) { index ->
                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 6.dp)
                                        .size(36.dp)
                                        .background(
                                            if (selectedEmojiCategory == index) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .clickable { onEmojiCategoryChange(index) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = emojiCategories[index].icon, fontSize = 20.sp)
                                }
                            }
                        }
                        
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(7),
                            modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(emojiCategories[selectedEmojiCategory].emojis) { emoji ->
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clickable { onEmojiClick(emoji) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = emoji, fontSize = 24.sp)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Row
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val modeText = if (keyboardMode == FontifyKeyboardService.KeyboardMode.LETTERS) "123" else "ABC"
            val nextMode = if (keyboardMode == FontifyKeyboardService.KeyboardMode.LETTERS) FontifyKeyboardService.KeyboardMode.NUMBERS else FontifyKeyboardService.KeyboardMode.LETTERS
            
            KeyButton(text = modeText, onClick = { onModeChange(nextMode) }, modifier = Modifier.weight(1.2f), color = Color(0xFF49454F))
            
            KeyButton(
                text = "😀", 
                onClick = { 
                    if (keyboardMode == FontifyKeyboardService.KeyboardMode.EMOJI) {
                        onModeChange(FontifyKeyboardService.KeyboardMode.LETTERS)
                    } else {
                        onModeChange(FontifyKeyboardService.KeyboardMode.EMOJI)
                    }
                }, 
                modifier = Modifier.weight(1f), 
                color = if (keyboardMode == FontifyKeyboardService.KeyboardMode.EMOJI) MaterialTheme.colorScheme.primary else Color(0xFF49454F)
            )

            KeyButton(text = " ", onClick = onSpace, modifier = Modifier.weight(3.5f))
            
            if (keyboardMode == FontifyKeyboardService.KeyboardMode.EMOJI) {
                KeyButton(text = "⌫", onClick = onDelete, modifier = Modifier.weight(1.2f), color = Color(0xFF49454F))
            }
            
            KeyButton(text = "Enter", onClick = onEnter, modifier = Modifier.weight(1.8f), color = MaterialTheme.colorScheme.primary)
        }
    }
}

data class EmojiCategory(val icon: String, val emojis: List<String>)

@Composable
fun KeyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF322F37)
) {
    Surface(
        onClick = onClick,
        modifier = modifier.padding(2.dp).height(48.dp),
        shape = RoundedCornerShape(4.dp),
        color = color,
        contentColor = Color.White
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}
