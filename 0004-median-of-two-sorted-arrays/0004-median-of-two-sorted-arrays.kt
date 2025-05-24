class Solution {
    fun findMedianSortedArrays(nums1: IntArray, nums2: IntArray): Double {
        val size = nums1.size + nums2.size
        val r1 = searchRankValue(nums1, nums2, (size + 1) / 2)
        val r2 = if (size % 2 == 1) r1 else searchRankValue(nums1, nums2, size / 2 + 1)
        return (r1 + r2) / 2
    }

    private fun searchRankValue(nums1: IntArray, nums2: IntArray, targetRank: Int): Double {
        var s = 0
        var e = nums1.lastIndex
        while (s <= e) {
            val m = (s + e) / 2
            val l = nums2.getOrElse(targetRank - m - 2) { if (it < 0) Int.MIN_VALUE else Int.MAX_VALUE }
            val r = nums2.getOrElse(targetRank - m - 1) { if (it < 0) Int.MIN_VALUE else Int.MAX_VALUE }
            if (nums1[m] in l..r) return nums1[m].toDouble()
            else if (nums1[m] < l) s = m + 1 else e = m - 1
        }
        s = 0
        e = nums2.lastIndex
        while (s <= e) {
            val m = (s + e) / 2
            val l = nums1.getOrElse(targetRank - m - 2) { if (it < 0) Int.MIN_VALUE else Int.MAX_VALUE }
            val r = nums1.getOrElse(targetRank - m - 1) { if (it < 0) Int.MIN_VALUE else Int.MAX_VALUE }
            if (nums2[m] in l..r) return nums2[m].toDouble()
            else if (nums2[m] < l) s = m + 1 else e = m - 1
        }
        return nums2[s].toDouble()
    }
}