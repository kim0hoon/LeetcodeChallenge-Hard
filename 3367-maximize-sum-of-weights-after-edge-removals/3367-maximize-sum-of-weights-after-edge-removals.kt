class Solution {
    fun maximizeSumOfWeights(edges: Array<IntArray>, k: Int): Long {
        val adjList = List(edges.size + 1) { mutableListOf<Pair<Int, Int>>() }
        edges.forEach {
            adjList[it[0]].add(it[1] to it[2])
            adjList[it[1]].add(it[0] to it[2])
        }
        val isVisited = MutableList(edges.size + 1) { false }
        fun dfs(n: Int): Pair<Long, Long> {
            isVisited[n] = true
            var parWeight: Long? = null
            val childrenRes = mutableListOf<Pair<Long, Long>>()
            adjList[n].forEach {
                if (isVisited[it.first]) parWeight = it.second.toLong()
                else childrenRes.add(dfs(it.first))
            }

            val sum = childrenRes.sumOf { it.first }
            val sortedByDiff = childrenRes.map { it.second - it.first }.sortedByDescending { it }.filter { it > 0 }
            return sum + sortedByDiff.take(k).sumOf { it } to (parWeight ?: 0L) + sum + sortedByDiff.take(k - 1)
                .sumOf { it }
        }
        return dfs(0).first
    }
}