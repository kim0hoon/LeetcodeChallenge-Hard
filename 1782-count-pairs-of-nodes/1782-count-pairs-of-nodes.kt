class Solution {
    fun countPairs(n: Int, edges: Array<IntArray>, queries: IntArray): IntArray {
        val adj = MutableList(n) { 0 }.apply {
            edges.forEach {
                this[it[0] - 1]++
                this[it[1] - 1]++
            }
        }
        val adjSorted = adj.mapIndexed { index, i -> index to i }.sortedBy { it.second }
        val dup = mutableMapOf<Pair<Int, Int>, Int>().apply {
            edges.forEach {
                val s = it.minOf { it } - 1
                val e = it.maxOf { it } - 1
                put(s to e, getOrDefault(s to e, 0) + 1)
            }
        }.toList()
        return queries.map { query ->
            var ans = 0
            (0 until n).forEach { sNode ->
                var s = 0
                var e = adjSorted.lastIndex
                var r = e + 1
                while (s <= e) {
                    val m = (s + e) / 2
                    if (adjSorted[m].second + adjSorted[sNode].second > query) {
                        r = m
                        e = m - 1
                    } else s = m + 1
                }
                ans += adjSorted.size - r
            }
            ans -= adj.filter { it * 2 > query }.size
            ans /= 2
            ans -= dup.filter {
                val n1 = adj[it.first.first]
                val n2 = adj[it.first.second]
                n1 + n2 > query && n1 + n2 - it.second <= query
            }.size
            ans
        }.toIntArray()
    }
}