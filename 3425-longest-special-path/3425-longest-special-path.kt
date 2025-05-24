class Solution {
    fun longestSpecialPath(edges: Array<IntArray>, nums: IntArray): IntArray {
        var answer = intArrayOf(0, 1)
        data class Edge(val k: Int, val l: Int)
        val adjList = List(edges.size + 1) { mutableListOf<Edge>() }
        edges.forEach {
            adjList[it[0]].add(Edge(it[1], it[2]))
            adjList[it[1]].add(Edge(it[0], it[2]))
        }
        val numToPathIndexMap = mutableMapOf<Int, Int?>()
        val pathAccLength = mutableListOf<Int>()
        var sIdx = 0
        fun dfs(n: Int, par: Int) {
            val _sIdx = sIdx
            var prevIndex = numToPathIndexMap[nums[n]]
            prevIndex?.let {
                 sIdx = maxOf(it + 1, sIdx)
            }
            numToPathIndexMap[nums[n]] = pathAccLength.lastIndex + 1
            val sumOfLength =
                (pathAccLength.lastOrNull() ?: 0) - (pathAccLength.getOrNull(sIdx - 1) ?: 0)
            if (answer[0] < sumOfLength) answer = intArrayOf(sumOfLength, pathAccLength.size - sIdx + 1)
            else if (answer[0] == sumOfLength) answer[1] = minOf(answer[1], pathAccLength.size - sIdx + 1)
            adjList[n].forEach {
                if (it.k == par) return@forEach
                pathAccLength.add((pathAccLength.lastOrNull() ?: 0) + it.l)
                dfs(n = it.k, par = n)
                pathAccLength.removeLast()
            }
            numToPathIndexMap[nums[n]] = prevIndex
            sIdx = _sIdx
        }
        dfs(0, -1)
        return answer
    }
}