class RandomizedCollection() {
    val set = mutableSetOf<Pair<Int, Int>>()
    val countMap = mutableMapOf<Int, Int>()
    fun insert(`val`: Int): Boolean {
        val nextCnt = countMap.getOrDefault(`val`, 0) + 1
        set.add(`val` to nextCnt)
        countMap[`val`] = nextCnt
        return nextCnt == 1
    }

    fun remove(`val`: Int): Boolean {
        val targetCnt = countMap.getOrDefault(`val`, 0)
        if (targetCnt > 0) {
            countMap[`val`] = targetCnt - 1
            set.remove(`val` to targetCnt)
        }
        return targetCnt > 0
    }

    fun getRandom(): Int {
        return set.random().first
    }
}

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * var obj = RandomizedCollection()
 * var param_1 = obj.insert(`val`)
 * var param_2 = obj.remove(`val`)
 * var param_3 = obj.getRandom()
 */