package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pair(char a, char b) {}

    private Map<Pair, List<String>> computePaths(String pad) {
        var paths = new HashMap<Pair, List<String>>();

        record Pos(int x, int y) {
            Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
            }

            boolean inRange(int width, int height) {
                return x() >= 0 && x() < width && y() >= 0 && y() < height;
            }
        }

        record E(Pos p, List<Pos> path) {}

        String[] grid = pad.split("\n");
        int width = grid[0].length();
        int height = grid.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                char c = grid[y].charAt(x);
                if (c == ' ') continue;
                paths.put(new Pair(c, c), List.of("A"));
                for (int j = 0; j < width; j++) {
                    for (int k = 0; k < height; k++) {
                        if (j == x && k == y) continue;
                        char c2 = grid[k].charAt(j);
                        if (c2 == ' ') continue;

                        Pos start = new Pos(x, y);
                        Pos end = new Pos(j, k);

                        var Q = new ArrayDeque<E>();
                        Q.add(new E(start, List.of(start)));

                        var cPaths = new ArrayList<String>();

                        while (!Q.isEmpty()) {
                            var e = Q.pop();
                            if (e.p().equals(end)) {
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < e.path().size()-1; i++) {
                                    Pos p1 = e.path().get(i);
                                    Pos p2 = e.path().get(i+1);
                                    if (p1.y() == p2.y()) {
                                        sb.append(p1.x() < p2.x() ? ">" : "<");
                                    } else {
                                        sb.append(p1.y() < p2.y() ? "v" : "^");
                                    }
                                }
                                sb.append('A');
                                cPaths.add(sb.toString());
                                continue;
                            }
                            for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1)}) {
                                Pos np = e.p().plus(delta);
                                if (e.path().contains(np)) continue;
                                if (!np.inRange(width, height)) continue;
                                if (grid[np.y()].charAt(np.x()) == ' ') continue;
                                var newPath = new ArrayList<>(e.path());
                                newPath.add(np);
                                Q.add(new E(np, newPath));
                            }
                        }

                        cPaths.sort(Comparator.comparingInt(String::length));
                        int shortest = cPaths.getFirst().length();

                        paths.put(new Pair(c, c2), cPaths.stream().filter(it -> it.length()==shortest).toList());
                    }
                }
            }
        }

        return paths;
    }

    private List<String> findSeq(String code, Map<Pair, List<String>> paths) {
        char key = 'A';
        var seqs = new ArrayList<StringBuilder>();;
        seqs.add(new StringBuilder());
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            var newSeqs = new ArrayList<StringBuilder>();
            for (StringBuilder seq : seqs) {
                for (String path : paths.get(new Pair(key, c))) {
                    var nsb = new StringBuilder(seq);
                    nsb.append(path);
                    newSeqs.add(nsb);
                }
            }
            key = c;
            seqs = newSeqs;
        }
        return seqs.stream().map(StringBuilder::toString).toList();
    }

    private List<String> filterShortest(List<String> l) {
        var list = new ArrayList<>(l);
        list.sort(Comparator.comparingInt(String::length));
        int shortest = list.getFirst().length();
        return list.stream().filter(it -> it.length()==shortest).toList();
    }

    public void part1(List<String> lines) {
        String numPad = """
            789
            456
            123
             0A""";

        var numPaths = computePaths(numPad);

        String dirPad = """
             ^A
            <v>""";

        var dirPaths = computePaths(dirPad);

        long s = 0;

        var costs = calcCosts(dirPaths, 2);

        for (String code : lines) {
            var seqs = findSeq(code, numPaths);

            long minCost = Long.MAX_VALUE;
            for (String seq : seqs) {
                char key = 'A';
                long cost = 0;
                for (int i = 0; i < seq.length(); i++) {
                    char c = seq.charAt(i);
                    cost += costs.get(new Pair(key, c));
                    key = c;
                }
                if (cost < minCost) {
                    minCost = cost;
                }
            }
            if (minCost == Long.MAX_VALUE) throw new RuntimeException();

            int iPart = Integer.parseInt(code.substring(0, 3));
            s += iPart * minCost;
        }

        System.out.println(s);
    }

    private Map<Pair, Long> calcCosts(Map<Pair, List<String>> dirPaths, int nRobots) {
        var minCosts = new HashMap<Pair, Long>();
        for (Map.Entry<Pair, List<String>> entry : dirPaths.entrySet()) {
            minCosts.put(entry.getKey(), (long)entry.getValue().stream().mapToInt(String::length).min().orElseThrow());
        }

        for (int robot = 0; robot < nRobots - 1; robot++) {
            var newMinCosts = new HashMap<Pair, Long>();
            for (Map.Entry<Pair, List<String>> entry : dirPaths.entrySet()) {
                long minCost = Long.MAX_VALUE;
                for (String path : entry.getValue()) {
                    var key = 'A';
                    long cost = 0;
                    for (int i = 0; i < path.length(); i++) {
                        char c = path.charAt(i);
                        cost += minCosts.get(new Pair(key, c));
                        key = c;
                    }
                    if (cost < minCost) {
                        minCost = cost;
                    }
                }
                if (minCost == Long.MAX_VALUE) throw new RuntimeException();
                newMinCosts.put(entry.getKey(), minCost);
            }
            minCosts = newMinCosts;
        }

        return minCosts;
    }

    public void part2(List<String> lines) {
        String numPad = """
            789
            456
            123
             0A""";

        var numPaths = computePaths(numPad);

        String dirPad = """
             ^A
            <v>""";

        var dirPaths = computePaths(dirPad);

        var costs = calcCosts(dirPaths, 25);

        long s = 0;
        for (String code : lines) {
            var seqs = findSeq(code, numPaths);

            long minCost = Long.MAX_VALUE;
            for (String seq : seqs) {
                char key = 'A';
                long cost = 0;
                for (int i = 0; i < seq.length(); i++) {
                    char c = seq.charAt(i);
                    cost += costs.get(new Pair(key, c));
                    key = c;
                }
                if (cost < minCost) {
                    minCost = cost;
                }
            }
            if (minCost == Long.MAX_VALUE) throw new RuntimeException();

            int iPart = Integer.parseInt(code.substring(0, 3));
            s += iPart * minCost;
        }
        System.out.println(s);
    }

    public static void main(String...args) {
        new Day21().test(1);
        new Day21().test(2);
        new Day21().run();
    }
}
