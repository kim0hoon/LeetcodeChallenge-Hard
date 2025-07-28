class Solution {
    fun constrainedSubsetSum(nums: IntArray, k: Int): Int {
        val pq = PriorityQueue<Pair<Int, Int>>(compareBy { -it.first })
        var ans = nums.maxOf { it }
        nums.forEachIndexed { index, i ->
            while (pq.isNotEmpty() && index - pq.peek().second > k) pq.poll()
            val n = if (pq.isEmpty()) i else maxOf(i, pq.peek().first + i)
            ans = maxOf(ans, n)
            pq.add(n to index)
        }
        return ans
    }
}