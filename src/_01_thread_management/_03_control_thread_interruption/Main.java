package _01_thread_management._03_control_thread_interruption;

import java.util.concurrent.TimeUnit;

public class Main {

    /* Use Java exceptions to control the interruption of a thread.
     On run the program starts going through folders by checking whether they have the file or not. */
    public static void main(String[] args) {
        FileSearch searcher = new FileSearch("../../", "Main.java");
        Thread thread = new Thread(searcher);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
