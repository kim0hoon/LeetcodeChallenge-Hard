class Solution {
    fun maxSpending(values: Array<IntArray>): Long {
        val pq = PriorityQueue<Pair<Int, Int>>(compareBy { values[it.first][it.second] })
        var ans = 0L
        var day = 1L
        values.forEachIndexed { idx, ints ->
            pq.add(idx to ints.lastIndex)
        }
        while (pq.isNotEmpty()) {
            val top = pq.poll()
            ans += day * values[top.first][top.second]
            day++
            if (top.second > 0) {
                pq.add(top.first to top.second - 1)
            }
        }
        return ans
    }
}