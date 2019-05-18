package volunteer.upay.com.upay.util;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import bolts.CancellationToken;
import bolts.Continuation;
import bolts.Task;

public class TaskUtilities {


    /**
     * Use this code to queue a {@code runnable} in the BACKGROUND_EXECUTOR
     *
     * @param runnable the code to get executed
     * @return the task wrapping the {@code runnable}
     */
    public static Task<Void> runOnBackgroundThread(@NonNull Runnable runnable) {
        return runOnExecutor(runnable, Task.BACKGROUND_EXECUTOR, null);
    }

    /**
     * Use this code to queue a {@code runnable} in the BACKGROUND_EXECUTOR
     *
     * @param codeToExecute the code to get executed
     * @return the task wrapping the {@code runnable}
     */
    public static <T> Task<T> runOnBackgroundThread(@NonNull Callable<T> codeToExecute) {
        return runOnExecutor(codeToExecute, Task.BACKGROUND_EXECUTOR, null);
    }

    public static Task<Void> runOnExecutor(@NonNull final Runnable runnable,
                                           @Nullable Executor executor,
                                           @Nullable CancellationToken cancellationToken) {
        Callable<Void> callable = new Callable<Void>() {
            @Override
            public Void call() {
                runnable.run();
                return null;
            }
        };

        return runOnExecutor(callable, executor, cancellationToken);
    }

    /**
     * Use this code to queue a {@code runnable} in the {@code executor}
     *
     * @param codeToExecute     the code to get executed
     * @param executor          the executor to be used to run the {@code runnable}
     * @param cancellationToken the cancellation token to be used
     * @return the task wrapping the {@code runnable}
     */
    public static <T> Task<T> runOnExecutor(@NonNull final Callable<T> codeToExecute,
                                            @Nullable Executor executor,
                                            @Nullable CancellationToken cancellationToken) {
        Task<T> resultTask;

        // If executor is main thread executor and already on main thread, execute it right away
        if (executor == Task.UI_THREAD_EXECUTOR && Looper.getMainLooper() == Looper.myLooper()) {
            try {
                resultTask = Task.forResult(codeToExecute.call());
            } catch (Exception e) {
                resultTask = Task.forError(e);
            }
        } else {
            resultTask = (executor != null) ? Task.call(codeToExecute, executor, cancellationToken)
                    : Task.call(codeToExecute, cancellationToken);
        }

        return resultTask;
    }
}
