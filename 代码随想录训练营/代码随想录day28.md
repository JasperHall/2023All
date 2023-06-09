时间: 2023.6.6周二
<a name="sPFyY"></a>
# 今日任务
第七章 回溯算法, 93.复原IP地址, 78.子集, 90.子集II  
<a name="CXkSh"></a>
# 收获
<a name="KOISD"></a>
# 明天计划

- [ ] day28__90.子集II  的去重再理解理解
- [ ] 40.数组总和II  的回溯去重再看看
- [ ] day28__90.子集II 的去重操作
<a name="XBsr0"></a>
# 复习

- [ ] 复习day27, 数组总和II
- [ ] 复习 131.分割回文串  

<a name="K5uLx"></a>
# 93.复原IP地址  
:::info
本期本来是很有难度的，不过 大家做完 分割回文串 之后，本题就容易很多了 <br />题目链接/文章讲解：[https://programmercarl.com/0093.%E5%A4%8D%E5%8E%9FIP%E5%9C%B0%E5%9D%80.html](https://programmercarl.com/0093.%E5%A4%8D%E5%8E%9FIP%E5%9C%B0%E5%9D%80.html)<br />视频讲解：[https://www.bilibili.com/video/BV1XP4y1U73i/](https://www.bilibili.com/video/BV1XP4y1U73i/)
:::
本题是一个切割问题, 切割问题就可以使用回溯搜索算法把所有的可能性搜索出来<br />切割问题可以抽象为树型结构，如图：<br />![20201123203735933.png](https://cdn.nlark.com/yuque/0/2023/png/32832913/1686468650167-1904d12b-fa63-4067-a0d2-645a4c92a535.png#averageHue=%23f6f5f5&clientId=u43ac0098-d5e7-4&from=paste&height=627&id=ubab98a48&originHeight=734&originWidth=1594&originalType=binary&ratio=1.1699999570846558&rotation=0&showTitle=false&size=149340&status=done&style=none&taskId=u7f90df4d-31c9-4197-aa93-2aba7d470b7&title=&width=1362.3932123654477)
<a name="U3jKC"></a>
## 回溯三部曲

1. **递归函数**

`startIndex`一定是需要的，因为**不能重复分割**，记录下一层递归分割的起始位置。本题我们还需要一个变量`pointNum`，记录添加逗点的数量。所以代码如下：
```java
List<String> result = new ArrayList<>();  // 记录结果
 // startIndex: 搜索的起始位置， pointNum:添加逗点的数量
private void backTrack(String s, int startIndex, int pointNum) {
```

2. **递归终止条件**

本题明确要求只会分成4段，所以不能用切割线切到最后作为终止条件，而是**分割的段数作为终止条件**。`pointNum`表示逗点数量，`pointNum`为3说明字符串分成了4段了。然后验证一下第四段是否合法，如果合法就加入到结果集里, 代码如下：
```java
if (pointNum == 3) {// 逗点数量为3时，分隔结束
    // 判断第四段⼦字符串是否合法，如果合法就放进result中
    if (isValid(s,startIndex,s.length()-1)) {
        result.add(s);
    }
    return;
}
```

3. 单层搜索的逻辑

在`for (int i = startIndex; i < s.size(); i++)`循环中`[startIndex, i]`这个区间就是截取的子串，需要判断这个子串是否合法。

- 如果合法就在字符串后面加上符号`.`表示已经分割。
- 如果不合法就结束本层循环，如图中剪掉的分支：

![20201123203735933-20230310132314109.png](https://cdn.nlark.com/yuque/0/2023/png/32832913/1686468869610-2d6c524b-d5fd-4ace-a591-b324a8461991.png#averageHue=%23f6f5f5&clientId=u43ac0098-d5e7-4&from=paste&height=627&id=u541dea5a&originHeight=734&originWidth=1594&originalType=binary&ratio=1.1699999570846558&rotation=0&showTitle=false&size=149340&status=done&style=none&taskId=u4c41cb0b-bc4f-40c1-9e4a-1d487832f0c&title=&width=1362.3932123654477)<br />然后就是递归和回溯的过程：<br />递归调用时，下一层递归的`startIndex`要从`i+2`开始（因为需要在字符串中加入了分隔符.），同时记录分割符的数量`pointNum`要 `+1`。<br />回溯的时候，就将刚刚加入的分隔符`.`删掉就可以了，`pointNum`也要 -1。代码如下：
```java
for (int i = startIndex; i < s.length(); i++) {
    if (isValid(s, startIndex, i)) {
        s = s.substring(0, i + 1) + "." + s.substring(i + 1);    //在str的后⾯插⼊⼀个逗点
        pointNum++;
        backTrack(s, i + 2, pointNum);// 插⼊逗点之后下⼀个⼦串的起始位置为i+2
        pointNum--;// 回溯
        s = s.substring(0, i + 1) + s.substring(i + 2);// 回溯删掉逗点
    } else {
        break;  // 不合法，直接结束本层循环
    }
}
```
<a name="bwVEE"></a>
## 判断字串是否合法
最后就是在写一个判断段位是否是有效段位了。主要考虑到如下三点：

- 段位以0为开头的数字不合法
- 段位里有非正整数字符不合法
- 段位如果大于255了不合法

代码如下：
```java
// 判断字符串s在左闭⼜闭区间[start, end]所组成的数字是否合法
private Boolean isValid(String s, int start, int end) {
    if (start > end) {
        return false;
    }
    if (s.charAt(start) == '0' && start != end) { // 0开头的数字不合法
        return false;
    }
    int num = 0;
    for (int i = start; i <= end; i++) {
        if (s.charAt(i) > '9' || s.charAt(i) < '0') { // 遇到⾮数字字符不合法
            return false;
        }
        num = num * 10 + (s.charAt(i) - '0');  // 注意: 这里用*10来进位 和 注意ASCII类型转换
        if (num > 255) { // 如果⼤于255了不合法
            return false;
        }
    }
    return true;
}
```
<a name="qqPJ6"></a>
## 完整代码
```java
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
```
:::danger
注意:

1. `s.substring()`方法的使用
2. 注意理解判断字符串是够合法的方法中对细节的处理
:::
<a name="l7scl"></a>
### 补充方法
```java
//方法一：但使用stringBuilder，故优化时间、空间复杂度，
// 因为向字符串插入字符时无需复制整个字符串，从而减少了操作的时间复杂度，也不用开新空间存subString，从而减少了空间复杂度。
class Solution {
    List<String> result = new ArrayList<>();
    public List<String> restoreIpAddresses(String s) {
        StringBuilder sb = new StringBuilder(s);  // 将字符串s转为 StringBuilder 然后处理
        backTracking(sb, 0, 0);
        return result;
    }
    private void backTracking(StringBuilder s, int startIndex, int dotCount){
        
        if(dotCount == 3){
            if(isValid(s, startIndex, s.length() - 1)){
                result.add(s.toString());
            }
            return;
        }
        
        for(int i = startIndex; i < s.length(); i++){
            if(isValid(s, startIndex, i)){
                s.insert(i + 1, '.'); // 注意这一步的用法
                
                backTracking(s, i + 2, dotCount + 1);
                
                s.deleteCharAt(i + 1);
            }else{
                break;
            }
        }
    }
    //[start, end]  判断字符串s在左闭⼜闭区间[start, end]所组成的数字是否合法 的方法
    private boolean isValid(StringBuilder s, int start, int end){
        if(start > end)
            return false;
        if(s.charAt(start) == '0' && start != end)
            return false;
        int num = 0;
        for(int i = start; i <= end; i++){
            int digit = s.charAt(i) - '0';
            num = num * 10 + digit;
            if(num > 255)
                return false;
        }
        return true;
    }
}


//方法二：比上面的方法时间复杂度低，更好地剪枝，优化时间复杂度
class Solution {
    List<String> result = new ArrayList<String>();
	StringBuilder stringBuilder = new StringBuilder();

	public List<String> restoreIpAddresses(String s) {
		restoreIpAddressesHandler(s, 0, 0);
		return result;
	}

	// number表示stringbuilder中ip段的数量
	public void restoreIpAddressesHandler(String s, int start, int number) {
		// 如果start等于s的长度并且ip段的数量是4，则加入结果集，并返回
		if (start == s.length() && number == 4) {
			result.add(stringBuilder.toString());
			return;
		}
		// 如果start等于s的长度但是ip段的数量不为4，或者ip段的数量为4但是start小于s的长度，则直接返回
		if (start == s.length() || number == 4) {
			return;
		}
		// 剪枝：ip段的长度最大是3，并且ip段处于[0,255]
		for (int i = start; i < s.length() && i - start < 3 && Integer.parseInt(s.substring(start, i + 1)) >= 0
				&& Integer.parseInt(s.substring(start, i + 1)) <= 255; i++) {
			// 如果ip段的长度大于1，并且第一位为0的话，continue
			if (i + 1 - start > 1 && s.charAt(start) - '0' == 0) {
				continue;
			}
			stringBuilder.append(s.substring(start, i + 1));
			// 当stringBuilder里的网段数量小于3时，才会加点；如果等于3，说明已经有3段了，最后一段不需要再加点
			if (number < 3) {
				stringBuilder.append(".");
			}
			number++;
			restoreIpAddressesHandler(s, i + 1, number);
			number--;
			// 删除当前stringBuilder最后一个网段，注意考虑点的数量的问题
			stringBuilder.delete(start + number, i + number + 2);
		}
	}
}
```

<a name="x4Ipx"></a>
# 78.子集  
:::info
子集问题，就是收集树形结构中，每一个节点的结果。 整体代码其实和 回溯模板都是差不多的。 <br />题目链接/文章讲解：[https://programmercarl.com/0078.%E5%AD%90%E9%9B%86.html](https://programmercarl.com/0078.%E5%AD%90%E9%9B%86.html)<br />视频讲解：[https://www.bilibili.com/video/BV1U84y1q7Ci](https://www.bilibili.com/video/BV1U84y1q7Ci)
:::
![image.png](https://cdn.nlark.com/yuque/0/2023/png/32832913/1686472665531-4f736529-a0c5-4b42-b8fb-5050c818f8ca.png#averageHue=%23323231&clientId=u43ac0098-d5e7-4&from=paste&height=117&id=ub24be24a&originHeight=137&originWidth=357&originalType=binary&ratio=1.1699999570846558&rotation=0&showTitle=false&size=5322&status=done&style=none&taskId=uf994da65-dfa8-481b-8f53-208520da510&title=&width=305.1282163202414)<br />如果把 子集问题、组合问题、分割问题 都抽象为一棵树的话，那么 **组合问题** 和 **分割问题** 都是**收集树的叶子节点**，而**子集问题**是**找树的所有节点**！<br />其实子集也是一种组合问题，因为它的集合是无序的，子集{1,2} 和 子集{2,1}是一样的。<br />那么既然是无序，取过的元素不会重复取，写回溯算法的时候，for就要从`startIndex`开始，而不是从0开始！<br />有同学问了，什么时候for可以从0开始呢？求排列问题的时候，就要从0开始，因为集合是有序的，{1, 2} 和{2, 1}是两个集合，排列问题我们后续的文章就会讲到的。<br />以示例中 nums = [1,2,3] 为例把求子集抽象为树型结构，如下：<br />![78.子集.png](https://cdn.nlark.com/yuque/0/2023/png/32832913/1686473392671-42891ee9-81c1-4ff4-bbc1-bb80fbf6da1f.png#averageHue=%23f5f5f5&clientId=u43ac0098-d5e7-4&from=paste&height=771&id=ud1e37a34&originHeight=902&originWidth=1546&originalType=binary&ratio=1.1699999570846558&rotation=0&showTitle=false&size=153019&status=done&style=none&taskId=ub111e1b3-914f-4208-8790-8d08682214e&title=&width=1321.367569834995)<br />从图中红线部分，可以看出遍历这个树的时候，把所有节点都记录下来，就是要求的子集集合。
<a name="qkCQo"></a>
## 回溯三部曲

1. **递归函数参数**

全局变量链表`path`为子集收集元素，二维列表`result`存放子集组合。（也可以放到递归函数参数里）<br />递归函数参数在上面讲到了，需要`startIndex`。代码如下：
```java
List<List<Integer>> result = new ArrayList<>();// 存放符合条件结果的集合
LinkedList<Integer> path = new LinkedList<>();// 用来存放符合条件结果

void subsetsHelper(int[] nums, int startIndex){
```

2. **递归终止条件**

从图中可以看出：<br />![78.子集 (1).png](https://cdn.nlark.com/yuque/0/2023/png/32832913/1686473931529-d2a6bf70-15c7-4b63-92ce-ba6f4bba351d.png#averageHue=%23f5f5f5&clientId=u43ac0098-d5e7-4&from=paste&height=771&id=u43dacd18&originHeight=902&originWidth=1546&originalType=binary&ratio=1.1699999570846558&rotation=0&showTitle=false&size=153019&status=done&style=none&taskId=uec2c97e2-998b-4fc6-a0ef-c95215d5646&title=&width=1321.367569834995)<br />剩余集合为空的时候，就是叶子节点。<br />那么什么时候剩余集合为空呢？就是`startIndex`已经大于数组的长度了，就终止了，因为没有元素可取了，代码如下:
```java
if (startIndex >= nums.length){ //终止条件可不加
    return;
}
```
其实可以不需要加终止条件，因为`startIndex >= nums.size()`时，本层for循环本来也结束了。

3. **单层搜索逻辑**

求取子集问题，不需要任何剪枝！因为子集就是要遍历整棵树。那么单层递归逻辑代码如下：
```java
for (int i = startIndex; i < nums.length; i++){
    path.add(nums[i]);
    subsetsHelper(nums, i + 1);
    path.removeLast();
}
```

完整代码: 
```java
class Solution {
    List<List<Integer>> result = new ArrayList<>();// 存放符合条件结果的集合
    LinkedList<Integer> path = new LinkedList<>();// 用来存放符合条件结果
    public List<List<Integer>> subsets(int[] nums) {
        subsetsHelper(nums, 0);
        return result;
    }

    private void subsetsHelper(int[] nums, int startIndex){
        result.add(new ArrayList<>(path));//「遍历这个树的时候，把所有节点都记录下来，就是要求的子集集合」。
        if (startIndex >= nums.length){ //终止条件可不加  其实可以不需要加终止条件，因为startIndex >= nums.size()时，本层for循环本来也结束了
            return;
        }
        for (int i = startIndex; i < nums.length; i++){
            path.add(nums[i]);
            subsetsHelper(nums, i + 1);
            path.removeLast();
        }
    }
}
```
-
<a name="Kiwxw"></a>
# 90.子集II 
:::info
大家之前做了 40.组合总和II 和 78.子集 ，本题就是这两道题目的结合，建议自己独立做一做，本题涉及的知识，之前都讲过，没有新内容。 <br />题目链接/文章讲解：[https://programmercarl.com/0090.%E5%AD%90%E9%9B%86II.html](https://programmercarl.com/0090.%E5%AD%90%E9%9B%86II.html)<br />视频讲解：[https://www.bilibili.com/video/BV1vm4y1F71J](https://www.bilibili.com/video/BV1vm4y1F71J)
:::
:::danger
注意本题的去重操作
:::
之前做了 `40.组合总和II` 和 `78.子集` ，本题就是这两道题目的结合.
>  `40.组合总和II`中讲了回溯算法中的去重问题

用示例中的 [1, 2, 2] 来举例，如图所示： （注意去重需要先对集合排序）<br />![20201124195411977.png](https://cdn.nlark.com/yuque/0/2023/png/32832913/1686477516932-364f80c7-5a7e-47e2-9d9b-2518062ae80c.png#averageHue=%23f7f6f6&clientId=u43ac0098-d5e7-4&from=paste&height=885&id=u86cd4749&originHeight=1036&originWidth=1752&originalType=binary&ratio=1.1699999570846558&rotation=0&showTitle=false&size=298634&status=done&style=none&taskId=uce00162f-4974-401c-bc89-a07ad7d7b83&title=&width=1497.4359523615208)<br />从图中可以看出，同一树层上重复取2 就要过滤掉，同一树枝上就可以重复取2，因为同一树枝上元素的集合才是唯一子集！
```java
// 使用used数组
class Solution {
    List<List<Integer>> result = new ArrayList<>();  // 存放符合条件结果的集合
    LinkedList<Integer> path = new LinkedList<>();  // 用来存放符合条件结果
    boolean[] used;

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums.length==0){
            result.add(path);
            return result;
        }
        Arrays.sort(nums);  // 排序一下
        used = new boolean[nums.length];  // 建立标记数组
        subsetsWithDupHelper(nums, 0);  // 调用自定义方法
        return result;
    }
    private void subsetsWithDupHelper(int[] nums, int startIndex){
        result.add(new ArrayList<>(path));  // 加一个空值

        if (startIndex >= nums.length) return;

        for (int i=startIndex; i<nums.length; i++){
            if (i>0 && nums[i]==nums[i-1] && !used[i-1]){  // !used[i-1] 和 used[i-1]==false 是一样的
                continue;
            }

            path.add(nums[i]);
            used[i] = true;
            subsetsWithDupHelper(nums, i+1);
            path.removeLast();
            used[i] = false;
        }

    }
}
```
```java
// 不使用used数组
class Solution {

  List<List<Integer>> res = new ArrayList<>();
  LinkedList<Integer> path = new LinkedList<>();
  
  public List<List<Integer>> subsetsWithDup( int[] nums ) {
    Arrays.sort( nums );
    subsetsWithDupHelper( nums, 0 );
    return res;
  }


  private void subsetsWithDupHelper( int[] nums, int start ) {
    res.add( new ArrayList<>( path ) );

    for ( int i = start; i < nums.length; i++ ) {
        // 跳过当前树层使用过的、相同的元素
      if ( i > start && nums[i - 1] == nums[i] ) {
        continue;
      }
      path.add( nums[i] );
      subsetsWithDupHelper( nums, i + 1 );
      path.removeLast();
    }
  }

}
```

