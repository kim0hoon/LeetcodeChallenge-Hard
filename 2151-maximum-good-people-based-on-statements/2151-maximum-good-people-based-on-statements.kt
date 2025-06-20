class Solution {
    fun maximumGood(statements: Array<IntArray>): Int {
        var ans = 0
        fun hasFlag(bit: Int, i: Int) = (bit and (1 shl i)) != 0
        fun isPossible(bit: Int): Boolean {
            statements.forEachIndexed { index, ints ->
                if (hasFlag(bit, index).not()) return@forEachIndexed
                ints.forEachIndexed { idx, s ->
                    if ((s == 0 && hasFlag(bit, idx)) || (s == 1 && hasFlag(bit, idx).not())) return false
                }
            }

            return true
        }
        for (i in 1 until (1 shl statements.size)) {
            if (isPossible(i)) {
                val goodPerson = i.toString(2).count { it == '1' }
                ans = maxOf(ans, goodPerson)
            }
        }
        return ans
    }
}