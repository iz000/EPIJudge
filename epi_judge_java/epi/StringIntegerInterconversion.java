package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;

public class StringIntegerInterconversion {

    public static String intToString(int x) {
        StringBuilder sb = new StringBuilder();
        String sign = x < 0 ? "-" : "";
        do {
            sb.append((char) ('0' + Math.abs(x % 10)));
            x /= 10;
        } while (x != 0);
        sb.append(sign);
        return sb.reverse().toString();
    }

    public static int stringToInt(String s) {
        int num = 0;
        int sign = 1;
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                num *= 10;
                num += (c - '0');
            } else if (c == '-') {
                sign = -1;
            }
        }
        return sign * num;
    }

    @EpiTest(testDataFile = "string_integer_interconversion.tsv")
    public static void wrapper(int x, String s) throws TestFailure {
        if (Integer.parseInt(intToString(x)) != x) {
            throw new TestFailure("Int to string conversion failed");
        }
        if (stringToInt(s) != x) {
            throw new TestFailure("String to int conversion failed");
        }
    }

    public static void main(String[] args) {
        System.exit(
                GenericTest
                        .runFromAnnotations(args, "StringIntegerInterconversion.java",
                                new Object() {
                                }.getClass().getEnclosingClass())
                        .ordinal());
    }
}
