package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.List;

public class Day25 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        List<int[]> keys = new ArrayList<>();
        List<int[]> locks = new ArrayList<>();
        for (String thing : String.join("\n", lines).split("\n\n")) {
            var l = List.of(thing.split("\n"));
            int[] k = new int[5];
            for (int i = 0; i < 5; i++) {
                for (String line : l.subList(1, l.size()-1)) {
                    if (line.charAt(i) == '#') k[i]++;
                }
            }
            if (l.getLast().equals("#####")) {
                keys.add(k);
            } else {
                locks.add(k);
            }
        }

        int g = 0;

        for (int[] key : keys) {
            for (int[] lock : locks) {
                boolean good = true;
                for (int i = 0; i < 5; i++) {
                    if (key[i] + lock[i] >= 6) {
                        good = false;
                        break;
                    }
                }
                if (good) {
                    g++;
                }
            }
        }

        System.out.println(g);
    }

    public void part2(List<String> lines) {

    }

    public static void main(String...args) {
        new Day25().test(1);
        new Day25().test(2);
        new Day25().run();
    }
}
