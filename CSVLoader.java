package darun.csvloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CSVLoader implements JobAllocator {

	private ExecutorService pool = Executors.newFixedThreadPool(50);
	private Job job = null;
	private JobExecutor executor = null;
	private int jobUnitSize = 0;
	//private int exeCount = 1;
	
	CSVLoader(int jobUnitSize){
		this.jobUnitSize = jobUnitSize;
		executor = new DatabaseWriter(jobUnitSize);
	}
	
	public void allocate(){
		if(job != null){
			if(executor.addJob(job)){
				job = null;
			}
			else{
				pool.execute(executor);
				executor = new DatabaseWriter(jobUnitSize);
				allocate();
				//exeCount++;
				//System.out.println("executor ="+exeCount);
			}
		}else{			
			pool.execute(executor);
		}
	}

	public void clean() throws Exception{
		pool.shutdown();
		while(!pool.isTerminated()){
			Thread.sleep(500);
		}
	}
	
	public void load(String fileName){
		try {
			BufferedReader br = new BufferedReader( new FileReader(fileName));
            String strLine = "";
            String[] values = null;
                        
            while( (strLine = br.readLine()) != null){
                values = strLine.split(",");
        		job = new Job();
        		job.setJobDefn(values);
        		allocate();
            }
            allocate();
            br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println("started at: "+sdf.format(new Date(System.currentTimeMillis())));
		try{
			int loadUnitSize = Integer.parseInt(ConfigLoader.getConfig().getProperty("LOAD_UNIT_SIZE")); 
			CSVLoader loader = new CSVLoader(loadUnitSize);
			loader.load(ConfigLoader.getConfig().getProperty("LOAD_FILE_NAME"));
			loader.clean();
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("ended at: "+sdf.format(new Date(System.currentTimeMillis())));
	}

}
