package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Day20 implements DayBase {

    private final List<Pos> dirs = List.of(new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1));

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {
        Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
        }

        boolean inRange(int width, int height) {
            return x() >= 0 && x() < width && y() >= 0 && y() < height;
        }
    }


    public void part1(List<String> lines) {
        int width = lines.getFirst().length();
        int height = lines.size();
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char c = lines.get(y).charAt(x);
                if (c == 'S') {
                    start = new Pos(x, y);
                } else if (c == 'E') {
                    end = new Pos(x, y);
                }
            }
        }
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        Map<Pos, Integer> picos = new HashMap<>();
        var visited = new HashSet<Pos>();
        picos.put(start, 0);
        var Q = new ArrayDeque<Pos>();
        Q.add(start);

        while (!Q.isEmpty()) {
            Pos p = Q.pop();
            int pico = picos.get(p);
            if (p.equals(end)) {
                break;
            }
            if (visited.contains(p)) continue;
            visited.add(p);
            for (Pos delta : dirs) {
                Pos np = p.plus(delta);
                if (lines.get(np.y()).charAt(np.x()) == '#') continue;
                if (picos.containsKey(np) && picos.get(np) < pico + 1) continue;
                picos.put(np, pico + 1);
                Q.add(np);
            }
        }

        int goodCheats = 0;

        for (Pos pos : visited) {
            int currPicos = picos.get(pos);
            for (Pos delta1 : dirs) {
                Pos cheat1 = pos.plus(delta1);
                if (!cheat1.inRange(width, height)) continue;
                if (lines.get(cheat1.y()).charAt(cheat1.x()) == '#') {
                    for (Pos delta2 : dirs) {
                        Pos cheat2 = cheat1.plus(delta2);
                        if (cheat2.equals(pos)) continue;
                        if (!cheat2.inRange(width, height)) continue;
                        if (lines.get(cheat2.y()).charAt(cheat2.x()) != '#') {
                            if (picos.get(cheat2) - currPicos >= 101) {
                                goodCheats++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(goodCheats);
    }

    public void part2(List<String> lines) {
        int width = lines.getFirst().length();
        int height = lines.size();
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.getFirst().length(); x++) {
                char c = lines.get(y).charAt(x);
                if (c == 'S') {
                    start = new Pos(x, y);
                } else if (c == 'E') {
                    end = new Pos(x, y);
                }
            }
        }
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        Map<Pos, Integer> picos = new HashMap<>();
        var visited = new HashSet<Pos>();
        picos.put(start, 0);
        var Q = new ArrayDeque<Pos>();
        Q.add(start);

        List<Pos> dirs = List.of(new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1));

        while (!Q.isEmpty()) {
            Pos p = Q.pop();
            int pico = picos.get(p);
            if (p.equals(end)) {
                break;
            }
            if (visited.contains(p)) continue;
            visited.add(p);
            for (Pos delta : dirs) {
                Pos np = p.plus(delta);
                if (lines.get(np.y()).charAt(np.x()) == '#') continue;
                if (picos.containsKey(np) && picos.get(np) < pico + 1) continue;
                picos.put(np, pico + 1);
                Q.add(np);
            }
        }

        HashSet<Cheat> cheats = new HashSet<>();
        for (Pos cheatStart : visited) {
            for (int x = -20; x <= 20; x++) {
                int yDim = 20 - Math.abs(x);
                for (int y = -yDim; y <= yDim; y++) {
                    Pos p = cheatStart.plus(new Pos(x, y));
                    int dist = Math.abs(cheatStart.x() - p.x()) + Math.abs(cheatStart.y() - p.y());
                    if (!p.inRange(width, height)) continue;
                    if (lines.get(p.y()).charAt(p.x()) == '#') continue;
                    if (picos.get(p) - picos.get(cheatStart) >= (100+dist)) {
                        cheats.add(new Cheat(cheatStart, p));
                    }
                }
            }
        }

        System.out.println(cheats.size());
    }

    record Cheat(Pos start, Pos end) {}

    public static void main(String...args) {
        new Day20().test(1);
        new Day20().test(2);
        new Day20().run();
    }
}
