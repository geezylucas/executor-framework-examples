import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Runtime.getRuntime().availableProcessors());

        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable runnableExample = new Runnable() {

            @Override
            public void run() {
                System.out.println("Hello world from runnableExample");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        executor.execute(runnableExample); // The execute() method is void and doesn’t give any possibility to get the
                                           // result of a task’s execution or to check the task’s status (is it running)

        Callable<String> callableExample = () -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello world from callableExample";
        };

        Future<String> future = executor.submit(callableExample); // submit() submits a Callable or a Runnable task to
                                                                  // an ExecutorService and returns a result of type
                                                                  // Future:

        System.out.println(future.isDone());
        System.out.println(future.get()); // The Future interface provides a special blocking method get(), which
                                          // returns an actual result of the Callable task’s execution or null in the
                                          // case of a Runnable task
                                          // String result = future.get(200, TimeUnit.MILLISECONDS);
                                          // boolean canceled = future.cancel(true);
                                          // boolean isCancelled = future.isCancelled();
        System.out.println(future.isDone());

        Callable<String> callableTaskFirst = () -> doSomething("first");
        Callable<String> callableTaskSecond = () -> doSomething("second");
        Callable<String> callableTaskThird = () -> doSomething("third");

        List<Callable<String>> callableTasks = new ArrayList<>();
        callableTasks.add(callableTaskFirst);
        callableTasks.add(callableTaskSecond);
        callableTasks.add(callableTaskThird);

        List<Future<String>> result = executor.invokeAll(callableTasks); // invokeAll() assigns a collection of tasks to
                                                                         // an ExecutorService, causing
        // each to run, and returns the result of all task executions in the form of
        // a list of objects of type Future:

        for (Future<String> f : result) {
            System.out.println("isDone: " + f.isDone() + " get: " + f.get());
        }

        String onlyOneResult = executor.invokeAny(callableTasks); // invokeAny() assigns a collection of tasks to an
                                                                  // ExecutorService, causing each to run, and returns
                                                                  // the result of a successful execution of one task
                                                                  // (if there was a successful execution):

        System.out.println(onlyOneResult);

        executor.shutdown();
        // The ExecutorService will not be automatically destroyed when there is no
        // task to process. It will stay alive and wait for new work to do.
        // The shutdown() method doesn’t cause immediate destruction of the
        // ExecutorService. It will make the ExecutorService stop accepting new tasks
        // and shut down after all running threads finish their current work
    }

    private static String doSomething(String i) {
        System.out.println("Running " + i);
        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        return "done " + i;
    }
}