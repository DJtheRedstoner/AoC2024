package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day15 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {
        Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
        }
    }
    
    boolean move(Pos p, Pos delta, List<StringBuilder> grid) {
        Pos np = p.plus(delta);
        char old = grid.get(p.y()).charAt(p.x());
        char curr = grid.get(np.y()).charAt(np.x());
        switch (curr) {
            case '.' -> {}
            case '#' -> { return false; }
            case 'O' -> {
                if (!move(np, delta, grid)) return false;
            }
        }
        grid.get(np.y()).setCharAt(np.x(), old);
        grid.get(p.y()).setCharAt(p.x(), '.');
        return true;
    }
    
    public void part1(List<String> lines) {
        var grid = new ArrayList<StringBuilder>();
        boolean isGrid = true;
        String instructions = "";
        for (String line : lines) {
            if (line.isEmpty()) isGrid = false;
            if (isGrid) {
                grid.add(new StringBuilder(line));
            } else {
                instructions += line;
            }
        }

        Pos robot = null;
        for (int y = 0; y < grid.size(); y++) {
            var r = grid.get(y);
            for (int x = 0; x < r.length(); x++) {
                if (r.charAt(x) == '@') robot = new Pos(x, y);
            }
        }
        Objects.requireNonNull(robot);

        for (char c : instructions.toCharArray()) {
            Pos delta = switch (c) {
                case '>' -> new Pos(1, 0);
                case '<' -> new Pos(-1, 0);
                case '^' -> new Pos(0, -1);
                case 'v' -> new Pos(0, 1);
                default -> throw new IllegalStateException();
            };
            
            if (move(robot, delta, grid)) {
                robot = robot.plus(delta);
            }
        }

        int sum = 0;
        for (int y = 0; y < grid.size(); y++) {
            var r = grid.get(y);
            for (int x = 0; x < r.length(); x++) {
                if (r.charAt(x) == 'O') {
                    sum += y * 100 + x;
                }
            }
        }
        System.out.println(sum);
    }

    boolean move2(Pos p, Pos delta, List<StringBuilder> grid) {
        Pos np = p.plus(delta);
        char old = grid.get(p.y()).charAt(p.x());
        char curr = grid.get(np.y()).charAt(np.x());
        switch (curr) {
            case '.' -> {}
            case '#' -> { return false; }
            case '[' -> {
                if (delta.y() != 0) {
                    if (!move2(np, delta, grid) || !move2(np.plus(new Pos(1, 0)), delta, grid)) return false;
                } else {
                    if (!move2(np, delta, grid)) return false;
                }
            }
            case ']' -> {
                if (delta.y() != 0) {
                    if (!move2(np, delta, grid) || !move2(np.plus(new Pos(-1, 0)), delta, grid)) return false;
                } else {
                    if (!move2(np, delta, grid)) return false;
                }
            }
        }
        grid.get(np.y()).setCharAt(np.x(), old);
        grid.get(p.y()).setCharAt(p.x(), '.');
        return true;
    }

    public void part2(List<String> lines) {
        var grid = new ArrayList<StringBuilder>();
        boolean isGrid = true;
        String instructions = "";
        for (String line : lines) {
            if (line.isEmpty()) isGrid = false;
            if (isGrid) {
                var sb = new StringBuilder();
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '#' -> sb.append("##");
                        case 'O' -> sb.append("[]");
                        case '.' -> sb.append("..");
                        case '@' -> sb.append("@.");
                    }
                }
                grid.add(sb);
            } else {
                instructions += line;
            }
        }

        Pos robot = null;
        for (int y = 0; y < grid.size(); y++) {
            var r = grid.get(y);
            for (int x = 0; x < r.length(); x++) {
                if (r.charAt(x) == '@') robot = new Pos(x, y);
            }
        }
        Objects.requireNonNull(robot);

        for (char c : instructions.toCharArray()) {
            Pos delta = switch (c) {
                case '>' -> new Pos(1, 0);
                case '<' -> new Pos(-1, 0);
                case '^' -> new Pos(0, -1);
                case 'v' -> new Pos(0, 1);
                default -> throw new IllegalStateException();
            };

            var newGrid = new ArrayList<StringBuilder>();
            for (StringBuilder stringBuilder : grid) {
                newGrid.add(new StringBuilder(stringBuilder));
            }

            if (move2(robot, delta, newGrid)) {
                grid = newGrid;
                robot = robot.plus(delta);
            }
        }

        int sum = 0;
        for (int y = 0; y < grid.size(); y++) {
            var r = grid.get(y);
            for (int x = 0; x < r.length(); x++) {
                if (r.charAt(x) == '[') {
                    sum += y * 100 + x;
                }
            }
        }
        System.out.println(sum);
    }

    public static void main(String...args) {
        new Day15().test(1);
        new Day15().test(2);
        new Day15().run();
    }
}
