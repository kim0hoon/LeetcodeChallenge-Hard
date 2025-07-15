class Solution {
    fun earliestAndLatest(n: Int, firstPlayer: Int, secondPlayer: Int): IntArray {
        var min = Int.MAX_VALUE
        var max = -1

        data class Data(val round: Int, val a: Int, val b: Int, val c: Int)

        val check = mutableSetOf<Data>()
        val initData = Data(
            1,
            maxOf(firstPlayer - 1, n - secondPlayer),
            secondPlayer - firstPlayer - 1,
            minOf(firstPlayer - 1, n - secondPlayer)
        )
        check.add(initData)
        val queue: Queue<Data> = LinkedList()
        queue.add(initData)
        while (queue.isNotEmpty()) {
            val now = queue.poll()
            if (now.a < 0 || now.c < 0) continue
            if (now.a == now.c) {
                min = minOf(min, now.round)
                max = maxOf(max, now.round)
                continue
            }
            val total = now.run { a + b + c + 2 }
            if (now.a < total / 2) {
                for (na1 in 0..now.c) for (na2 in 0 until now.a - now.c) {
                    val na = na1 + na2
                    val nc = now.c - na1
                    val nData =
                        Data(now.round + 1, maxOf(na, nc), (now.b - 1 - na2).let { it / 2 + it % 2 }, minOf(na, nc))
                    if (check.contains(nData)) continue
                    check.add(nData)
                    queue.add(nData)
                }
            } else if (now.a * 2 + 1 == total) {
                for (nb in 0..now.b) for (nc in 0..now.c) {
                    val na = now.a - nb - nc - 1
                    val nData = Data(now.round + 1, maxOf(na, nc), nb, minOf(na, nc))
                    if (check.contains(nData)) continue
                    check.add(nData)
                    queue.add(nData)
                }
            } else {
                for (nb in 0..now.b) for (nc in 0..now.c) {
                    val na = now.a - nb - nc - 2 - (now.a - total / 2 - total % 2)
                    val nData = Data(now.round + 1, maxOf(na, nc), nb, minOf(na, nc))
                    if(check.contains(nData)) continue
                    check.add(nData)
                    queue.add(nData)
                }
            }
        }
        return intArrayOf(min, max)
    }
}