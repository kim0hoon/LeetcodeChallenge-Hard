class Solution {
    fun boxDelivering(boxes: Array<IntArray>, portsCount: Int, maxBoxes: Int, maxWeight: Int): Int {
        val dp = MutableList(boxes.size + 1) { Int.MAX_VALUE }
        var acc = 0L
        val weightAcc = boxes.map {
            acc += it[1]
            acc
        }
        var groupNum = 0
        val groupMap = mutableMapOf<Int, Pair<Int, Int>>()
        val groups = boxes.mapIndexed { index, ints ->
            if (index > 0 && ints[0] != boxes[index - 1][0]) groupNum++
            groupMap[groupNum] = groupMap.getOrPut(groupNum) { index to index }.run {
                first to index
            }
            groupNum
        }
        dp[0] = 0
        boxes.forEachIndexed { index, ints ->
            if (dp[index] == Int.MAX_VALUE) return@forEachIndexed
            var s = index
            var e = minOf(index + maxBoxes - 1, boxes.lastIndex)
            var r = e
            while (s <= e) {
                val m = (s + e) / 2
                val w = weightAcc.run { get(m) - getOrElse(index - 1) { 0L } }
                if (w <= maxWeight) {
                    r = m
                    s = m + 1
                } else e = m - 1
            }
            dp[r + 1] = minOf(dp[r+1],dp[index] + groups.subList(index, r + 1).toSet().size + 1)
            if (groups[r] == groups.getOrNull(r + 1) && groups[r] != groups[index]) {
                groupMap[groups[r]]?.run {
                    if (maxWeight >= weightAcc.run { get(first) - get(second) }) {
                        dp[first] =minOf(dp[first], dp[index] + groups.subList(index, first).toSet().size + 1)
                    }
                }
            }
        }
        return dp.last()
    }
}