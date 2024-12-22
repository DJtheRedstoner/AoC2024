package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.List;

public class Day22 implements DayBase {

    public void init(List<String> lines) {
        
    }

    class MonkeyRand {
        long seed;
        public MonkeyRand(long seed) {
            this.seed = seed;
        }

        public long next() {
            seed = (seed ^ (seed * 64)) % 16777216;
            seed = (seed ^ (seed / 32)) % 16777216;
            seed = (seed ^ (seed * 2048)) % 16777216;
            return seed;
        }
    }

    public void part1(List<String> lines) {
        long s = 0;
        for (String line : lines) {
            var rand = new MonkeyRand(Long.parseLong(line));
            for (int i = 0; i < 1999; i++) {
                rand.next();
            }
            s += rand.next();
        }
        System.out.println(s);
    }

    public void part2(List<String> lines) {
        List<long[]> monkeyPrices = new ArrayList<>();
        List<long[]> monkeyDeltas = new ArrayList<>();
        for (String line : lines) {
            long[] prices = new long[2001];
            long s = Long.parseLong(line);
            var rand = new MonkeyRand(s);
            prices[0] = s%10;
            for (int i = 0; i < 2000; i++) {
                prices[i+1] = rand.next() % 10;
            }
            monkeyPrices.add(prices);

            long curr = prices[0];
            var deltas = new long[2000];
            for (int i = 1; i < 2001; i++) {
                deltas[i-1] = prices[i]-curr;
                curr = prices[i];
            }
            monkeyDeltas.add(deltas);
        }

        long max = 0;

        for (int d1 = -9; d1 < 10; d1++) {
            for (int d2 = -9; d2 < 10; d2++) {
                for (int d3 = -9; d3 < 10; d3++) {
                    System.out.println(d3);
                    for (int d4 = -9; d4 < 10; d4++) {
                        long tot = 0;
                        for (int j = 0; j < monkeyPrices.size(); j++) {
                            long[] prices = monkeyPrices.get(j);
                            long[] deltas = monkeyDeltas.get(j);
                            for (int i = 3; i < deltas.length; i++) {
                                if (deltas[i-3] == d1 && deltas[i-2] == d2 && deltas[i-1] == d3 && deltas[i] == d4) {
                                    tot += prices[i+1];
                                    break;
                                }
                            }
                        }
                        if (tot > max) max = tot;
                    }
                }
            }
        }
        System.out.println(max);
    }

    public static void main(String...args) {
        new Day22().test(1);
        new Day22().test(2);
        new Day22().run();
    }
}
