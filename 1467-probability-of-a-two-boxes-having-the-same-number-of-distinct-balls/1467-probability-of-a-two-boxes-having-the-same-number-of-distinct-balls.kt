import java.math.BigInteger

class Solution {
    fun getProbability(balls: IntArray): Double {
        val sum = balls.sumOf { it }
        val comb = MutableList(sum + 1) { MutableList(7) { 0 } }
        for (i in 0..sum) {
            comb[i][0] = 1
            if (i <= 6) comb[i][i] = 1
            for (j in 1 until minOf(i, 7)) {
                comb[i][j] = comb[i - 1][j - 1] + comb[i - 1][j]
            }
        }
        var total = BigInteger("1")

        data class Data(val leftCnt: Int, val rightCnt: Int, val leftColor: Int, val rightColor: Int)

        val dp = MutableList(2) { mutableMapOf<Data, BigInteger>() }
        var remain = sum
        dp[1][Data(0, 0, 0, 0)] = BigInteger("1")
        balls.forEachIndexed { idx, i ->
            val now = idx % 2
            val prev = 1 - now
            dp[now].clear()

            dp[prev].forEach { data ->
                (0..i).forEach { leftAdd ->
                    val key = data.key
                    val rightAdd = i - leftAdd
                    val nLeftCnt = key.leftCnt + leftAdd
                    val nRightCnt = key.rightCnt + i - leftAdd
                    if (nLeftCnt > sum / 2 || nRightCnt > sum / 2) return@forEach
                    val nLeftColor = key.leftColor + if (leftAdd == 0) 0 else 1
                    val nRightColor = key.rightColor + if (rightAdd == 0) 0 else 1
                    val nData = Data(nLeftCnt, nRightCnt, nLeftColor, nRightColor)
                    dp[now][nData] = dp[now].getOrDefault(nData, BigInteger("0")) +
                            data.value * BigInteger((comb[sum / 2 - key.leftCnt][leftAdd] * comb[sum / 2 - key.rightCnt][rightAdd]).toString())

                }
            }

            total *= BigInteger(comb[remain][i].toString())
            remain -= i
        }
        return dp[balls.lastIndex % 2].filter { it.key.leftColor == it.key.rightColor }.values.sumOf { it }
            .toDouble() / total.toDouble()
    }
}