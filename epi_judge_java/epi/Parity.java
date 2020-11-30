package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;

public class Parity {

  @EpiTest(testDataFile = "parity.tsv")
  public static short parity(long x) {
    x ^= x >>> 32;
    x ^= x >>> 16;
    x ^= x >>> 8;
    x ^= x >>> 4;
    x ^= x >>> 2;
    x ^= x >>> 1;
    return (short) (x & 0x1);
  }

  public static short propagateRightmost(short x) {
    return (short) (x | (x - 1));
  }

  public static int getMod(int x, int d) {
    return x & (d - 1);
  }

  public static boolean isPowerOfTwo(int x) {
    return x != 0 && (x & (x - 1)) == 0;
  }

  public static void main(String[] args) {
    GenericTest
            .runFromAnnotations(args, "Parity.java",
                    new Object() {
                    }.getClass().getEnclosingClass());
    System.out.println(Integer.toBinaryString(propagateRightmost((short) 80))); // 1011111
    System.out.println(isPowerOfTwo(7)); // false
    System.out.println(isPowerOfTwo(1024)); // true
    System.out.println(getMod(77, 64)); // 13
  }
}
