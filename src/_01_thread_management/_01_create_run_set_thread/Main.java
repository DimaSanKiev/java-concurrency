package _01_thread_management._01_create_run_set_thread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {

    private static final int THREADS_NUMBER = 10;

    public static void main(String[] args) {
        System.out.printf("Minimum priority: %s\n", Thread.MIN_PRIORITY);
        System.out.printf("Normal priority: %s\n", Thread.NORM_PRIORITY);
        System.out.printf("Maximum priority: %s\n", Thread.MAX_PRIORITY);

        Thread threads[] = new Thread[THREADS_NUMBER];
        Thread.State status[] = new Thread.State[THREADS_NUMBER];

        for (int i = 0; i < THREADS_NUMBER; i++) {
            threads[i] = new Thread(new Calculator());
            if ((i % 2) == 0) {
                threads[i].setPriority(Thread.MAX_PRIORITY);
            } else {
                threads[i].setPriority(Thread.MIN_PRIORITY);
            }
            threads[i].setName("My thread " + i);
        }

        try (FileWriter file = new FileWriter("log.txt");
             PrintWriter pw = new PrintWriter(file)) {
            for (int i = 0; i < THREADS_NUMBER; i++) {
                pw.println("Main : Status of Thread " + i + " : " + threads[i].getState());
                status[i] = threads[i].getState();
            }
            for (int i = 0; i < THREADS_NUMBER; i++) {
                threads[i].start();
            }

            boolean finish = false;
            while (!finish) {
                for (int i = 0; i < THREADS_NUMBER; i++) {
                    if (threads[i].getState() != status[i]) {
                        writeThreadInfo(pw, threads[i], status[i]);
                    }
                }
            }

            finish = true;
            for (int i = 0; i < THREADS_NUMBER; i++) {
                finish = finish && (threads[i].getState() == Thread.State.TERMINATED);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeThreadInfo(PrintWriter pw, Thread thread, Thread.State state) {
        pw.printf("Main : Id %d - %s\n", thread.getId(), thread.getName());
        pw.printf("Main : Priority %d\n", thread.getPriority());
        pw.printf("Main : Old State %s\n", state);
        pw.printf("Main : New State %s\n", thread.getState());
        pw.printf("Main : =============\n", thread.getState());
    }
}
