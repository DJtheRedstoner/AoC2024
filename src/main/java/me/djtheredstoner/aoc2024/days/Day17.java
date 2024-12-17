package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day17 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public long _2pow(long x) {
        long r = 1;
        for (long i = 0; i < x; i++) {
            r*=2;
        }
        return r;
    }

    public long getCombo(int operand, long a, long b, long c) {
        return switch (operand) {
            case 0 -> 0;
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> throw new IllegalStateException();
        };
    }

    public void part1(List<String> lines) {
        long a = Long.parseLong(lines.get(0).split(": ")[1]);
        long b = Long.parseLong(lines.get(1).split(": ")[1]);
        long c = Long.parseLong(lines.get(2).split(": ")[1]);

        var program = Arrays.stream(lines.get(4).split(": ")[1].split(",")).map(Integer::parseInt).toList();
        int rdi = 0;

        List<Long> output = new ArrayList<>();

        do {
            int instruction = program.get(rdi);
            int operand = program.get(rdi + 1);
            rdi += 2;
            switch (instruction) {
                case 0 -> { // adv
                    a = a / _2pow(getCombo(operand, a, b, c));
                }
                case 1 -> { // bxl
                    b ^= operand;
                }
                case 2 -> { // bst
                    b = getCombo(operand, a, b, c) % 8;
                }
                case 3 -> { // jnz
                    if (a != 0) {
                        rdi = operand;
                    }
                }
                case 4 -> { // bxc
                    b ^= c;
                }
                case 5 -> { // out
                    output.add(getCombo(operand, a, b, c) % 8);
                }
                case 6 -> { // bdv
                    b = a / _2pow(getCombo(operand, a, b, c));
                }
                case 7 -> { // cdv
                    c = a / _2pow(getCombo(operand, a, b, c));
                }
            }
        } while (rdi < program.size());

        System.out.println(output);
    }

    public void part2(List<String> lines) {
        long initB = Long.parseLong(lines.get(1).split(": ")[1]);
        long initC = Long.parseLong(lines.get(2).split(": ")[1]);

        var program = Arrays.stream(lines.get(4).split(": ")[1].split(",")).map(Integer::parseInt).toList();

        var queue = new ArrayList<Long>();
        queue.add(0L);

        for (int target : program.reversed()) {
            var nQ = new ArrayList<Long>();
            for (long x : queue) {
                for (int i = 0; i < 8; i++) {
                    long test = (x << 3) | i;
                    long a = test;
                    long b = initB;
                    long c = initC;

                    List<Integer> output = new ArrayList<>();

                    int rdi = 0;

                    do {
                        int instruction = program.get(rdi);
                        int operand = program.get(rdi + 1);
                        rdi += 2;
                        switch (instruction) {
                            case 0 -> { // adv
                                a = a / _2pow(getCombo(operand, a, b, c));
                            }
                            case 1 -> { // bxl
                                b ^= operand;
                            }
                            case 2 -> { // bst
                                b = getCombo(operand, a, b, c) % 8;
                            }
                            case 3 -> { // jnz
                                if (a != 0) {
                                    rdi = operand;
                                }
                            }
                            case 4 -> { // bxc
                                b ^= c;
                            }
                            case 5 -> { // out
                                output.add((int) (getCombo(operand, a, b, c) % 8L));
                            }
                            case 6 -> { // bdv
                                b = a / _2pow(getCombo(operand, a, b, c));
                            }
                            case 7 -> { // cdv
                                c = a / _2pow(getCombo(operand, a, b, c));
                            }
                        }
                    } while (rdi < program.size());

                    if (output.getFirst() == target) {
                        nQ.add(test);
                    }
                }
            }
            if (nQ.isEmpty()) {
                System.out.println(":((");
            }
            queue = nQ;
        }

        queue.sort(Long::compare);

        System.out.println(queue.getFirst());
    }

    public static void main(String...args) {
        new Day17().test(1);
        new Day17().test(2);
        new Day17().run();
    }
}
