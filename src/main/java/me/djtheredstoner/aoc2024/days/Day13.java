package me.djtheredstoner.aoc2024.days;

import me.djtheredstoner.aoc2024.DayBase;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 implements DayBase {

    public void init(List<String> lines) {
        
    }

    public void part1(List<String> lines) {
        String inp = String.join("\n", lines);
        Pattern p = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)\nButton B: X\\+(\\d+), Y\\+(\\d+)\nPrize: X=(\\d+), Y=(\\d+)");
        Matcher matcher = p.matcher(inp);

        int tot = 0;

        while (matcher.find()) {
            int aX = Integer.parseInt(matcher.group(1));
            int aY = Integer.parseInt(matcher.group(2));
            int bX = Integer.parseInt(matcher.group(3));
            int bY = Integer.parseInt(matcher.group(4));
            int pX = Integer.parseInt(matcher.group(5));
            int pY = Integer.parseInt(matcher.group(6));

            int minCost = -1;

            for (int a = 0; a < 100; a++) {
                for (int b = 0; b < 100; b++) {
                    int posX = aX * a + bX * b;
                    int posY = aY * a + bY * b;
                    if (posX == pX && posY == pY) {
                        int cost = 3 * a + b;
                        if (minCost == -1 || cost < minCost) {
                            minCost = cost;
                        }
                    }
                }
            }

            if (minCost != -1) {
                tot+=minCost;
            }
        }
        System.out.println(tot);
    }

    public long gcd(long a, long b) { return b==0 ? a : gcd(b, a%b); }

    public void part2(List<String> lines) {
        String inp = String.join("\n", lines);
        Pattern p = Pattern.compile("Button A: X\\+(\\d+), Y\\+(\\d+)\nButton B: X\\+(\\d+), Y\\+(\\d+)\nPrize: X=(\\d+), Y=(\\d+)");
        Matcher matcher = p.matcher(inp);

        long tot = 0;

        while (matcher.find()) {
            long aX = Integer.parseInt(matcher.group(1));
            long aY = Integer.parseInt(matcher.group(2));
            long bX = Integer.parseInt(matcher.group(3));
            long bY = Integer.parseInt(matcher.group(4));
            long pX = 10000000000000L + Integer.parseInt(matcher.group(5));
            long pY = 10000000000000L + Integer.parseInt(matcher.group(6));

            // hope you've studied your linalg
            long[] mtx = {aX, bX, pX, aY, bY, pY};

            mtx[0] *= aY;
            mtx[1] *= aY;
            mtx[2] *= aY;
            mtx[3] *= aX;
            mtx[4] *= aX;
            mtx[5] *= aX;

            mtx[3] -= mtx[0];
            mtx[4] -= mtx[1];
            mtx[5] -= mtx[2];

            if (mtx[5] % mtx[4] != 0) continue;
            long b = mtx[5] / mtx[4];
            long pY2 = mtx[2] - (mtx[1] * b);
            if (pY2 % mtx[0] != 0) continue;
            long a = pY2 / mtx[0];
            tot += 3*a+b;
        }
        System.out.println(tot);
    }

    public static void main(String...args) {
        new Day13().test(1);
        new Day13().test(2);
        new Day13().run();
    }
}
