package com.eltonb.concurrency.demo;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MainApp {

    private static final String dateValue = "2021-11-15 19:30:00";
    private static final int MAX_TRIES = 500;

    public static void main(String[] args) {
        MainApp app = new MainApp();
        boolean res = app.testOne();
        System.out.println("Single run returned " + res);

        /*
        System.out.println("Sequential multiple runs:");
        for(int i=0; i<MAX_TRIES; i++) {
            boolean r = app.testOne();
            System.out.println(i + ": " + r);
        };
        */

        System.out.println("Concurrent multiple runs... ");
        for(int i=0; i<MAX_TRIES; i++) {
            Thread t = new Thread(() -> {
                app.testOne();
                System.out.printf("thread %s finished \n", Thread.currentThread().getName());
            });  //app::testOne
            t.start();
        }
        System.out.println("main thread terminates: " + Thread.currentThread().getName());
    }

    private boolean testOne() {
        try {
            Date date = CommonUtils.toDate(dateValue);
            boolean ok =
                    date.getYear()      ==121 &&
                    date.getMonth()     == 10 &&
                    date.getDate()      == 15 &&
                    date.getHours()     == 19 &&
                    date.getMinutes()   == 30 &&
                    date.getSeconds()   ==  0 ;
            if ( ! ok )
                System.out.printf("Parsing %s failed \n", dateValue);
            return ok;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void go() {
        System.out.println("starting application");
        System.out.println("application terminated");
        //new Thread()
    }
}
