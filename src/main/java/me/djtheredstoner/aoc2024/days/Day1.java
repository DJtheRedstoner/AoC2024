package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        int s = 0;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            left.add(Integer.parseInt(parts[0]));
            right.add(Integer.parseInt(parts[1]));
        }
        Collections.sort(left);
        Collections.sort(right);
        for (int i = 0; i < left.size(); i++) {
            s += Math.abs(left.get(i) - right.get(i));
        }
        System.out.println(s);
    }

    public void part2(List<String> lines) {
        int s = 0;
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split("\\s+");
            left.add(Integer.parseInt(parts[0]));
            right.add(Integer.parseInt(parts[1]));
        }
        for (int n : left) {
            s += n * (int)right.stream().filter(a -> a == n).count();
        }
        System.out.println(s);
    }

    public static void main(String...args) {
        new Day1().test(1);
        new Day1().test(2);
        new Day1().run();
    }
}
