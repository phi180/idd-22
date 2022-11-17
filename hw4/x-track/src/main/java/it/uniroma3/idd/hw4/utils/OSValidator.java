package it.uniroma3.idd.hw4.utils;

public class OSValidator {

    private static String OS = System.getProperty("os.name").toLowerCase();

    public static String getOS(){
        if (isWindows()) {
            return "windows";
        } else if (isMac()) {
            return "mac";
        } else if (isUnix()) {
            return "linux";
        } else if (isSolaris()) {
            return "solaris";
        }

        return "err";
    }

    /** PRIVATE METHODS */
    private static boolean isWindows() {
        return OS.contains("win");
    }

    private static boolean isMac() {
        return OS.contains("mac");
    }

    private static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.contains("aix"));
    }

    private static boolean isSolaris() {
        return OS.contains("sunos");
    }

}
