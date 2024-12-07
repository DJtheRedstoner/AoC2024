package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 implements DayBase {

    public void init(List<String> lines) {
        
    }

    boolean doesEqual(List<Long> thing, long sum) {
        if (thing.size() == 1) {
            return thing.getFirst() == sum;
        }
        long first = thing.getFirst();
        long second = thing.get(1);
        var added = new ArrayList<>(thing.subList(2,thing.size()));
        var multed = new ArrayList<>(thing.subList(2,thing.size()));
        added.addFirst(first+second);
        multed.addFirst(first*second);
        return doesEqual(added, sum) || doesEqual(multed, sum);
    }

    public void part1(List<String> lines) {
        long s = 0;
        for (String line : lines) {
            var parts = line.split(": ");
            long target = Long.parseLong(parts[0]);
            parts = parts[1].split(" ");
            List<Long> terms = Arrays.stream(parts).map(Long::parseLong).toList();
            if (doesEqual(terms, target)) s+=target;
        }
        System.out.println(s);
    }

    boolean doesEqual2(List<Long> thing, long sum) {
        if (thing.size() == 1) {
            return thing.getFirst() == sum;
        }
        long first = thing.getFirst();
        long second = thing.get(1);
        var added = new ArrayList<>(thing.subList(2,thing.size()));
        var multed = new ArrayList<>(thing.subList(2,thing.size()));
        var concated = new ArrayList<>(thing.subList(2,thing.size()));
        added.addFirst(first+second);
        multed.addFirst(first*second);
        concated.addFirst(Long.parseLong(Long.toString(first)+ second));
        return doesEqual2(added, sum) || doesEqual2(multed, sum) || doesEqual2(concated, sum);
    }


    public void part2(List<String> lines) {
        long s = 0;
        for (String line : lines) {
            var parts = line.split(": ");
            long target = Long.parseLong(parts[0]);
            parts = parts[1].split(" ");
            List<Long> terms = Arrays.stream(parts).map(Long::parseLong).toList();
            if (doesEqual2(terms, target)) s+=target;
        }
        System.out.println(s);
    }

    public static void main(String...args) {
        new Day7().test(1);
        new Day7().test(2);
        new Day7().run();
    }
}
