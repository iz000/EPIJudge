package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTest;
import epi.test_framework.TestFailure;
import epi.test_framework.TimedExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DutchNationalFlag {
  public enum Color {RED, WHITE, BLUE}

  public static void dutchFlagPartition(int pivotIndex, List<Color> A) {
    Color pivot = A.get(pivotIndex);
    int smaller = 0;
    for (int i = 0; i < A.size(); i++) {
      if (A.get(i).ordinal() < pivot.ordinal()) {
        Collections.swap(A, smaller++, i);
      }
    }
    int larger = A.size() - 1;
    for (int i = A.size() - 1; i >= 0; i--) {
      if (A.get(i).ordinal() > pivot.ordinal()) {
        Collections.swap(A, larger--, i);
      }
    }
  }

  public static void dutchFlagPartition2(int pivotIndex, List<Color> A) {
    Color pivot = A.get(pivotIndex);
    int smaller = 0, equal = 0, larger = A.size();
    while (equal < larger) {
      if (A.get(equal).ordinal() < pivot.ordinal()) {
        Collections.swap(A, smaller++, equal++);
      } else if (A.get(equal).ordinal() == pivot.ordinal()) {
        equal++;
      } else {
        Collections.swap(A, --larger, equal);
      }
    }
  }

  public static void fourPartitions(List<Integer> A) {
    int end = fourPartitionsInternal(A, 0, A.size(), 2);
    fourPartitionsInternal(A, 0, end, 1);
    fourPartitionsInternal(A, end, A.size(), 3);
  }

  private static int fourPartitionsInternal(List<Integer> A, int start, int end, int pivot) {
    int smaller = start;
    for (int i = start; i < end; i++) {
      if (A.get(i) < pivot) {
        Collections.swap(A, smaller++, i);
      }
    }
    return smaller;
  }

  @EpiTest(testDataFile = "dutch_national_flag.tsv")
  public static void dutchFlagPartitionWrapper(TimedExecutor executor,
                                               List<Integer> A, int pivotIdx)
          throws Exception {
    List<Color> colors = new ArrayList<>();
    int[] count = new int[3];

    Color[] C = Color.values();
    for (int i = 0; i < A.size(); i++) {
      count[A.get(i)]++;
      colors.add(C[A.get(i)]);
    }

    Color pivot = colors.get(pivotIdx);
    executor.run(() -> dutchFlagPartition(pivotIdx, colors));

    int i = 0;
    while (i < colors.size() && colors.get(i).ordinal() < pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() == pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    while (i < colors.size() && colors.get(i).ordinal() > pivot.ordinal()) {
      count[colors.get(i).ordinal()]--;
      ++i;
    }

    if (i != colors.size()) {
      throw new TestFailure("Not partitioned after " + Integer.toString(i) +
              "th element");
    } else if (count[0] != 0 || count[1] != 0 || count[2] != 0) {
      throw new TestFailure("Some elements are missing from original array");
    }
  }

  public static void main(String[] args) {
    GenericTest
            .runFromAnnotations(args, "DutchNationalFlag.java",
                    new Object() {
                    }.getClass().getEnclosingClass());
    List<Integer> A = Arrays.asList(1, 3, 0, 2, 1, 0, 2, 3, 1);
    fourPartitions(A);
    System.out.println(A);
  }
}
