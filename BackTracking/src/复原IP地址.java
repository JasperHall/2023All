import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/restore-ip-addresses/
 * @Version 1.0
 * @Author:MenFanys
 * @Date:2023/6/11 14:41
 */
public class 复原IP地址 {  // 93. 复原 IP 地址

    class Solution {
        List<String> result = new ArrayList<>();  // 记录结果

        public List<String> restoreIpAddresses(String s) {
            if (s.length() > 12) return result;  // 算是直接剪枝了
            backTrack(s, 0, 0);
            return result;
        }

        /**
         * startIndex: 搜索的起始位置,  pointNum:添加逗点的数量
         * @param s
         * @param startIndex
         * @param pointNum
         */
        private void backTrack(String s, int startIndex, int pointNum){
            if (pointNum == 3){  // 逗点数量为3时，分隔结束
                // 判断第四段⼦字符串是否合法，如果合法就放进result中
                if (isValid(s,startIndex,s.length()-1)) {
                    result.add(s);
                }
                return;
            }

            for (int i=startIndex; i<s.length(); i++){
                if (isValid(s, startIndex, i)){
                    s = s.substring(0, i+1) + "." + s.substring(i+1);  // 在str的后⾯插⼊⼀个逗点

                    pointNum++;
                    backTrack(s, i+2, pointNum);  // 插⼊逗点之后下⼀个⼦串的起始位置为i+2

                    pointNum--;  // 回溯
                    s = s.substring(0, i+1) + s.substring(i+2);  // 回溯 删掉 逗点 +2就跳过逗点了
                } else {
                  break;  // 不合法，直接结束本层循环
                }
            }
        }

        /**
         * 判断字符串s在左闭⼜闭区间[start, end]所组成的数字是否合法 的方法
         * @param s
         * @param start
         * @param end
         * @return
         */
        private Boolean isValid(String s, int start, int end){
            if (start > end) return false;  // 起始点大于结束点, 返回false

            if (s.charAt(start)=='0' && start!=end){  // 0开头的数字不合法, !=end表示不在末尾
                return false;
            }

            int num = 0;
            for (int i=start; i<=end; i++){
                if (s.charAt(i)>'9' || s.charAt(i)<'0'){  // 遇到 非数字 字符 不合法
                    return false;
                }
                num = num * 10 + (s.charAt(i) - '0');  // 注意: 这里用*10来进位 和 注意ASCII类型转换
                if (num > 255) { // 如果⼤于255了不合法
                    return false;
                }
            }
            return true;
        }




    }
}
