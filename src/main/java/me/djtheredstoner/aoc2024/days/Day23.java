package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day23 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        var connections = new HashMap<String, List<String>>();
        for (String line : lines) {
            var p = line.split("-");
            connections.computeIfAbsent(p[0], __ -> new ArrayList<>()).add(p[1]);
            connections.computeIfAbsent(p[1], __ -> new ArrayList<>()).add(p[0]);
        }

        var trios = new HashSet<Set<String>>();

        for (String s : connections.keySet()) {
            for (String conn1 : connections.get(s)) {
                for (String conn2 : connections.get(conn1)) {
                    if (!conn2.equals(s) && connections.get(s).contains(conn2)) {
                        trios.add(Set.of(s, conn1, conn2));
                    }
                }
            }
        }

        int t = 0;
        for (Set<String> trio : trios) {
            for (String s : trio) {
                if (s.startsWith("t")) {
                    t++;
                    break;
                }
            }
        }
        System.out.println(t);
    }

    public void part2(List<String> lines) {
        var connections = new HashMap<String, List<String>>();
        for (String line : lines) {
            var p = line.split("-");
            connections.computeIfAbsent(p[0], __ -> new ArrayList<>()).add(p[1]);
            connections.computeIfAbsent(p[1], __ -> new ArrayList<>()).add(p[0]);
        }


        Set<String> largestNet = Set.of();

        for (String s : connections.keySet()) {
            var net = new HashSet<String>();
            net.add(s);
            var visited = new HashSet<>();
            var Q = new ArrayDeque<>(connections.get(s));
            while (!Q.isEmpty()) {
                String string = Q.removeFirst();
                if (visited.contains(string)) continue;
                visited.add(string);
                if (net.contains(string)) continue;
                boolean good = true;
                for (String inNet : net) {
                    if (!connections.get(string).contains(inNet)) {
                        good = false;
                        break;
                    }
                }
                if (good) {
                    net.add(string);
                }
                Q.addAll(connections.get(string));
            }

            if (net.size() > largestNet.size()) largestNet = net;
        }

        System.out.println(largestNet.stream().sorted().collect(Collectors.joining(",")));
    }

    public static void main(String...args) {
        new Day23().test(1);
        new Day23().test(2);
        new Day23().run();
    }
}
