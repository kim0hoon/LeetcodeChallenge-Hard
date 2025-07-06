class Solution {
    fun minInteger(num: String, k: Int): String {
        val segTree = SegTree(num.length)
        val pq = PriorityQueue(Comparator.comparing<Pair<Char, Int>?, Char?> { it.first }
            .thenBy { it.second })
        var remain = k
        val check = MutableList(num.length) { false }
        val isAdded = MutableList(num.length) { false }
        return buildString {
            var rSize = 0
            var next = 0
            repeat(num.length) {
                val targetSize = minOf(remain + 1, num.length - it)
                while (rSize != targetSize) {
                    if (rSize < targetSize) {
                        if (next == num.length) break
                        if (check[next]) next++
                        else {
                            if (isAdded[next].not()) {
                                pq.add(num[next] to next)
                                isAdded[next] = true
                            }
                            next++
                            rSize++
                        }
                    } else if (rSize > targetSize) {
                        if (next == 0) break
                        next--
                        if (!check[next]) rSize--
                    }
                }
                while (pq.isNotEmpty()) {
                    val top = pq.poll()
                    val swapCnt = top.second - segTree.search(0, top.second)
                    isAdded[top.second] = false
                    if (swapCnt <= remain) {
                        check[top.second] = true
                        append(top.first)
                        remain -= swapCnt
                        rSize--
                        segTree.insert(top.second)
                        break
                    }
                }
            }
        }
    }

    class SegTree(private val size: Int) {
        private val tree: MutableList<Int>

        init {
            var mSize = 2
            while (mSize < size) {
                mSize *= 2
            }
            tree = MutableList(mSize * 2) { 0 }
        }

        fun insert(i: Int) {
            var pos = 1
            var s = 0
            var e = size - 1
            while (s <= e) {
                tree[pos]++
                if (s == e) break
                val m = (s + e) / 2
                if (i <= m) {
                    pos *= 2
                    e = m
                } else {
                    pos = pos * 2 + 1
                    s = m + 1
                }
            }
        }

        fun search(s: Int, e: Int): Int = search(s, e, 1, 0, size - 1)

        private fun search(qs: Int, qe: Int, pos: Int, s: Int, e: Int): Int {
            if (qs == s && qe == e) return tree[pos]
            val m = (s + e) / 2
            var ret = 0
            if (qs <= m) ret += search(qs, minOf(qe, m), pos * 2, s, m)
            if (qe > m) ret += search(maxOf(qs, m + 1), qe, pos * 2 + 1, m + 1, e)
            return ret
        }
    }
}
