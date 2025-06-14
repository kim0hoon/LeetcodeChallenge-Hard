class Solution {
    fun waysToReachStair(k: Int): Int {
        var jump = 0
        var answer = 0
        val comb = List(32) { MutableList(it + 1) { 1 } }
        for (i in 1..31) {
            comb[i][0] = 1
            comb[i][i] = 1
            for (j in 1 until i) {
                comb[i][j] = comb[i - 1][j] + comb[i - 1][j - 1]
            }
        }
        while (true) {
            val max = (1 shl jump)
            val back = max - k
            if (back >= 0 && back <= jump + 1) {
                answer += comb[jump + 1][back]
            } else if (back > jump + 1) break
            jump++
        }
        return answer
    }
}