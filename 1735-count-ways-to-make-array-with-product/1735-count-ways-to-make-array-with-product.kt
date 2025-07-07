class Solution {
    fun waysToFillArray(queries: Array<IntArray>): IntArray {
        val maxNum = queries.maxOf { it[1] }
        val isPrime = MutableList(maxNum + 1) { it >= 2 }
        val mod = 1_000_000_000 + 7
        for (i in 2..maxNum) {
            if (!isPrime[i]) continue
            for (j in 2 * i..maxNum step (i)) isPrime[j] = false
        }

        val primes = mutableListOf<Int>().apply {
            isPrime.forEachIndexed { index, b -> if (b) add(index) }
        }

        //2^14 >= 10000
        val comb = MutableList(queries.maxOf { it[0] } + 15) { MutableList(minOf(it + 1, 15)) { 0 } }
        for (i in comb.indices) {
            comb[i][0] = 1
            if (comb[i].lastIndex == i) comb[i][comb[i].lastIndex] = 1
            (1 until comb[i].lastIndex).forEach {
                comb[i][it] = (comb[i - 1][it - 1] + comb[i - 1][it]) % mod
            }
        }

        return queries.map { query ->
            val factors = mutableMapOf<Int, Int>()
            var remain = query[1]
            for (prime in primes) {
                if (prime > remain) break
                while (remain % prime == 0) {
                    remain /= prime
                    factors[prime] = (factors[prime] ?: 0) + 1
                }
            }
            var ans = 1L
            factors.forEach {
                ans = (ans * comb[query[0] - 1 + it.value][it.value]) % mod
            }
            ans.toInt()
        }.toIntArray()
    }
}