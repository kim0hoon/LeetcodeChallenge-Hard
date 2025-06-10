class Solution {
    fun minimumMoney(transactions: Array<IntArray>): Long {
        var positiveMaxCost = 0
        var negativeMaxCashback = 0
        var costSum = 0L
        transactions.forEach {
            if (it[0] > it[1]) {
                costSum += it[0] - it[1]
                if (negativeMaxCashback < it[1]) negativeMaxCashback = it[1]
            } else if (positiveMaxCost < it[0]) positiveMaxCost = it[0]
        }
        return maxOf(costSum + negativeMaxCashback, costSum + positiveMaxCost)
    }
}