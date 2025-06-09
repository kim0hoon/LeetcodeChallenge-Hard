class Solution {
    fun maxJumps(arr: IntArray, d: Int): Int {
        val sorted = arr.mapIndexed { index, i -> i to index }.sortedByDescending { it.first }
        val dp = arr.map { 1 }.toMutableList()
        var maxVal = 0
        sorted.forEach {
            for (i in 1..d) {
                val idx = it.second - i
                if (idx < 0 || arr[idx] >= it.first) break
                dp[idx] = maxOf(dp[idx], dp[it.second] + 1)
            }
            maxVal = 0
            for (i in 1..d) {
                val idx = it.second + i
                if (idx > arr.lastIndex || arr[idx] >= it.first) break
                dp[idx] = maxOf(dp[idx], dp[it.second] + 1)
            }
        }
        return dp.maxOf { it }
    }
}