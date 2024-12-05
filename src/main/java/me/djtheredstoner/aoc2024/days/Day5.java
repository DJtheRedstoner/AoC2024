package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        int s = 0;
        int s2 = 0;
        Map<Integer, List<Integer>> before = new HashMap<>();
        Map<Integer, List<Integer>> after = new HashMap<>();
        for (String line : lines) {
            if (line.isBlank()) continue;
            if (line.contains("|")) {
                String[] parts = line.split("\\|");
                int left = Integer.parseInt(parts[0]);
                int right = Integer.parseInt(parts[1]);
                after.computeIfAbsent(left, __ -> new ArrayList<>()).add(right);
                before.computeIfAbsent(right, __ -> new ArrayList<>()).add(left);
            } else {
                List<Integer> pages = new ArrayList<>(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
                boolean good = true;
                for (int i = 0; i < pages.size(); i++) {
                    int page = pages.get(i);
                    List<Integer> pagesBefore = pages.subList(0, i);
                    List<Integer> pagesAfter = pages.subList(i+1, pages.size());
                    if (before.containsKey(page)) {
                        for (int p : before.get(page)) {
                            if (pagesAfter.contains(p)) {
                                good = false;
                                break;
                            }
                        }
                    }
                    if (after.containsKey(page)) {
                        for (int p : after.get(page)) {
                            if (pagesBefore.contains(p)) {
                                good = false;
                                break;
                            }
                        }
                    }
                }
                if (good) {
                    s += pages.get(pages.size() / 2);
                } else {
                    List<Integer> newPages = new ArrayList<>();
                    newPages.add(pages.removeFirst());
                    while (!pages.isEmpty()) {
                        int page = pages.removeFirst();
                        boolean added = false;
                        for (int i = 0; i < newPages.size(); i++) {
                            int p = newPages.get(i);
                            if (after.containsKey(page) && after.get(page).contains(p)) {
                                newPages.add(i, page);
                                added = true;
                                break;
                            }
                        }
                        if (!added) {
                            newPages.add(page);
                        }
                    }
                    s2 += newPages.get(newPages.size()/2);
                }
            }
        }
        System.out.println(s);
        System.out.println(s2);
    }

    public void part2(List<String> lines) {

    }

    public static void main(String...args) {
        new Day5().test(1);
        new Day5().test(2);
        new Day5().run();
    }
}
