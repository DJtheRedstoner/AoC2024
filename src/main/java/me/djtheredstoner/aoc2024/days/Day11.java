package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        List<Long> stones = Arrays.stream(lines.get(0).split(" ")).map(Long::parseLong).toList();
        for (int i = 0; i < 25; i++) {
            var newStones = new ArrayList<Long>();
            for (Long stone : stones) {
                String s = Long.toString(stone);
                if (stone == 0) {
                    newStones.add(1L);
                } else if (s.length() % 2 == 0) {
                    newStones.add(Long.parseLong(s.substring(0, s.length()/2)));
                    newStones.add(Long.parseLong(s.substring(s.length()/2)));
                } else {
                    newStones.add(stone * 2024);
                }
            }
            stones = newStones;
        }
        System.out.println(stones.size());
    }

    record Key(long stone, int depth) {}

    private final Map<Key, Long> cache = new HashMap<>();

    public long split(long stone, int depth) {
        Key k = new Key(stone, depth);
        if (cache.containsKey(k)) return cache.get(k);
        if (depth == 0) return 1;
        long result;
        if (stone == 0) {
            result = split(1, depth-1);
        } else {
            String s = Long.toString(stone);
            if (s.length() % 2 == 0) {
                result = split(Long.parseLong(s.substring(0, s.length()/2)), depth-1) + split(Long.parseLong(s.substring(s.length()/2)),depth-1);
            } else {
                result = split(stone*2024,depth-1);
            }
        }
        cache.put(k, result);
        return result;
    }

    public void part2(List<String> lines) {
        List<Long> stones = Arrays.stream(lines.get(0).split(" ")).map(Long::parseLong).toList();
        long s=0;
        for (Long stone : stones) {
            s += split(stone, 75);
        }
        System.out.println(s);
    }

    public static void main(String...args) {
        new Day11().test(1);
        new Day11().test(2);
        new Day11().run();
    }
}
