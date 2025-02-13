package com.suhba.utils;

public class ServerStateManager {
    private static boolean isServerRunning = false;

    public static boolean isServerRunning() {
        return isServerRunning;
    }

    public static void setServerRunning(boolean running) {
        isServerRunning = running;
    }
}
