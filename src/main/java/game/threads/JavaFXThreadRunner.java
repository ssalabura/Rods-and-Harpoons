package game.threads;

import javafx.application.Platform;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaFXThreadRunner implements ThreadRunner {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    @Override
    public void runLaterInBackground(Runnable runnable) {
        executorService.submit(runnable);
    }

    @Override
    public void runLaterInMainThread(Runnable runnable) {
        Platform.runLater(runnable);
    }
}
