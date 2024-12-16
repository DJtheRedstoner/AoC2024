package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

public class Day16 implements DayBase {

    public void init(List<String> lines) {
        
    }

    
    record Pos(int x, int y) {
        Pos plus(Pos p) {
            return new Pos(x() + p.x(), y() + p.y());
        }
    }
    
    public void part1(List<String> lines) {
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char c = lines.get(y).charAt(x);
                if (c == 'S') {
                    start = new Pos(x, y);
                } else if (c == 'E') {
                    end = new Pos(x, y);
                }
            }
        }
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        record K(Pos p, Pos dir) {}
        Map<K, Integer> minScores = new HashMap<>();
        record E(Pos p, Pos dir, int score) {}

        var Q = new PriorityQueue<E>(Comparator.comparingInt(E::score));
        Q.add(new E(start, new Pos(1, 0), 0));

        List<Pos> dirs = List.of(new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1));

        while (!Q.isEmpty()) {
            E current = Q.remove();
            if (current.p().equals(end)) {
                System.out.println(current.score());
                return;
            }
            K k = new K(current.p(), current.dir());
            if (minScores.containsKey(k) && minScores.get(k) < current.score()) {
                continue;
            } else {
                minScores.put(k, current.score());
            }
            {
                Pos next = current.p().plus(current.dir());
                if (lines.get(next.y()).charAt(next.x()) != '#') {
                    Q.add(new E(next, current.dir(), current.score() + 1));
                }
            }
            int c = dirs.indexOf(current.dir());
            int c1 = (c+1)%4;
            Q.add(new E(current.p(), dirs.get(c1), current.score() + 1000));
            int c2 = (c-1);
            if (c2 < 0) c2+=4;
            Q.add(new E(current.p(), dirs.get(c2), current.score() + 1000));
        }
    }

    public void part2(List<String> lines) {
        Pos start = null;
        Pos end = null;
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                char c = lines.get(y).charAt(x);
                if (c == 'S') {
                    start = new Pos(x, y);
                } else if (c == 'E') {
                    end = new Pos(x, y);
                }
            }
        }
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);

        record K(Pos p, Pos dir) {}
        Map<K, Integer> minScores = new HashMap<>();
        record E(Pos p, Pos dir, int score) {}

        Map<E, Set<E>> parents = new HashMap<>();

        var Q = new PriorityQueue<E>(Comparator.comparingInt(E::score));
        Q.add(new E(start, new Pos(1, 0), 0));

        List<Pos> dirs = List.of(new Pos(1, 0), new Pos(0, 1), new Pos(-1, 0), new Pos(0, -1));

        Set<E> finals = new HashSet<>();

        while (!Q.isEmpty()) {
            E current = Q.remove();
            if (current.p().equals(end)) {
                finals.add(current);
                continue;
            }
            K k = new K(current.p(), current.dir());
            if (minScores.containsKey(k) && minScores.get(k) < current.score()) {
                continue;
            } else {
                minScores.put(k, current.score());
            }
            {
                Pos next = current.p().plus(current.dir());
                if (lines.get(next.y()).charAt(next.x()) != '#') {
                    E n = new E(next, current.dir(), current.score() + 1);
                    parents.computeIfAbsent(n, __ -> new HashSet<>()).add(current);
                    Q.add(n);
                }
            }
            int c = dirs.indexOf(current.dir());
            int c1 = (c+1)%4;
            E n1 = new E(current.p(), dirs.get(c1), current.score() + 1000);
            parents.computeIfAbsent(n1, __ -> new HashSet<>()).add(current);
            Q.add(n1);
            int c2 = (c-1);
            if (c2 < 0) c2+=4;
            E n2 = new E(current.p(), dirs.get(c2), current.score() + 1000);
            parents.computeIfAbsent(n2, __ -> new HashSet<>()).add(current);
            Q.add(n2);
        }

        Set<Pos> onBest = new HashSet<>();

        var finalsL = new ArrayList<>(finals);
        finalsL.sort(Comparator.comparingInt(E::score));
        int bestScore = finalsL.getFirst().score();

        for (E e : finals) {
            if (e.score() != bestScore) continue;
            var q = new ArrayList<E>();
            q.add(e);
            while (!q.isEmpty()) {
                E o = q.removeFirst();
                onBest.add(o.p());
                if (parents.containsKey(o))
                    q.addAll(parents.get(o));
            }
        }
        System.out.println(onBest.size());
    }

    public static void main(String...args) {
        new Day16().test(1);
        new Day16().test(2);
        new Day16().run();
    }
}
