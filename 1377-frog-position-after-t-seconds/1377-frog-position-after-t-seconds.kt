class Solution {
    fun frogPosition(n: Int, edges: Array<IntArray>, t: Int, target: Int): Double {
        val check = MutableList(n + 1) { -1.0 }
        val adjList = List(n + 1) { mutableListOf<Int>() }
        edges.forEach {
            adjList[it[0]].add(it[1])
            adjList[it[1]].add(it[0])
        }
        check[1] = 1.0
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(1 to 0)
        while (queue.isNotEmpty()) {
            val now = queue.poll()
            val childCnt = adjList[now.first].count { check[it] == -1.0 }
            if (now.first == target) {
                if (now.second < t && childCnt > 0) check[now.first] = -1.0
                break
            }
            if (now.second >= t) continue
            adjList[now.first].forEach {
                if (check[it] != -1.0) return@forEach
                check[it] = check[now.first] / childCnt
                queue.add(it to now.second + 1)
            }
        }
        return if (check[target] == -1.0) 0.0 else check[target]
    }
}