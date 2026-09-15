package com.mora.flexfont

object UnicodeMapper {

    val styles = listOf(
        FontStyle("Cursive", mapOf(
            'A'..'Z' to "𝓐𝓑𝓒𝓓𝓔𝓕𝓖𝓗𝓘𝓙𝓚𝓛𝓜𝓝𝓞𝓟𝓠𝓡𝓢𝓣𝓤𝓥𝓦𝓧𝓨𝓩",
            'a'..'z' to "𝓪𝓫𝓬𝓭𝓮𝓯𝓰𝓱𝓲𝓳𝓴𝓵𝓶𝓷𝓸𝓹𝓺𝓻𝓼𝓽𝓾𝓿𝔀𝔁𝔂𝔃",
            '0'..'9' to "𝟎𝟏𝟐𝟑𝟒𝟓𝟔𝟕𝟖𝟗"
        )),
        FontStyle("Gothic", mapOf(
            'A'..'Z' to "𝔄𝔅ℭ𝔇𝔈𝔉𝔊ℌℑ𝔍𝔎𝔏𝔐𝔑𝔒𝔓𝔔ℜ𝔖𝔗𝔘𝔙𝔚𝔛𝔜ℨ",
            'a'..'z' to "𝔞𝔟𝔠𝔡𝔢𝔣𝔤𝔥𝔦𝔧𝔨𝔩𝔪𝔫𝔬𝔭𝔮𝔯𝔰𝔱𝔲𝔳𝔴𝔵𝔶𝔷",
            '0'..'9' to "𝟘𝟙𝟚𝟛𝟜𝟝𝟞𝟟𝟠𝟡"
        )),
        FontStyle("Bold Serif", mapOf(
            'A'..'Z' to "𝐀𝐁𝐂𝐃𝐄𝐅𝐆𝐇𝐈𝐉𝐊𝐋𝐌𝐍𝐎𝐏Ｑ𝐑𝐒𝐓𝐔𝐕𝐖𝐗𝐘𝐙",
            'a'..'z' to "𝐚𝐛𝐜𝐝𝐞𝐟𝐠𝐡𝐢𝐣𝐤𝐥𝐦𝐧𝐨𝐩𝐪𝐫𝐬𝐭𝐮𝐯𝐰𝐱𝐲𝐳",
            '0'..'9' to "𝟎𝟏𝟐𝟑𝟒𝟓𝟔𝟕𝟖𝟗"
        )),
        FontStyle("Double Struck", mapOf(
            'A'..'Z' to "𝔸𝔹ℂ𝔻𝔼𝔽𝔾ℍ𝕀𝕁𝕂𝕃𝕄ℕ𝕆ℙℚℝ𝕊𝕋𝕌𝕍𝕎𝕏𝕐ℤ",
            'a'..'z' to "𝕒𝕓𝕔𝕕𝕖𝕗𝕘𝕙𝕚𝕛𝕜𝕝𝕞𝕟𝕠𝕡𝕢𝕣𝕤𝕥𝕦𝕧𝕨𝕩𝕪𝕫",
            '0'..'9' to "𝟘𝟙𝟚𝟛𝟜𝟝𝟞𝟟𝟠𝟡"
        )),
        FontStyle("Bubbles", mapOf(
            'A'..'Z' to "ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏ",
            'a'..'z' to "ⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ",
            '0'..'9' to "⓪①②③④⑤⑥⑦⑧⑨"
        )),
        FontStyle("Squared", mapOf(
            'A'..'Z' to "🄰🄱🄲🄳🄴🄵🄿🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉",
            'a'..'z' to "🄰🄱🄲🄳🄴🄵🄿🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉",
            '0'..'9' to "0123456789"
        )),
        FontStyle("Small Caps", mapOf(
            'A'..'Z' to "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ",
            'a'..'z' to "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ",
            '0'..'9' to "₀₁₂₃₄₅₆₇₈₉"
        )),
        FontStyle("Monospace", mapOf(
            'A'..'Z' to "𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉",
            'a'..'z' to "𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣",
            '0'..'9' to "𝟶𝟷𝟸𝟹𝟺𝟻𝟼𝟽𝟾𝟿"
        )),
        FontStyle("Upside Down", mapOf(
            'A'..'Z' to "∀𐐒ƆᗡƎℲ⅁HI𐌠ʞꞀWNOPΌᴚS┴∩ΛMX⅄Z",
            'a'..'z' to "ɐqɔpǝɟƃɥᴉɾʞlɯuodbɹsʇnʌʍxʎz",
            '0'..'9' to "0⇂ᘔƐㄣϛ9ㄥ86"
        ), reverse = true),
        FontStyle("Bracketed", mapOf(
            'A'..'Z' to "⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵",
            'a'..'z' to "⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵",
            '0'..'9' to "(0)(1)(2)(3)(4)(5)(6)(7)(8)(9)"
        )),
        FontStyle("Bold Italic", mapOf(
            'A'..'Z' to "𝑨𝑩𝑪𝑫𝑬𝑭𝑮𝑯𝑰𝑱𝑲𝑳𝑴𝑵𝑶𝑷𝑸𝑹𝑺𝑻𝑼𝑽𝑾𝑿𝒀𝒁",
            'a'..'z' to "𝒂𝒃𝒄𝒅𝒆𝒇𝒈𝒉𝒊𝒋𝒌𝒍𝒎𝒏𝒐𝒑𝒒𝒓𝒔𝒕𝒖𝒗𝒘𝒙𝒚𝒛",
            '0'..'9' to "𝟎𝟏𝟐𝟑𝟒𝟓𝟔𝟕𝟖𝟗"
        )),
        FontStyle("Sans Bold", mapOf(
            'A'..'Z' to "𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭",
            'a'..'z' to "𝗮𝗯𝗰𝗱𝒆𝗳𝗴𝗵𝗶𝗷𝗸𝒍𝒎𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇",
            '0'..'9' to "𝟬𝟭𝟮𝟯𝟰𝟱𝟲𝟳𝟴𝟵"
        ))
    )

