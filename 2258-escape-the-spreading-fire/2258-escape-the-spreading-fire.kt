class Solution {
    fun maximumMinutes(grid: Array<IntArray>): Int {
        val m = grid.size
        val n = grid[0].size
        val inf = 1_000_000_000
        val times = List(m) { MutableList(n) { inf } }
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        fun isValidPos(r: Int, c: Int) = r in (0 until m) && c in (0 until n)
        fun isValidAndGrass(r: Int, c: Int) = isValidPos(r, c) && grid[r][c] == 0
        fun getValidAdjGrassList(r: Int, c: Int) = mutableListOf<Pair<Int, Int>>().apply {
            if (isValidAndGrass(r - 1, c)) add(r - 1 to c)
            if (isValidAndGrass(r + 1, c)) add(r + 1 to c)
            if (isValidAndGrass(r, c - 1)) add(r to c - 1)
            if (isValidAndGrass(r, c + 1)) add(r to c + 1)
        }
        grid.forEachIndexed { r, ints ->
            ints.forEachIndexed { c, i ->
                if (i == 1) {
                    times[r][c] = 0
                    queue.add(r to c)
                }
            }
        }
        while (queue.isNotEmpty()) {
            val now = queue.poll()
            getValidAdjGrassList(now.first, now.second).forEach {
                if (times[it.first][it.second] != inf) return@forEach
                times[it.first][it.second] = times[now.first][now.second] + 1
                queue.add(it)
            }
        }
        var ans = -1
        var s = 0
        var e = m * n
        val visit = List(m) { MutableList(n) { -1 } }
        while (s <= e) {
            val mid = (s + e) / 2
            queue.clear()
            visit.forEach { it.fill(-1) }
            visit[0][0] = mid
            if (mid <= visit[0][0]) queue.add(0 to 0)
            while (queue.isNotEmpty()) {
                val now = queue.poll()
                if (visit[now.first][now.second] == times[now.first][now.second]) continue
                getValidAdjGrassList(now.first, now.second).forEach {
                    if (visit[it.first][it.second] != -1) return@forEach
                    if (visit[now.first][now.second] + 1 <= times[it.first][it.second]) {
                        visit[it.first][it.second] = visit[now.first][now.second] + 1
                        queue.add(it)
                    }
                }
            }
            if (visit[m - 1][n - 1] == -1) e = mid - 1
            else {
                ans = mid
                s = mid + 1
            }
        }
        return if (ans == m * n) inf else ans
    }
}