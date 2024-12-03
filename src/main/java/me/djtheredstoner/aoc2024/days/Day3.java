package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day3 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        int sum = 0;
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        for (String line : lines) {
            var m = p.matcher(line);
            while (m.find()) {
                sum += Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
            }
        }
        System.out.println(sum);
    }

    public void part2(List<String> lines) {
        int sum = 0;
        Pattern p = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        String s = String.join("", lines);
        var m = p.matcher(s);
        while (m.find()) {
            int doi = s.substring(0, m.start()).lastIndexOf("do()");
            int dont = s.substring(0, m.start()).lastIndexOf("don't()");
            if ((doi == -1 && dont == -1) || doi > dont)
                sum += Integer.parseInt(m.group(1)) * Integer.parseInt(m.group(2));
        }
        System.out.println(sum);
    }

    public static void main(String...args) {
        new Day3().test(1);
        new Day3().test(2);
        new Day3().run();
    }
}
