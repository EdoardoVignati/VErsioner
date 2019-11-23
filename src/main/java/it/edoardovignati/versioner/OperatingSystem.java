package it.edoardovignati.versioner;

public class OperatingSystem {

    static String OS;

    public static String detect() {
        OS = System.getProperty("os.name");
        return OS;
    }

    public int prerequisites() {
        //TODO: Run installation of Git
        return 0;
    }

    public static boolean isWindows() {

        return (OS.indexOf("win") >= 0);

    }

    public static boolean isMac() {

        return (OS.indexOf("mac") >= 0);

    }

    public static boolean isUnix() {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);

    }

    public static boolean isSolaris() {

        return (OS.indexOf("sunos") >= 0);

    }

}
