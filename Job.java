package darun.csvloader;

import java.io.Serializable;

public class Job implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	Object jobDefn = null;
	
	public Object getJobDefn(){
		return jobDefn;
	}
	public void setJobDefn(Object jobDefn){
		this.jobDefn = jobDefn;
	}
}
