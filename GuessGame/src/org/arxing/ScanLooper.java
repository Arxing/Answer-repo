package org.arxing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import javafx.util.Pair;

public abstract class ScanLooper<TVal> implements LooperControl {
    //格式黑名單
    private List<Pair<Function<String, Boolean>, String>> invalidInputConditions = new ArrayList<>();
    private List<Function<String, Boolean>> exitConditions = new ArrayList<>();
    private Scanner scanner;
    private AtomicBoolean looping = new AtomicBoolean(true);

    public ScanLooper(InputStream in) {
        scanner = new Scanner(in);
    }

    public abstract String inputPrefix();

    public abstract String invalidInputMessage();

    public abstract void run(LooperControl control, TVal value, Object... params);

    public abstract TVal convertInput(String input);

    public void startLoop(Object... params) {
        while (looping.get()) {
            System.out.print(inputPrefix());
            String input = scanner.next();
            if (exitConditions.stream().anyMatch(o -> o.apply(input)))
                break;

            //輸入格式符合其中一個不合法的格式
            if (invalidInputConditions.stream().anyMatch(o -> o.getKey().apply(input))) {
                String msg = invalidInputConditions.stream()
                                                   .filter(o -> o.getKey().apply(input))
                                                   .map(o -> o.getValue())
                                                   .findFirst()
                                                   .orElse(invalidInputMessage());
                System.out.println(msg);
                //印出所有不合法的錯誤訊息並繼續下一輪迴圈
                continue;
            }
            run(this, convertInput(input), params);
        }
    }

    @Override public void stopLoop() {
        looping.set(false);
    }

    public void addInvalidCondition(Function<String, Boolean> condition, String format, Object... params) {
        invalidInputConditions.add(new Pair<>(condition, String.format(format, params)));
    }

    public void addExitCondition(Function<String, Boolean> condition) {
        exitConditions.add(condition);
    }
}
