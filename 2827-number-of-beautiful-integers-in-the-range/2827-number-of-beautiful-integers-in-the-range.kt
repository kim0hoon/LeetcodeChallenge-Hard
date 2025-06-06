class Solution {
    fun numberOfBeautifulIntegers(low: Int, high: Int, k: Int): Int {
        var answer = 0
        val modKCount = List(k) { mutableMapOf<Pair<Int, Int>, Int>() }
        val preCalNum = 1_000_0
        repeat(preCalNum) {
            val mod = (k - it % k) % k
            val odd = it.toString().count { it.digitToInt() % 2 == 1 }
            val even = 4 - odd
            modKCount[mod].run {
                put(odd to even, getOrDefault(odd to even, 0) + 1)
            }
        }
        val lowRoundUp = (low + preCalNum - 1).let { it - it % preCalNum }
        val highRoundDown = high - high % preCalNum
        fun isBeautiful(n: Int): Boolean {
            val str = n.toString()
            val odd = str.count { it.digitToInt() % 2 == 1 }
            return odd == str.length - odd
        }
        if (lowRoundUp >= high || highRoundDown <= low) {
            (low..high).forEach {
                if (it % k != 0) return@forEach
                if(isBeautiful(it)) answer++
            }
        } else {
            (low until lowRoundUp).forEach {
                if (it % k != 0) return@forEach
                if(isBeautiful(it)) answer++
            }
            (highRoundDown..high).forEach {
                if (it % k != 0) return@forEach
                if(isBeautiful(it)) answer++

            }
            (lowRoundUp / preCalNum until highRoundDown / preCalNum).forEach {
                var str = it.toString()
                if (str.length % 2 == 1) return@forEach
                val odd = str.count { it.digitToInt() % 2 == 1 }
                val even = str.length - odd
                val totalLength = str.length + 4
                answer += modKCount[(it * preCalNum) % k].getOrDefault(
                    (totalLength / 2).let { it - odd to it - even },
                    0
                )
            }
        }
        return answer
    }
}