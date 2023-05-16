package com.rmq.sample;

/**
 * TODO
 *
 * @author gnl
 * @since 2023/5/14
 */
public class Main {
    public static void main(String[] args) {
        test("oneway");
    }

    public static String test(String str) {
        switch (str) {
            case "oneway":
                System.out.println("oneway");
                assert false;
                return null;
            case "async":
                System.out.println("async");
                return null;
            case "sync":
                System.out.println("sync");
                return str;
            default:
                assert false;
                return null;
        }
    }
}
