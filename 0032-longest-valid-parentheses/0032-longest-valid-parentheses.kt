class Solution {
    fun longestValidParentheses(s: String): Int {
        var st = Stack<Pair<Boolean, Int>>()
        var ans = 0
        s.forEach {
            if (it == '(') st.push(true to 0)
            else if (st.isNotEmpty() && st.peek().first) {
                val top = st.pop()
                var length = top.second + 2
                while (st.isNotEmpty() && st.peek().first.not()) {
                    length += st.pop().second
                }
                if (st.isEmpty()) st.push(false to length)
                else {
                    val t = st.pop()
                    st.push(true to t.second + length)
                }
                ans = maxOf(ans,st.peek().second)
            } else {
                st.clear()
            }
        }
        return ans
    }
}