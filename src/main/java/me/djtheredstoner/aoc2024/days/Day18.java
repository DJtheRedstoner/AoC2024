package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day18 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {
        Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
        }
    }

    public void part1(List<String> lines) {
        boolean test = lines.size() < 30;
        int dim = test ? 6 : 70;
        int toFall = test ? 12 : 1024;

        Set<Pos> fallen = new HashSet<>();

        for (int i = 0; i < toFall; i++) {
            var parts = lines.get(i).split(",");
            fallen.add(new Pos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }

        var dist = new HashMap<Pos, Integer>();
        Set<Pos> visited = new HashSet<>();
        var Q = new ArrayDeque<Pos>();
        Pos init = new Pos(0, 0);
        Pos goal = new Pos(dim, dim);
        Q.add(init);
        dist.put(init, 0);

        while (!Q.isEmpty()) {
            Pos p = Q.pop();
            if (visited.contains(p)) continue;
            visited.add(p);
            int d = dist.get(p);

            if (p.equals(goal)) {
                System.out.println(d);
                return;
            }

            for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1)}) {
                Pos np = p.plus(delta);
                if (np.x() < 0 || np.x() > dim || np.y() < 0 || np.y() > dim || fallen.contains(np) || visited.contains(np)) continue;
                if (dist.containsKey(np) && dist.get(np) < d + 1) continue;
                dist.put(np, d + 1);
                Q.add(np);
            }
        }
    }

    public void part2(List<String> lines) {
        boolean test = lines.size() < 30;
        int dim = test ? 6 : 70;
        int toFall = test ? 12 : 1024;

        Set<Pos> fallen = new HashSet<>();

        for (int i = 0; i < toFall; i++) {
            var parts = lines.get(i).split(",");
            fallen.add(new Pos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
        }

        for (int i = toFall; i < lines.size(); i++) {
            var dist = new HashMap<Pos, Integer>();
            Set<Pos> visited = new HashSet<>();
            var Q = new ArrayDeque<Pos>();
            Pos init = new Pos(0, 0);
            Pos goal = new Pos(dim, dim);
            Q.add(init);
            dist.put(init, 0);

            var parts = lines.get(i).split(",");
            fallen.add(new Pos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
            boolean path = false;
            while (!Q.isEmpty()) {
                Pos p = Q.pop();
                if (visited.contains(p)) continue;
                visited.add(p);
                int d = dist.get(p);

                if (p.equals(goal)) {
                    path = true;
                    break;
                }

                for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1)}) {
                    Pos np = p.plus(delta);
                    if (np.x() < 0 || np.x() > dim || np.y() < 0 || np.y() > dim || fallen.contains(np) || visited.contains(np)) continue;
                    if (dist.containsKey(np) && dist.get(np) < d + 1) continue;
                    dist.put(np, d + 1);
                    Q.add(np);
                }
            }
            if (!path) {
                System.out.println(lines.get(i));
                break;
            }
        }
    }

    public static void main(String...args) {
        new Day18().test(1);
        new Day18().test(2);
        new Day18().run();
    }
}
