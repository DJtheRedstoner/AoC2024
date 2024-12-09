package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.List;

public class Day9 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        List<Integer> blocks = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < lines.get(0).length(); i++) {
            int n = Integer.parseInt(String.valueOf(lines.get(0).charAt(i)));
            if (i % 2 == 0) {
                for (int j = 0; j < n; j++) {
                    blocks.add(id);
                }
                id++;
            } else {
                for (int j = 0; j < n; j++) {
                    blocks.add(-1);
                }
            }
        }

        while (true) {
            int n = blocks.removeLast();
            if (n == -1) continue;
            boolean gapFound = false;
            for (int i = 0; i < blocks.size(); i++) {
                if (blocks.get(i) == -1) {
                    blocks.set(i, n);
                    gapFound = true;
                    break;
                }
            }
            if (!gapFound) {
                blocks.add(n);
                break;
            }
        }
        long sum = 0;
        for (int i = 0; i < blocks.size(); i++) {
            int n = blocks.get(i);
            if (n == -1) throw new RuntimeException();
            sum += (long)i * n;
        }
        System.out.println(sum);
    }

    public void part2(List<String> lines) {
        record Block(int id, int size) {}

        List<Block> blocks = new ArrayList<>();
        int id = 0;
        for (int i = 0; i < lines.get(0).length(); i++) {
            int n = Integer.parseInt(String.valueOf(lines.get(0).charAt(i)));
            if (i % 2 == 0) {
                blocks.add(new Block(id, n));
                id++;
            } else {
                blocks.add(new Block(-1, n));
            }
        }

        for (Block block : new ArrayList<>(blocks.reversed())) {
            int targetId = block.id();
            if (targetId == -1) continue;
            int idx = blocks.indexOf(block);
            for (int i = 0; i < idx; i++) {
                Block b = blocks.get(i);
                if (b.id() == -1 && b.size() >= block.size()) {
                    int newFree = b.size() - block.size();
                    blocks.set(idx, new Block(-1, block.size()));

                    blocks.set(i, block);
                    if (newFree > 0) {
                        blocks.add(i+1, new Block(-1, newFree));
                    }
                    break;
                }
            }
        }

        long sum = 0;
        long offset = 0;
        for (Block block : blocks) {
            if (block.id() == -1) {
                offset += block.size();
                continue;
            }
            for (long i = offset; i < offset+block.size(); i++) {
                sum += block.id() * i;
            }
            offset += block.size();
        }
        System.out.println(sum);
    }

    public static void main(String...args) {
        new Day9().test(1);
        new Day9().test(2);
        new Day9().run();
    }
}
