package sbu.cs.Semaphore;

import java.util.concurrent.Semaphore;

public class Operator extends Thread {
    private String name;
    private Semaphore semaphore;
    public Operator(String name, Semaphore semaphore) {
        this.name = name;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire();
            System.out.println("Operator [" + name + "] accessed to Resources.");
            for (int i = 0; i < 10; i++)
            {
                Resource.accessResource();         // critical section - a Maximum of 2 operators can access the resource concurrently
                sleep(500);
            }
            System.out.println("Operator [" + name + "] exited from Resources.");
            semaphore.release();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
