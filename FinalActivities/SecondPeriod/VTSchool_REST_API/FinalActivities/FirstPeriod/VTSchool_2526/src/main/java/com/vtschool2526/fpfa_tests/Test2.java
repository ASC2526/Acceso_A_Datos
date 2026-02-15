package com.vtschool2526.fpfa_tests;

import com.vtschool2526.App;

public class Test2 {
    public static void main(String[] args) {
        App testMain = new App();

        String[] params = new String[1];

        System.out.println("1 --enroll (no arguments)");
        params[0] = "--enroll";
        testMain.main(params);

        System.out.println("2 -e (no arguments)");
        params[0] = "-e";
        testMain.main(params);

        params = new String[2];

        System.out.println("3 missing course");
        params[0] = "-e";
        params[1] = "12332003";
        testMain.main(params);

        params = new String[3];

        System.out.println("4 wrong student");
        params[0] = "-e";
        params[1] = "11112222";
        params[2] = "1";
        testMain.main(params);

        System.out.println("4B wrong course");
        params[0] = "-e";
        params[1] = "12332001";
        params[2] = "0";
        testMain.main(params);

        System.out.println("5 Previously enrolled");
        params[0] = "-e";
        params[1] = "12332001";
        params[2] = "1";
        testMain.main(params);

        System.out.println("6 First course enrollment");
        params[0] = "-e";
        params[1] = "12332008";
        params[2] = "1";
        testMain.main(params);

        System.out.println("7 Has joined other course");
        params[0] = "-e";
        params[1] = "12332008";
        params[2] = "2";
        testMain.main(params);

        System.out.println("8 Second course enrollment (DAM to DAW)");
        params[0] = "-e";
        params[1] = "12332006";
        params[2] = "2";
        testMain.main(params);

        System.out.println("9 Second course enrollment (third year)");
        params[0] = "-e";
        params[1] = "12332004";
        params[2] = "1";
        testMain.main(params);

        System.out.println("10 First course enrollment");
        params[0] = "-e";
        params[1] = "12332003";
        params[2] = "3";
        testMain.main(params);
    }
}
