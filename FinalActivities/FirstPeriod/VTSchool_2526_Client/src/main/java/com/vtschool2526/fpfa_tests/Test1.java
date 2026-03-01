package com.vtschool2526.fpfa_tests;

import com.sun.tools.javac.Main;
import com.vtschool2526.App;

public class Test1 {
    public static void main(String[] args) {
        App testMain = new App();
        String[] params = new String[0];

//        VTSchoolDBManager.initDatabase();

        System.out.println("1 SIN PARAMETROS");
        testMain.main(params);

        params = new String[1];

        System.out.println("2 --HELP");
        params[0] = "--help";
        testMain.main(params);

        System.out.println("3 -H");
        params[0] = "-h";
        testMain.main(params);

        System.out.println("4 INVALID ARGUMENT");
        params[0] = "--fck";
        testMain.main(params);

        System.out.println("5 -A (MISSING FILENAME)");
        params[0] = "-a";
        testMain.main(params);

        System.out.println("6 -ADD (MISSING FILENAME)");
        params[0] = "--add";
        testMain.main(params);

        params = new String[2];

        System.out.println("7 INVALID FILENAME");
        params[0] = "-a";
        params[1] = "notAfile.xml";
        testMain.main(params);

        System.out.println("8 INVALID EMAIL");
        params[0] = "-a";
        params[1] = "students5.xml";
        testMain.main(params);

        System.out.println("9 INVALID IDCARD");
        params[0] = "-a";
        params[1] = "students4.xml";
        testMain.main(params);

        System.out.println("10 XML FORMAT ERROR");
        params[0] = "-a";
        params[1] = "students3.xml";
        testMain.main(params);

        System.out.println("11 INVALID PHONE");
        params[0] = "-a";
        params[1] = "students2.xml";
        testMain.main(params);

        System.out.println("12 ADD OK");
        params[0] = "-a";
        params[1] = "students.xml";
        testMain.main(params);

        System.out.println("13 DUPLICATE IDCARD");
        params[0] = "-a";
        params[1] = "students.xml";
        testMain.main(params);
    }
}
