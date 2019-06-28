package org.arxing;

import java.io.InputStream;
import java.util.Random;

//猜拳遊戲
public class GuessBoneGame extends ScanLooper<Integer> {
    private final static int CLIPPER = 1;
    private final static int STONE = 2;
    private final static int CLOTH = 3;

    private final static String NAME_CLIPPER = "剪刀";
    private final static String NAME_STONE = "石頭";
    private final static String NAME_CLOTH = "布";

    private Random computer = new Random();

    {
        addExitCondition(s -> s.matches("[qQ]"));
        addInvalidCondition(s -> !s.matches("(剪刀)|(石頭)|(布)"), "只能出(剪刀, 石頭, 布)啦 不懂規則喔");
    }

    public GuessBoneGame(InputStream in) {
        super(in);
    }

    @Override public String inputPrefix() {
        return "請出拳: ";
    }

    @Override public String invalidInputMessage() {
        return "只能出(剪刀, 石頭, 布)";
    }

    @Override public void run(LooperControl control, Integer value, Object... params) {
        int computerValue = computer.nextInt(3) + 1;
        int del = value - computerValue;
        int compare = value == computerValue ? 0 : (del == -1 || del == 2) ? -1 : 1;
        String result = compare > 0 ? "你贏了" : compare < 0 ? "你輸了" : "平手";
        System.out.println(String.format("===> 你出了%s, 電腦出了%s, 結果為 %s", valueToName(value), valueToName(computerValue), result));
    }

    @Override public Integer convertInput(String input) {
        switch (input) {
            case NAME_CLIPPER:
                return CLIPPER;
            case NAME_STONE:
                return STONE;
            case NAME_CLOTH:
                return CLOTH;
        }
        throw new Error("轉換錯誤: " + input);
    }

    private String valueToName(int value) {
        switch (value) {
            case CLIPPER:
                return NAME_CLIPPER;
            case STONE:
                return NAME_STONE;
            case CLOTH:
                return NAME_CLOTH;
        }
        throw new Error("轉換錯誤: " + value);
    }
}
