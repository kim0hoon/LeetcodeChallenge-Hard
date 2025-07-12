class Solution {
    fun kthCharacter(k: Long, operations: IntArray): Char {
        var ex = 0
        while (k > 1L shl ex) ex++
        var r = k - (1L shl ex) / 2
        val op = mutableListOf<Int>()
        while (ex > 0) {
            op.add(operations[ex - 1])
            while (r * 2 - 1 < 1L shl ex) ex--
            r -= (1L shl ex) / 2
        }
        return 'a'.plus(op.reversed().fold(0) { acc, i ->
            if (i == 0) acc
            else (acc + 1) % 26
        })
    }
}