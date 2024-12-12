package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day12 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {}
    
    public void part1(List<String> lines) {
        int cost = 0;
        Set<Pos> visited = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                var pos = new Pos(x, y);
                if (visited.contains(pos)) continue;
                visited.add(pos);
                char current = lines.get(y).charAt(x);
                int area = 1;
                int perimeter = 0;
                var queue = new ArrayDeque<Pos>();
                queue.add(pos);
                while (!queue.isEmpty()) {
                    Pos p = queue.remove();
                    for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                        Pos np = new Pos(p.x() + delta.x(), p.y() + delta.y());
                        if (np.x() < 0 || np.y() < 0 || np.x() >= lines.get(0).length() || np.y() >= lines.size()) {
                            perimeter += 1;
                            continue;
                        }
                        if (lines.get(np.y()).charAt(np.x()) != current) {
                            perimeter += 1;
                            continue;
                        }
                        if (visited.contains(np)) continue;
                        visited.add(np);
                        queue.add(np);
                        area +=1;
                    }
                }
                cost += area * perimeter;
            }
        }
        System.out.println(cost);
    }

    record Edge(Pos p, Pos dir) {}

    public void part2(List<String> lines) {
        int cost = 0;
        Set<Pos> visited = new HashSet<>();
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                var pos = new Pos(x, y);
                if (visited.contains(pos)) continue;
                visited.add(pos);
                char current = lines.get(y).charAt(x);
                int area = 1;
                var queue = new ArrayDeque<Pos>();
                queue.add(pos);
                Set<Edge> edges = new HashSet<>();
                while (!queue.isEmpty()) {
                    Pos p = queue.remove();
                    for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                        Pos np = new Pos(p.x() + delta.x(), p.y() + delta.y());
                        if (np.x() < 0 || np.y() < 0 || np.x() >= lines.get(0).length() || np.y() >= lines.size()) {
                            edges.add(new Edge(p, delta));
                            continue;
                        }
                        if (lines.get(np.y()).charAt(np.x()) != current) {
                            edges.add(new Edge(p, delta));
                            continue;
                        }
                        if (visited.contains(np)) continue;
                        visited.add(np);
                        queue.add(np);
                        area +=1;
                    }
                }

                int count = 0;

                // i'm so sorry...
                for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                    Set<Edge> seenedges = new HashSet<>();
                    for (Edge edge : edges) {
                        if (!edge.dir().equals(delta)) continue;
                        if (seenedges.contains(edge)) continue;
                        seenedges.add(edge);
                        var queue2 = new ArrayDeque<Edge>();
                        queue2.add(edge);
                        while (!queue2.isEmpty()) {
                            Edge e = queue2.remove();
                            for (Pos delta2 : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                                Pos np = new Pos(e.p().x() + delta2.x(), e.p().y() + delta2.y());
                                if (np.x() < 0 || np.y() < 0 || np.x() >= lines.get(0).length() || np.y() >= lines.size()) continue;
                                Edge newEdge = new Edge(np, delta);
                                if (edges.contains(newEdge) && !seenedges.contains(newEdge)) {
                                    seenedges.add(newEdge);
                                    queue2.add(newEdge);
                                }
                            }
                        }
                        count +=1;
                    }
                }

                cost += area * count;
                System.out.println(current);
                System.out.println(count);
            }
        }
        System.out.println(cost);
    }

    public static void main(String...args) {
        new Day12().test(1);
        new Day12().test(2);
        new Day12().run();
    }
}
