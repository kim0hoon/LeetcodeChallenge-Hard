class Solution {
    fun subsequencesWithMiddleMode(nums: IntArray): Int {
        val mod = 1_000_000_000 + 7
        fun Long.modMul(other: Long) = (this * other) % mod
        fun Long.modPlus(other: Long) = (this + other) % mod
        fun Long.modMinus(other: Long) = (mod + this - other) % mod
        fun Long.modComb2() = (this * (this - 1) / 2) % mod
        fun Map<Int, Long>.getOrZero(key: Int) = getOrDefault(key, 0)

        var answer = 0L
        val leftMap = mutableMapOf<Int, Long>()
        val rightMap = mutableMapOf<Int, Long>()
        val numSet = nums.toSet()
        nums.forEachIndexed { index, i ->
            if (index < 2) leftMap[i] = leftMap.getOrZero(i) + 1
            else rightMap[i] = rightMap.getOrZero(i) + 1
        }

        for (i in 2..nums.lastIndex - 2) {
            rightMap[nums[i]] = rightMap.getOrZero(nums[i]) - 1
            val rightSum = rightMap.values.sumOf { it } % mod
            val leftSum = leftMap.values.sumOf { it } % mod
            val leftCurrent = leftMap.getOrZero(nums[i])
            val rightCurrent = rightMap.getOrZero(nums[i])
            val all = leftSum.modComb2().modMul(rightSum.modComb2())
            val zero = leftSum.modMinus(leftCurrent).modComb2().modMul((rightSum.modMinus(rightCurrent)).modComb2())
            var oneImpossible = numSet.filterNot { it == nums[i] }.sumOf { target ->
                val leftNotContainsBoth = leftSum - leftCurrent - leftMap.getOrZero(target)
                val rightNotContainsBoth = rightSum - rightCurrent - rightMap.getOrZero(target)
                val leftTargetOneToOne =
                    leftCurrent.modMul(leftMap.getOrZero(target)).modMul(rightMap.getOrZero(target))
                        .modMul(rightNotContainsBoth)
                val leftTargetZeroToTwo =
                    leftCurrent.modMul(leftSum.modMinus(leftCurrent).modMinus(leftMap.getOrZero(target)))
                        .modMul(rightMap.getOrZero(target).modComb2())
                val leftTargetOneToTwo =
                    leftCurrent.modMul(leftMap.getOrZero(target)).modMul(rightMap.getOrZero(target).modComb2())

                val rightTargetOneToOne =
                    rightCurrent.modMul(rightMap.getOrZero(target)).modMul(leftMap.getOrZero(target))
                        .modMul(leftNotContainsBoth)
                val rightTargetZeroToTwo =
                    rightCurrent.modMul(rightSum.modMinus(rightCurrent).modMinus(rightMap.getOrZero(target)))
                        .modMul(leftMap.getOrZero(target).modComb2())
                val rightTargetOneToTwo =
                    rightCurrent.modMul(rightMap.getOrZero(target)).modMul(leftMap.getOrZero(target).modComb2())
                leftTargetOneToOne.modPlus(leftTargetZeroToTwo).modPlus(leftTargetOneToTwo).modPlus(rightTargetOneToOne)
                    .modPlus(rightTargetZeroToTwo).modPlus(rightTargetOneToTwo)
            }
            answer = answer.modPlus(all).modMinus(zero).modMinus(oneImpossible)
            leftMap[nums[i]] = leftMap.getOrZero(nums[i]) + 1
        }
        return answer.toInt()
    }
}