class Solution {
    val Char.bit get() = 1 shl (this.code - 'a'.code)
    fun findNumOfValidWords(words: Array<String>, puzzles: Array<String>): List<Int> {
        val wordBits = words.map {
            it.fold(0) { acc, c ->
                acc or c.bit
            }
        }
        val wordBitsMap = mutableMapOf<Int, Int>().apply {
            wordBits.forEach {
                this[it] = this.getOrDefault(it, 0) + 1
            }
        }
        return puzzles.map {
            var ans = 0
            for (i in 0 until (1 shl (it.lastIndex))) {
                var bit = it.first().bit
                var testS = it.first().toString()
                for (j in 0 until it.lastIndex) {
                    if (i and (1 shl j) == 0) continue
                    bit = bit or (it[j + 1].bit)
                    testS += it[j+1]
                }
                ans += wordBitsMap[bit] ?: 0
            }
            ans
        }
    }
}