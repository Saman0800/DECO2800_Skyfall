package deco2800.skyfall.managers;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


import deco2800.skyfall.tasks.MovementTask;
import deco2800.skyfall.tasks.PathFindingServiceThread;

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
		// TODO Auto-generated method stub
		
	}
}
