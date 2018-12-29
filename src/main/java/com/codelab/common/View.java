package com.codelab.common;

/**
 * Utility methods to print to the command line.
 *
 * @author rmistry@google.com (Ravi Mistry)
 */
public class View {

  static void header1(String name) {
    System.out.println();
    System.out.println("================== " + name + " ==================");
    System.out.println();
  }

  static void header2(String name) {
    System.out.println();
    System.out.println("~~~~~~~~~~~~~~~~~~ " + name + " ~~~~~~~~~~~~~~~~~~");
    System.out.println();
  }
}
