package api.ceroxe.thread;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class ThreadManager {
    private CopyOnWriteArrayList<Runnable> runnables = new CopyOnWriteArrayList<>();
    private int waitingTime = 100;

    public ThreadManager(CopyOnWriteArrayList<Runnable> runnables) {
        this.runnables = runnables;
    }

    public ThreadManager(Runnable... runnables) {
        this.runnables.addAll(Arrays.asList(runnables));
    }

    public void addRunnableMethod(Runnable runnable) {
        this.runnables.add(runnable);
    }

    public void startAll() {
        final int[] completeThreadNum = {0};
        for (Runnable runnable : runnables) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runnable.run();
                    completeThreadNum[0]++;
                }
            }).start();
        }
        while (completeThreadNum[0] < runnables.size()) {
            try {
                Thread.sleep(waitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        if (waitingTime >= 0) {
            this.waitingTime = waitingTime;
        }
    }

    public void startAt(int index) {
        new Thread(runnables.get(index)).start();
    }
}
