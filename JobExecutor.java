package darun.csvloader;

public interface JobExecutor extends Runnable{
		
	public void execute();
	public boolean addJob(Job job);
}
