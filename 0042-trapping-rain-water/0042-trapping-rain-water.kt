class Solution {
    fun trap(height: IntArray): Int {
        val sorted = height.mapIndexed { index, i -> i to index }.sortedByDescending { it.first }
        var ans = 0
        var leftIdx = sorted.first().second
        var rightIdx = sorted.first().second
        for (i in 1..sorted.lastIndex) {
            val target = sorted[i]
            if (target.second < leftIdx) {
                ans += target.first * (leftIdx - target.second - 1)
                leftIdx = target.second
            } else if (target.second > rightIdx) {
                ans += target.first * (target.second - rightIdx - 1)
                rightIdx = target.second
            } else ans -= target.first
        }
        return ans
    }
}