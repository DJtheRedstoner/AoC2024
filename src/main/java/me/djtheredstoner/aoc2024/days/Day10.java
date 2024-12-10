package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Day10 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Pos(int x, int y) {}

    public void part1(List<String> lines) {
        int s = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] charArray = line.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                char c = charArray[j];
                if (c == '0') {
                    Set<Pos> reachable = new HashSet<>();
                    Queue<Pos> queue = new ArrayDeque<>();
                    queue.add(new Pos(j, i));
                    while (!queue.isEmpty()) {
                        Pos p = queue.remove();
                        char currH = lines.get(p.y()).charAt(p.x());
                        for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                            Pos np = new Pos(p.x() + delta.x(), p.y() + delta.y());
                            if (np.x() < 0 || np.y() < 0 || np.x() >= lines.get(0).length() || np.y() >= lines.size()) continue;
                            char newH = lines.get(np.y()).charAt(np.x());
                            if (newH - currH == 1) {
                                if (newH == '9') {
                                    reachable.add(np);
                                } else {
                                    queue.add(np);
                                }
                            }
                        }
                    }
                    s+= reachable.size();
                }
            }
        }
        System.out.println(s);
    }

    public void part2(List<String> lines) {
        int s = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            char[] charArray = line.toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                char c = charArray[j];
                if (c == '0') {
                    Queue<Pos> queue = new ArrayDeque<>();
                    queue.add(new Pos(j, i));
                    while (!queue.isEmpty()) {
                        Pos p = queue.remove();
                        char currH = lines.get(p.y()).charAt(p.x());
                        for (Pos delta : new Pos[]{new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)}) {
                            Pos np = new Pos(p.x() + delta.x(), p.y() + delta.y());
                            if (np.x() < 0 || np.y() < 0 || np.x() >= lines.get(0).length() || np.y() >= lines.size()) continue;
                            char newH = lines.get(np.y()).charAt(np.x());
                            if (newH - currH == 1) {
                                if (newH == '9') {
                                    s++;
                                } else {
                                    queue.add(np);
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println(s);
    }

    public static void main(String...args) {
        new Day10().test(1);
        new Day10().test(2);
        new Day10().run();
    }
}
