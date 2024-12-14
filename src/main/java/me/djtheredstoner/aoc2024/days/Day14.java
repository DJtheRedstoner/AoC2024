package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Day14 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
//        int width = 11; // 101
        int width = 101;
//        int height = 7; // 103
        int height = 103;

        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;

        Pattern p = Pattern.compile("p=([-\\d]+),([-\\d]+) v=([-\\d]+),([-\\d]+)");
        for (String line : lines) {
            var m = p.matcher(line);
            m.find();
            int pX = Integer.parseInt(m.group(1));
            int pY = Integer.parseInt(m.group(2));
            int vX = Integer.parseInt(m.group(3));
            int vY = Integer.parseInt(m.group(4));

            for (int i = 0; i < 100; i++) {
                pX = (pX + vX) % width;
                if (pX < 0) pX += width;
                pY = (pY + vY) % height;
                if (pY < 0) pY += height;
            }

            if (pX < width/2 && pY < height/2) {
                q1++;
            } else if (pX > width/2 && pY < height/2) {
                q2++;
            } else if (pX < width/2 && pY > height/2) {
                q3++;
            } else if (pX > width / 2 && pY > height/2){
                q4++;
            }
        }
        System.out.println(q1 * q2 * q3 * q4);
    }

    public void part2(List<String> lines) {
        //        int width = 11; // 101
        int width = 101;
        //        int height = 7; // 103
        int height = 103;

        record Robot(int pX, int pY, int vX, int vY) {}
        record Pos(int x, int y) {}

        List<Robot> robots = new ArrayList<>();

        Pattern pattern = Pattern.compile("p=([-\\d]+),([-\\d]+) v=([-\\d]+),([-\\d]+)");
        for (String line : lines) {
            var m = pattern.matcher(line);
            m.find();
            int pX = Integer.parseInt(m.group(1));
            int pY = Integer.parseInt(m.group(2));
            int vX = Integer.parseInt(m.group(3));
            int vY = Integer.parseInt(m.group(4));

            robots.add(new Robot(pX, pY, vX, vY));
        }

        long time = 0;
        outer:
        while (true) {
            time++;
            HashSet<Pos> poses = new HashSet<>();
            for (int i = 0; i < robots.size(); i++) {
                var r = robots.get(i);
                int pX = (r.pX() + r.vX()) % width;
                if (pX < 0) pX += width;
                int pY = (r.pY() + r.vY()) % height;
                if (pY < 0) pY += height;
                robots.set(i, new Robot(pX, pY, r.vX(), r.vY()));
                poses.add(new Pos(pX, pY));
            }

            for (Pos pos : poses) {
                var visited = new HashSet<Pos>();
                var queue = new ArrayDeque<Pos>();
                queue.add(pos);
                while (!queue.isEmpty()) {
                    Pos p = queue.remove();
                    for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                        Pos np = new Pos(p.x() + delta.x(), p.y() + delta.y());
                        if (poses.contains(np) && !visited.contains(np)) {
                            visited.add(np);
                            queue.add(np);
                        }
                    }
                }
                if (visited.size() > 20) {
                    System.out.println(time);
                    break outer;
                }
            }
        }

//        try (var ps = new PrintStream(new BufferedOutputStream(new FileOutputStream("out.txt")))) {
//            long itercount = 0;
//            while (itercount < 10000) {
//                itercount++;
//                HashSet<Pos> poses = new HashSet<>();
//                for (int i = 0; i < robots.size(); i++) {
//                    var r = robots.get(i);
//                    int pX = (r.pX() + r.vX()) % width;
//                    if (pX < 0) pX += width;
//                    int pY = (r.pY() + r.vY()) % height;
//                    if (pY < 0) pY += height;
//                    robots.set(i, new Robot(pX, pY, r.vX(), r.vY()));
//                    poses.add(new Pos(pX, pY));
//                }
//
//                ps.println(itercount);
//                for (int y = 0; y < height; y++) {
//                    for (int x = 0; x < width; x++) {
//                        if (poses.contains(new Pos(x, y))) {
//                            ps.print("#");
//                        } else {
//                            ps.print(".");
//                        }
//                    }
//                    ps.println();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void main(String...args) {
        new Day14().test(1);
//        new Day14().test(2);
        new Day14().run();
    }
}
