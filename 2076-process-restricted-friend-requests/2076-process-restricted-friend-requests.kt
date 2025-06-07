class Solution {
    fun friendRequests(n: Int, restrictions: Array<IntArray>, requests: Array<IntArray>): BooleanArray {
        val u = MutableList(n) { it }
        val rank = MutableList(n) { 0 }
        fun find(n: Int): Int {
            if (n != u[n]) u[n] = find(u[n])
            return u[n]
        }

        fun union(a: Int, b: Int) {
            val aUnion = find(a)
            val bUnion = find(b)
            if (aUnion == bUnion) return
            if (rank[aUnion] > rank[bUnion]) {
                u[bUnion] = aUnion
            } else if (rank[bUnion] > rank[aUnion]) {
                u[aUnion] = bUnion
            } else {
                u[bUnion] = aUnion
                rank[aUnion]++
            }
        }

        return requests.map {
            val u1 = find(it[0])
            val u2 = find(it[1])
            val isPossible = restrictions.any { restriction ->
                restriction.all {
                    listOf(u1, u2).contains(find(it))
                }
            }.not()
            if (isPossible) union(it[0], it[1])
            isPossible
        }.toBooleanArray()
    }
}