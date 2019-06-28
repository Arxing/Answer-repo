package org.arxing;

import java.io.InputStream;
import java.math.BigInteger;

//猜數字遊戲
public class GuessNumberGame extends ScanLooper<Integer> {
    private int pwd = 70;
    private int min = 0;
    private int max = 99;
    private int count = 0;
    private int maxCount = 5;

    {
        //新增離開條件
        addExitCondition(s -> s.matches("[qQ]"));
        //限制不能輸入非整數字串
        addInvalidCondition(s -> !s.matches("-?\\d+"), "請輸入合法整數");
        //限制不能輸入範圍外的整數
        addInvalidCondition(s -> {
            BigInteger bi = new BigInteger(s);
            return bi.compareTo(BigInteger.valueOf(min)) < 0 || bi.compareTo(BigInteger.valueOf(max)) > 0;
        }, "請輸入範圍內的整數");
    }

    public GuessNumberGame(InputStream in) {
        super(in);
    }

    @Override public String inputPrefix() {
        return String.format("請猜一個%d-%d的數字(或輸入q or Q結束):", min, max);
    }

    @Override public String invalidInputMessage() {
        return "輸入格式不正確! 請重新輸入";
    }

    @Override public void run(LooperControl control, Integer value, Object... params) {
        if (++count >= maxCount) {
            System.out.println("太弱了吧!!!! 遊戲結束!!!!!");
            control.stopLoop();
            return;
        }
        int compare = Integer.compare(value, pwd);
        if (compare != 0) {
            System.out.println(String.format("請猜%s一點", compare > 0 ? "小" : "大"));
            return;
        }
        System.out.println(String.format("猜中了!!!!! 你花了%d次", count));
        count = 0;
    }

    @Override public Integer convertInput(String input) {
        return Integer.parseInt(input);
    }
}
