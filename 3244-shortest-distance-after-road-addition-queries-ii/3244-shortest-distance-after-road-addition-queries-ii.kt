class Solution {
    fun shortestDistanceAfterQueries(n: Int, queries: Array<IntArray>): IntArray {
        val par = MutableList(n) { it }
        val size = MutableList(n) { 1 }
        var cnt = n
        fun find(a: Int): Int {
            if (par[a] != a) {
                par[a] = find(par[a])
            }
            return par[a]
        }

        fun union(a: Int, b: Int) {
            val pa = find(a)
            val pb = find(b)
            if (pa == pb) return
            val root = minOf(pa, pb)
            val sub = maxOf(pa, pb)
            par[sub] = root
            size[root] += size[sub]
            cnt -= 1
        }

        return queries.map {
            var now = it[0]

            while (now < it[1]) {
                val next = now + size[find(now)]
                union(it[0], now)
                now = next
            }
            cnt - 1
        }.toIntArray()
    }
}