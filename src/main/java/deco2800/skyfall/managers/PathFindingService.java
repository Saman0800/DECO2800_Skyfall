package deco2800.skyfall.managers;

import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.tasks.PathFindingServiceThread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PathFindingService extends TickableManager {

	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(25);

	public String toString() {
		return String.format("[%d/%d] Active: %d, Completed: %d, Task: %d, %n Shutdown: %s, Terminated: %s",
				executor.getPoolSize(),
				executor.getCorePoolSize(),
				executor.getActiveCount(),
				executor.getCompletedTaskCount(),
				executor.getTaskCount(),
				executor.isShutdown(),
				executor.isTerminated());
	}

	public void addPath(MovementTask movementTask) {
		executor.submit(new PathFindingServiceThread(movementTask), "PathFindingServiceThread");
	}

	public void stop() {
		executor.shutdownNow();
	}

	@Override
	public void onTick(long i) {
		// Currently not used but required due to interface
	}
}
