class Solution {
    fun minimumWeight(edges: Array<IntArray>, queries: Array<IntArray>): IntArray {
        val n = edges.size + 1
        val adjList = List(n) { mutableListOf<Pair<Int, Int>>() }
        val par = List(n) { mutableListOf<Pair<Int, Int>>() }
        val path = mutableListOf<Pair<Int, Int>>()
        val height = MutableList(n) { 0 }
        edges.forEach {
            adjList[it[0]].add(it[1] to it[2])
            adjList[it[1]].add(it[0] to it[2])
        }

        path.add(0 to 0)
        fun dfs(n: Int, distFromRoot: Int) {
            var ex2 = 1
            while (ex2 < path.size) {
                val dist = distFromRoot - path[path.size - ex2 - 1].second
                par[n].add(path[path.size - ex2 - 1].first to dist)
                ex2 *= 2
            }
            adjList[n].forEach {
                if (it.first == 0 || par[it.first].isNotEmpty()) return@forEach
                height[it.first] = height[n] + 1
                path.add(it.first to distFromRoot + it.second)
                dfs(it.first, distFromRoot + it.second)
                path.removeLast()
            }
        }
        dfs(0, 0)
        fun moveToHeightAncestor(n: Int, h: Int): Pair<Int, Int> {
            var diff = height[n] - h
            if (diff <= 0) n to 0
            var dist = 0
            var now = n
            var idx = 0
            while (height[now] != h) {
                if (diff % 2 == 1) {
                    now = par[now][idx].run {
                        dist += second
                        first
                    }
                }
                diff /= 2
                idx++
            }
            return now to dist
        }

        fun lca(a: Int, b: Int): Pair<Int, Int> {
            val minHeight = minOf(height[a], height[b])
            var dist = 0
            var na = moveToHeightAncestor(a, minHeight).run {
                dist += second
                first
            }
            var nb = moveToHeightAncestor(b, minHeight).run {
                dist += second
                first
            }
            while (na != nb) {
                var idx = 0
                while (par[na].getOrNull(idx)?.first != par[nb].getOrNull(idx)?.first) idx++
                if (idx == 0) {
                    dist += par[na][idx].second + par[nb][idx].second
                    na = par[na][idx].first
                    break
                }
                na = par[na][idx - 1].run {
                    dist += second
                    first
                }
                nb = par[nb][idx - 1].run {
                    dist += second
                    first
                }
            }
            return na to dist
        }
        return queries.map { query ->
            var ans = 0
            for (i in 0..2) for (j in i + 1..2) {
                ans += lca(query[i], query[j]).second
            }
            ans / 2
        }.toIntArray()
    }
}