class Solution {
    fun largestPathValue(colors: String, edges: Array<IntArray>): Int {
        val adjList = List(colors.length) { mutableListOf<Int>() }
        val prevCnt = MutableList(colors.length) { 0 }
        val dp = List(colors.length) { mutableMapOf<Char, Int>() }
        val queue = LinkedList<Int>()
        edges.forEach {
            adjList[it[0]].add(it[1])
            prevCnt[it[1]]++
        }
        prevCnt.forEachIndexed {idx,i-> if (i == 0) queue.add(idx) }
        while (queue.isNotEmpty()) {
            val now = queue.poll()
            val color = colors[now]
            dp[now][color] = (dp[now][color] ?: 0) + 1
            adjList[now].forEach { next ->
                dp[now].forEach {
                    dp[next][it.key] = maxOf(dp[next][it.key] ?: 0, it.value)
                }
                prevCnt[next]--
                if (prevCnt[next] == 0) queue.add(next)
            }
        }

        return if (prevCnt.any { it > 0 }) -1 else dp.maxOf { it.values.maxOfOrNull { it } ?: -1 }
    }
}