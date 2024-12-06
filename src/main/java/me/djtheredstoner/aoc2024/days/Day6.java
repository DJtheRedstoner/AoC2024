package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day6 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {
        Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
        }
    }

    public void part1(List<String> lines) {
        List<Pos> directions = List.of(new Pos(0, -1), new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0));

        Set<Pos> obstacles = new HashSet<>();
        Pos start = null;
        for (int y = 0; y < lines.size(); y++) {
            String l = lines.get(y);
            for (int x = 0; x < l.length(); x++) {
                switch (l.charAt(x)) {
                    case '#' -> obstacles.add(new Pos(x, y));
                    case '^' -> start = new Pos(x, y);
                }
            }
        }
        Objects.requireNonNull(start);
        Set<Pos> visited = new HashSet<>();
        visited.add(start);
        int direction = 0;
        Pos current = start;
        while (true) {
            Pos next = current.plus(directions.get(direction));
            if (obstacles.contains(next)) {
                direction = (direction + 1) % 4;
                continue;
            }
            if (next.x() >= lines.get(0).length() || next.x() < 0 || next.y() < 0 || next.y() >= lines.size()) break;
            current = next;
            visited.add(current);
        }
        System.out.println(visited.size());
    }

    public void part2(List<String> lines) {
        List<Pos> directions = List.of(new Pos(0, -1), new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0));

        int width = lines.get(0).length();
        int height = lines.size();

        Set<Pos> obstacles = new HashSet<>();
        Pos start = null;
        for (int y = 0; y < lines.size(); y++) {
            String l = lines.get(y);
            for (int x = 0; x < l.length(); x++) {
                switch (l.charAt(x)) {
                    case '#' -> obstacles.add(new Pos(x, y));
                    case '^' -> start = new Pos(x, y);
                }
            }
        }
        Objects.requireNonNull(start);

        int c = 0;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (new Pos(x, y).equals(start)) continue;
                var newObstacles = new HashSet<>(obstacles);
                newObstacles.add(new Pos(x, y));
                if (doesLoop(newObstacles, start, width, height)) c++;
            }
        }
        System.out.println(c);
    }

    private boolean doesLoop(Set<Pos> obstacles, Pos start, int width, int height) {
        List<Pos> directions = List.of(new Pos(0, -1), new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0));

        record Visit(Pos p, int dir) {}

        Set<Visit> visited = new HashSet<>();

        int direction = 0;
        Pos current = start;
        while (true) {
            Pos next = current.plus(directions.get(direction));
            Visit v = new Visit(next, direction);
            if (obstacles.contains(next)) {
                direction = (direction + 1) % 4;
                continue;
            }
            if (next.x() >= width || next.x() < 0 || next.y() < 0 || next.y() >= height) return false;
            if (visited.contains(v)) return true;
            current = next;
            visited.add(v);
        }
    }

    public static void main(String...args) {
        new Day6().test(1);
        new Day6().test(2);
        new Day6().run();
    }
}
