package darun.csvloader;

import java.util.ArrayList;

public class DatabaseWriter implements JobExecutor{
	
	private ArrayList<Job> jobList = new ArrayList<Job>();
	private int jobCapacity = 0;
	
	DatabaseWriter(int jobCapacity){
		this.jobCapacity = jobCapacity;
	}
	
	public void execute(){
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		DatabaseSession session = new DatabaseSession();
		session.prepareStatement("INSERT INTO SDH_XC VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);");
		
		for(Job job : jobList){
			String[] values = (String[]) job.getJobDefn();
			session.addBatch(values);
		}
		session.executeBatch();
		session.close();
	}

	public boolean addJob(Job job) {
		if(jobList.size() < jobCapacity){
			jobList.add(job);
			return true;
		}
		return false;
	}
}
