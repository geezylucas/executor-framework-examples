import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("do something...");
        };

        scheduledExecutor.schedule(task, 3, TimeUnit.SECONDS);
        //scheduledExecutor.scheduleAtFixedRate(task, 2, 1, TimeUnit.SECONDS);
        //scheduledExecutor.scheduleWithFixedDelay(task, 2, 1, TimeUnit.SECONDS);

        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

        scheduledExecutor.shutdown();
    }
}
