class Solution {
    fun minEdgeReversals(n: Int, edges: Array<IntArray>): IntArray {
        val inEdgeCnt = MutableList(n) { 0 }
        val outEdgeCnt = MutableList(n) { 0 }
        val adjList = MutableList(n) { mutableListOf<Pair<Int, Boolean>>() }
        edges.forEach {
            adjList[it[0]].add(it[1] to true)
            adjList[it[1]].add(it[0] to false)
        }
        val isVisited = MutableList(n) { false }
        var totalInEdge = 0
        fun dfs(n: Int) {
            adjList[n].forEach {
                if (isVisited[it.first]) return@forEach
                isVisited[it.first] = true
                outEdgeCnt[it.first] = outEdgeCnt[n] + if (it.second) 1 else 0
                inEdgeCnt[it.first] = inEdgeCnt[n] + if (it.second) 0 else 1
                if (it.second.not()) totalInEdge++
                dfs(it.first)
            }
        }
        isVisited[0] = true
        dfs(0)
        return IntArray(n){
            totalInEdge - inEdgeCnt[it] + outEdgeCnt[it]
        }
    }
}