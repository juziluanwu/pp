package app.pp.common;

import java.util.Stack;
import java.util.stream.IntStream;

/**
 * 10进制 62进制转换通用类
 *
 * @Author yinjun
 */
public class Encode62 {

    //62进制字符数组
    public static final char[] array = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    /**
     * 10进制转62
     *
     * @param number 10进制数字
     * @return
     */
    public static String _10_to_62(int number) {
        Integer rest = number;
        //定义栈 后进先出便于排序
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            //求余 入栈
            stack.add(array[rest % 62]);
            //源数字除62继续计算
            rest = rest / 62;
        }
        // 出栈
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        //返回62进制数字
        return result.toString();

    }

    /**
     * 62进制转10进制
     *
     * @param sixty_str 62进制字符串
     * @return
     */
    public static int _62_to_10(String sixty_str) {
        int multiple = 1;
        int result = 0;

        //倒叙循环 char列表
        for (int i = sixty_str.length(); i > 0; multiple = multiple * 62) {
            //下标迭代并赋值
            result += _62_value(sixty_str.charAt(--i)) * multiple;
        }
        return result;
    }

    /**
     * 查询单个字符对应的10进制数字,未找到当作0处理
     *
     * @param c 字符
     * @return
     */
    private static int _62_value(Character c) {
        return IntStream.range(0, array.length).filter(i -> c == array[i]).findFirst().orElse(0);
    }
}