    fun getAllStyles(input: String): List<FontItem> {
        return styles.map { style ->
            FontItem(
                styleName = style.name,
                transformedText = applyStyle(input, style)
            )
        }
    }

    fun mapChar(char: Char, styleName: String): String {
        val style = styles.find { it.name == styleName } ?: return char.toString()
        for ((range, replacement) in style.mappings) {
            if (char in range) {
                val index = char - range.first
                val codePointIndex = getCodePointIndex(replacement, index)
                if (codePointIndex != -1) {
                    return replacement.substring(codePointIndex, getNextCodePointIndex(replacement, codePointIndex))
                }
            }
        }
        return char.toString()
    }

    private fun applyStyle(input: String, style: FontStyle): String {
        if (input.isEmpty()) return ""
        val workingInput = if (style.reverse) input.reversed() else input
        val sb = StringBuilder()
        
        for (char in workingInput) {
            var mapped = false
            for ((range, replacement) in style.mappings) {
                if (char in range) {
                    val index = char - range.first
                    val codePointIndex = getCodePointIndex(replacement, index)
                    if (codePointIndex != -1) {
                        sb.append(replacement.substring(codePointIndex, getNextCodePointIndex(replacement, codePointIndex)))
                        mapped = true
                        break
                    }
                }
            }
            if (!mapped) sb.append(char)
        }
        return sb.toString()
    }

    private fun getCodePointIndex(s: String, logicalIndex: Int): Int {
        var currentLogical = 0
        var currentIndex = 0
        while (currentIndex < s.length) {
            if (currentLogical == logicalIndex) return currentIndex
            currentIndex += if (Character.isSurrogate(s[currentIndex])) 2 else 1
            currentLogical++
        }
        return -1
    }

    private fun getNextCodePointIndex(s: String, currentIndex: Int): Int {
        return currentIndex + if (Character.isSurrogate(s[currentIndex])) 2 else 1
    }
}

data class FontStyle(
    val name: String,
    val mappings: Map<CharRange, String>,
    val reverse: Boolean = false
)

data class FontItem(
    val styleName: String,
    val transformedText: String
)
