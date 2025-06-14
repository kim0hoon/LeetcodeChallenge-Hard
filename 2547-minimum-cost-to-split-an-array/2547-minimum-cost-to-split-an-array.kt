class Solution {
    fun minCost(nums: IntArray, k: Int): Int {
        val dp = MutableList(nums.size) { Long.MAX_VALUE }
        val firstSet = mutableSetOf<Int>()
        val secondSet = mutableSetOf<Int>()
        for (i in nums.indices) {
            for (j in i..nums.lastIndex) {
                val target = nums[j]
                if (firstSet.contains(target)) {
                    firstSet.remove(target)
                    secondSet.add(target)
                } else if (secondSet.contains(target).not()) firstSet.add(target)
                dp[j] = minOf(dp[j], (k + dp.getOrElse(i - 1) { 0 } + j - i + 1 - firstSet.size))
            }
            firstSet.clear()
            secondSet.clear()
        }
        return dp[nums.lastIndex].toInt()
    }
}