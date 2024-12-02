package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day2 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        int safe = 0;
        for (String line : lines) {
            List<Integer> list = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            List<Integer> sorted = new ArrayList<>(list);
            Collections.sort(sorted);
            if (list.equals(sorted) || list.equals(sorted.reversed())) {
                boolean good = true;
                for (int i = 0; i < list.size() - 1; i++) {
                    int diff = Math.abs(list.get(i) - list.get(i+1));
                    if (diff < 1 || diff > 3) {
                        good = false;
                        break;
                    }
                }
                if (good) {
                    safe += 1;
                }
            }
        }
        System.out.println(safe);
    }

    private boolean isSafe(List<Integer> list) {
        List<Integer> sorted = new ArrayList<>(list);
        Collections.sort(sorted);
        if (list.equals(sorted) || list.equals(sorted.reversed())) {
            boolean good = true;
            for (int i = 0; i < list.size() - 1; i++) {
                int diff = Math.abs(list.get(i) - list.get(i+1));
                if (diff < 1 || diff > 3) {
                    good = false;
                    break;
                }
            }
            return good;
        }
        return false;
    }

    public void part2(List<String> lines) {
        int safe = 0;
        outer:
        for (String line : lines) {
            List<Integer> list = Arrays.stream(line.split(" ")).map(Integer::parseInt).toList();
            if (isSafe(list)) {
                safe += 1;
                continue;
            }
            for (int i = 0; i < list.size(); i++) {
                List<Integer> listP = new ArrayList<>(list);
                listP.remove(i);
                if (isSafe(listP)) {
                    safe += 1;
                    continue outer;
                }
            }
        }
        System.out.println(safe);
    }

    public static void main(String...args) {
        new Day2().test(1);
        new Day2().test(2);
        new Day2().run();
    }
}
