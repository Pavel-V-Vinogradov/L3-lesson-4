public class Main {
    private final Object mon = new Object();
    private volatile int currentLetter = 'A';

    public static void main(String[] args) {

        Main main = new Main();
        Thread thread1 = new Thread(() -> main.printX('A'));
        Thread thread2 = new Thread(() -> main.printX('B'));
        Thread thread3 = new Thread(() -> main.printX('C'));
        thread1.start();
        thread2.start();
        thread3.start();
    }

    public void printX(char abc) {
        synchronized (mon) {
            try {
                for (int i = 0; i < 5; i++) {
                    while (currentLetter != abc) {
                        mon.wait();
                    }
                    System.out.print(abc);
                    currentLetter++;
                    if (currentLetter == 'D') {
                        currentLetter = 'A';
                    }
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}