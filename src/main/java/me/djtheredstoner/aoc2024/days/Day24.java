package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day24 implements DayBase {

    public void init(List<String> lines) {
        
    }

    record Ins(String a, String b, String op) {
        @Override
        public String toString() {
            return a + " " + op + " " + b;
        }
    }

    private boolean getValue(String gate, Map<String, Ins> gates, Map<String, Boolean> states) {
        if (states.containsKey(gate)) return states.get(gate);

        var ins = gates.get(gate);
        var a = getValue(ins.a(), gates, states);
        var b = getValue(ins.b(), gates, states);
        var v = switch (ins.op()) {
             case "AND" -> a && b;
             case "OR" -> a || b;
             case "XOR" -> a ^ b;
            default -> throw new IllegalStateException("Unexpected value: " + ins.op());
        };
        states.put(gate, v);
        return v;
    }

    public void part1(List<String> lines) {
        Map<String, Boolean> states = new HashMap<>();
        Map<String, Ins> gates = new HashMap<>();
        for (String line : lines) {
            if (line.contains(": ")) {
                var s = line.split(": ");
                states.put(s[0], s[1].equals("1"));
            } else if (line.contains("->")) {
                var s = line.split(" -> ");
                var out = s[1];
                var ins = s[0].split(" ");
                var a = ins[0];
                var b = ins[2];
                gates.put(out, new Ins(a, b, ins[1]));
//                switch (ins[1]) {
//                    case "AND" -> states.put(out, a && b);
//                    case "OR" -> states.put(out, a || b);
//                    case "XOR" -> states.put(out, a ^ b);
//                }
            }
        }

        var out = new BitSet();

        for (String s : gates.keySet()) {
            if (s.startsWith("z")) {
                out.set(Integer.parseInt(s.substring(1)), getValue(s, gates, states));
            }
        }

        System.out.println(out.toLongArray()[0]);
    }

    private String z(int s) {
        return String.format("%02d", s);
    }

    public void part2(List<String> lines) {
        // yeah good luck lmao
        Map<String, Ins> adderGates = new LinkedHashMap<>();
        for (int i = 0; i < 45; i++) {
            if (i == 0) {
                // add
                adderGates.put("z" + z(i), new Ins("x" + z(i), "y" + z(i), "XOR"));
                // carry
                adderGates.put("carry" + z(i), new Ins("x" + z(i), "y" + z(i), "AND"));
            } else {
                // add
                adderGates.put("add" + z(i), new Ins("x" + z(i), "y" + z(i), "XOR"));
                // carry
                adderGates.put("carryt1_" + z(i), new Ins("x" + z(i), "y" + z(i), "AND"));
                adderGates.put("carryt2_" + z(i), new Ins("add" + z(i), "carry" + z(i-1), "AND"));
                adderGates.put("carry" + z(i), new Ins("carryt1_" + z(i), "carryt2_" + z(i), "OR"));
                // output
                adderGates.put("z" + z(i), new Ins("add" + z(i), "carry" + z(i-1), "XOR"));
            }
        }
        for (Map.Entry<String, Ins> entry : adderGates.entrySet()) {
            System.out.println(entry.getValue() + " -> " + entry.getKey());
        }
        System.out.println(adderGates.size());

        System.out.println("-------");

        Map<String, Ins> gates = new LinkedHashMap<>();
        for (String line : lines) {
            if (line.contains("->")) {
                var s = line.split(" -> ");
                var out = s[1];
                var ins = s[0].split(" ");
                var a = ins[0];
                var b = ins[2];
                gates.put(out, new Ins(a, b, ins[1]));
            }
        }

        for (Map.Entry<String, Ins> entry : gates.entrySet()) {
            var ins = entry.getValue();
            if (ins.b().startsWith("x")) {
                gates.put(entry.getKey(), new Ins(ins.b(), ins.a(), ins.op()));
            }
        }

        Map<String, String> renames = new HashMap<>();

        for (int i = 1; i < 45; i++) {
            var ins = gates.get("z" + z(i));
            if (ins.a().startsWith("x") || ins.b().startsWith("y")) continue;
            Ins carryIns;
            if (gates.get(ins.a()).op().equals("OR")) {
                renames.put(ins.a(), "carry" + z(i-1));
                carryIns = gates.get(ins.a());
                renames.put(ins.b(), "add" + z(i));
            } else {
                renames.put(ins.b(), "carry" + z(i-1));
                carryIns = gates.get(ins.b());
                renames.put(ins.a(), "add" + z(i));
            }
            if (carryIns.a().startsWith("x") || carryIns.b().startsWith("y")) continue;
            if (gates.get(carryIns.a()).a().startsWith("x")) {
                renames.put(carryIns.a(), "carryt1_" + z(i-1));
                renames.put(carryIns.b(), "carryt2_" + z(i-1));
            } else {
                renames.put(carryIns.b(), "carryt1_" + z(i-1));
                renames.put(carryIns.a(), "carryt2_" + z(i-1));
            }
        }

        System.out.println(renames);

        var list = new ArrayList<>(gates.entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.comparing(Ins::a)));

        for (Map.Entry<String, Ins> entry : list) {
            var ins = entry.getValue();
            var a = renames.getOrDefault(ins.a(), ins.a());
            var b = renames.getOrDefault(ins.b(), ins.b());
            if (b.startsWith("add") || b.startsWith("carryt1")) {
                var temp = b;
                b = a;
                a = temp;
            }
            System.out.println(a + " " + ins.op() + " " + b + " -> " + renames.getOrDefault(entry.getKey(), entry.getKey()));
        }
        System.out.println(gates.size());
    }

    public static void main(String...args) {
        new Day24().test(1);
//        new Day24().test(2);
        new Day24().run();
    }
}
