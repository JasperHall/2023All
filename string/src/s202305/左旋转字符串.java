package s202305;

/**
 * https://leetcode.cn/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof/
 * @Version 1.0
 * @Author:MenFanys
 * @Date:2023/5/20 11:39
 */
public class 左旋转字符串 {  // 剑指offer58

    public static void main(String[] args) {
        Solution solution = new Solution();
        String res = solution.reverseLeftWords("abcdefg", 2);
        System.out.println(res);
    }

    static class Solution {
        /**
         * 使用StringBuilder申请额外的空间
         * @param s
         * @param n
         * @return
         */
        public String reverseLeftWords(String s, int n) {
            int slow=0, fast = n;
            StringBuilder sb = new StringBuilder();
            while (fast < s.length()){
                sb.append(s.charAt(fast));
                fast++;
            }

            while (slow < n){
                sb.append(s.charAt(slow));
                slow++;
            }
            return sb.toString();
        }

        /**
         * 申请额外的空间
         * 1. 反转区间为前 n 的子串
         * 2. 反转区间为 n 到末尾的子串
         * 3. 反转整个字符串
         * 4. 最后就可以达到左旋 n 的目的，而不用定义新的字符串，完全在本串上操作。
         * @param s
         * @param n
         * @return
         */
        public String reverseLeftWords2(String s, int n) {
            int len = s.length();
            StringBuilder sb = new StringBuilder(s);
            reverseString(sb, 0, n-1); // 反转前面的区间
            reverseString(sb,n,len-1);  // 反转后面的区间
            return sb.reverse().toString();  // 先反转, 再转成字符串
        }
        private void reverseString(StringBuilder sb, int start, int end){
            while (start < end){
                char temp = sb.charAt(start);
                sb.setCharAt(start, sb.charAt(end));
                sb.setCharAt(end, temp);
                start++;
                end--;
            }
        }

        /**
         * 解法三  不使用额外空间
         * 空间复杂度：O(1)。用原始数组来进行反转操作
         *
         * // 思路为：先整个字符串反转，再反转前面的，最后反转后面 n 个
         * @param s
         * @param n
         * @return
         */
        public String reverseLeftWords3(String s, int n) {
            char[] chars = s.toCharArray();
            reverseAA(chars, 0, chars.length - 1);
            reverseAA(chars, 0, chars.length - 1 - n);
            reverseAA(chars, chars.length - n, chars.length - 1);
            return new String(chars);
        }
        private void reverseAA(char[] chars, int left, int right) {
            while (left < right) {
                chars[left] ^= chars[right];
                chars[right] ^= chars[left];
                chars[left] ^= chars[right];
                left++;
                right--;
            }
        }
    }
}
