package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.List;

public class Day4 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {
        Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
        }

        boolean valid(List<String> lines) {
            return x() >= 0 && y() >= 0 && x() < lines.getFirst().length() && y() < lines.size();
        }

        char get(List<String> lines) {
            return lines.get(y()).charAt(x());
        }
    }

    public int search(int x, int y, List<String> lines) {
        int c = 0;
        Pos start = new Pos(x, y);
        for (Pos offset : new Pos[]{new Pos(-1, -1), new Pos(-1, 0), new Pos(1, 0), new Pos(0, 1), new Pos(0, -1), new Pos(1, 1), new Pos(-1, 1), new Pos(1, -1)}) {
            Pos m = start.plus(offset);
            Pos a = m.plus(offset);
            Pos s = a.plus(offset);
            if (m.valid(lines) && a.valid(lines) && s.valid(lines) && m.get(lines) == 'M' && a.get(lines) == 'A' && s.get(lines) == 'S') {
                c++;
            }
        }
        return c;
    }

    public void part1(List<String> lines) {
        int c = 0;
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'X') {
                    c += search(i, y, lines);
                }
            }
        }
        System.out.println(c);
    }

    public void part2(List<String> lines) {
        int c = 0;
        for (int y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            outer:
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == 'A') {
                    Pos start = new Pos(i, y);
                    for (Pos offset : new Pos[]{new Pos(-1, 1), new Pos(1, 1), new Pos(1, -1), new Pos(-1, -1)}) {
                        Pos p = start.plus(offset);
                        if (!p.valid(lines)) continue outer;
                    }
                    if ((start.plus(new Pos(-1, -1)).get(lines) == 'M' && start.plus(new Pos(1, 1)).get(lines) == 'S' || start.plus(new Pos(-1, -1)).get(lines) == 'S' && start.plus(new Pos(1, 1)).get(lines) == 'M')
                        && (start.plus(new Pos(-1, 1)).get(lines) == 'M' && start.plus(new Pos(1, -1)).get(lines) == 'S' || start.plus(new Pos(-1, 1)).get(lines) == 'S' && start.plus(new Pos(1, -1)).get(lines) == 'M')) {
                        c++;
                    }
                }
            }
        }
        System.out.println(c);
    }

    public static void main(String...args) {
        new Day4().test(1);
        new Day4().test(2);
        new Day4().run();
    }
}
