class Solution {
    fun numOfWays(nums: IntArray): Int {
        val mod = 1_000_000_000 + 7
        val child = MutableList<Pair<Int?, Int?>>(nums.size + 1) { null to null }
        val subTreeSize = MutableList(nums.size + 1) { 0 }
        val dp = MutableList<Long?>(nums.size + 1) { null }
        val comb = List(nums.size + 1) { MutableList(it + 1) { 1 } }
        var root = nums[0]
        for (i in 1..nums.size) {
            comb[i][0] = 1
            comb[i][i] = 1
            for (j in 1 until i) {
                comb[i][j] = (comb[i - 1][j] + comb[i - 1][j - 1]) % mod
            }
        }
        for (i in 1..nums.lastIndex) {
            var now = root
            while (true) {
                if (now < nums[i]) {
                    val right = child[now].second
                    if (right == null) {
                        child[now] = child[now].run { first to nums[i] }
                        break
                    } else {
                        now = right
                    }
                } else {
                    val left = child[now].first
                    if (left == null) {
                        child[now] = child[now].run { nums[i] to second }
                        break
                    } else {
                        now = left
                    }
                }
            }
        }
        val stack = Stack<Int>()
        stack.push(root)
        while (stack.isNotEmpty()) {
            val top = stack.peek()
            val isVisited = child[top].run {
                first?.let { dp[it] } != null || second?.let { dp[it] } != null
            }
            val isLeaf = child[top].run { first == null && second == null }
            if (isVisited || isLeaf) {
                stack.pop()
                val left = child[top].first
                val right = child[top].second
                val leftDp = left?.let { dp[it] } ?: 1L
                val rightDp = right?.let { dp[it] } ?: 1L
                val leftSubTreeSz = left?.let { subTreeSize[it] } ?: 0
                val rightSubTreeSz = right?.let { subTreeSize[it] } ?: 0
                dp[top] = (((leftDp * rightDp) % mod) * comb[leftSubTreeSz + rightSubTreeSz][leftSubTreeSz]) % mod
                subTreeSize[top] = leftSubTreeSz + rightSubTreeSz + 1
            } else {
                child[top].run {
                    first?.let { stack.push(it) }
                    second?.let { stack.push(it) }
                }
            }
        }
        return (dp[root]?.toInt() ?: 1) - 1 
    }
}