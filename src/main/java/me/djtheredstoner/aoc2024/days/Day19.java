package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day19 implements DayBase {

    public void init(List<String> lines) {
        
    }

    private final Map<String, Boolean> cache1 = new HashMap<>();

    public boolean isPossible1(String design, Set<String> towels) {
        if (cache1.containsKey(design)) return cache1.get(design);
        if (design.isEmpty()) return true;
        for (String towel : towels) {
            if (design.startsWith(towel)) {
                if (isPossible1(design.substring(towel.length()), towels)) {
                    cache1.put(design, true);
                    return true;
                }
            }
        }
        cache1.put(design, false);
        return false;
    }

    public void part1(List<String> lines) {
        Set<String> towels = Set.of(lines.getFirst().split(", "));
        var designs = lines.subList(2, lines.size());

        int s = 0;

        for (String design : designs) {
            if (isPossible1(design, towels)) s++;
        }

        System.out.println(s);
    }

    private final Map<String, Long> cache2 = new HashMap<>();

    public long countPossible(String design, Set<String> towels) {
        if (cache2.containsKey(design)) return cache2.get(design);
        if (design.isEmpty()) return 1;
        long s = 0;
        for (String towel : towels) {
            if (design.startsWith(towel)) {
                s += countPossible(design.substring(towel.length()), towels);
            }
        }
        cache2.put(design, s);
        return s;
    }

    public void part2(List<String> lines) {
        Set<String> towels = Set.of(lines.getFirst().split(", "));
        var designs = lines.subList(2, lines.size());

        long s = 0;

        for (String design : designs) {
            s += countPossible(design, towels);
        }

        System.out.println(s);
    }

    public static void main(String...args) {
        new Day19().test(1);
        new Day19().test(2);
        new Day19().run();
    }
}
