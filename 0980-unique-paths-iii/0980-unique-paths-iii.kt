class Solution {
    fun uniquePathsIII(grid: Array<IntArray>): Int {
        val nodeNum = grid.size * grid[0].size
        val dp = List(nodeNum) { MutableList(1 shl nodeNum) { 0 } }
        val masked = { source: Int, n: Int -> source or (1 shl n) }
        val adjList = List(nodeNum) { mutableListOf<Int>() }
        var start = -1
        var end = -1
        var endBit = 0
        grid.forEachIndexed { r, ints ->
            ints.forEachIndexed { c, i ->
                val num = r * ints.size + c
                if (i == 1) start = num
                if (i == 2) end = num
                if (r > 0 && grid[r - 1][c] != -1) adjList[num].add(num - ints.size)
                if (r < grid.lastIndex && grid[r + 1][c] != -1) adjList[num].add(num + ints.size)
                if (c > 0 && grid[r][c - 1] != -1) adjList[num].add(num - 1)
                if (c < ints.lastIndex && grid[r][c + 1] != -1) adjList[num].add(num + 1)
                if (i != -1) endBit = masked(endBit, num)
            }
        }
        val bit = masked(0, start)
        dp[start][bit] = 1
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(start to bit)
        while (queue.isNotEmpty()) {
            val current = queue.poll()
            adjList[current.first].forEach { next ->
                val nBit = masked(current.second, next)
                if (nBit == current.second) return@forEach
                if (dp[next][nBit] == 0) {
                    dp[next][nBit] = dp[current.first][current.second]
                    queue.add(next to nBit)
                } else dp[next][nBit] += dp[current.first][current.second]
            }
        }
        return dp[end][endBit]
    }
}