package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {
        boolean inBox(int width, int height) {
            return x() >= 0 && x() < width && y() >= 0 && y < height;
        }
    }

    record Antenna(int x, int y, char frequency) {}

    public void part1(List<String> lines) {
        int width = lines.get(0).length();
        int height = lines.size();

        List<Antenna> ants = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (lines.get(y).charAt(x) != '.') {
                    ants.add(new Antenna(x, y, lines.get(y).charAt(x)));
                }
            }
        }

        Set<Pos> ans = new HashSet<>();

        for (Antenna a1 : ants) {
            for (Antenna a2 : ants) {
                if (a1 == a2 || a1.frequency() != a2.frequency()) continue;
                int dx = a2.x() - a1.x();
                int dy = a2.y() - a1.y();
                Pos n1 = new Pos(a1.x() - dx, a1.y() - dy);
                if (n1.inBox(width, height)) ans.add(n1);
                Pos n2 = new Pos(a2.x() + dx, a2.y() + dy);
                if (n2.inBox(width, height)) ans.add(n2);
            }
        }

        System.out.println(ans.size());

//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (ans.contains(new Pos(x, y))) {
//                    System.out.print("#");
//                } else System.out.print(".");
//            }
//            System.out.println();
//        }
    }

    public int gcd(int a, int b) { return b==0 ? a : gcd(b, a%b); }

    public void part2(List<String> lines) {
        int width = lines.get(0).length();
        int height = lines.size();

        List<Antenna> ants = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (lines.get(y).charAt(x) != '.') {
                    ants.add(new Antenna(x, y, lines.get(y).charAt(x)));
                }
            }
        }

        Set<Pos> ans = new HashSet<>();

        for (Antenna a1 : ants) {
            for (Antenna a2 : ants) {
                if (a1 == a2 || a1.frequency() != a2.frequency()) continue;
                int dx = a2.x() - a1.x();
                int dy = a2.y() - a1.y();
                int d = gcd(dx, dy);
                dx /= d; dy /= d;
                Pos n1 = new Pos(a1.x() - dx, a1.y() - dy);
                while (n1.inBox(width, height)) {
                    ans.add(n1);
                    n1 = new Pos(n1.x() - dx, n1.y() - dy);
                }
                Pos n2 = new Pos(a2.x() + dx, a2.y() + dy);
                while (n2.inBox(width, height)) {
                    ans.add(n2);
                    n2 = new Pos(n2.x() + dx, n2.y() + dy);
                }
            }
        }

        System.out.println(ans.size());
    }

    public static void main(String...args) {
        new Day8().test(1);
        new Day8().test(2);
        new Day8().run();
    }
}
