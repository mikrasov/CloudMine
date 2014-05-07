package com.cloudmine.mine;

import java.util.LinkedList;
import java.util.List;

import com.cloudmine.Graph;
import com.cloudmine.http.AppClient;
import com.google.gson.Gson;

public class Mine implements Runnable{

	private static transient final int THREAD_TIMEOUT = 5*1000;
	public static final String SERVER_IP = "http://localhost:8080";
	
	protected transient AppClient master = new AppClient(SERVER_IP);
	protected List<Solution> solutionsQueue = new LinkedList<>();
	
	protected transient Gson gson = new Gson();
	protected transient Thread thread = new Thread(this);
	protected transient int timeSinceLastPost = 0;
	
	protected  Miner[] miners;
	
	public Mine(int numMiners) {
		miners = new Miner[numMiners];
		miners[0] = new ForwardMiner(solutionsQueue);
		miners[0].mine(Graph.generateRandom(8), 7);
	}
	
	
	protected void contactServer(){
		String json = gson.toJson(this);
		
		System.out.println(json);
		String responce = master.post(json);
		solutionsQueue.clear();
	}
	
	@Override
	public void run() {
		while(true){		
			try {
				contactServer();
				Thread.sleep(THREAD_TIMEOUT);
			} catch (Exception e) {}
		}
	}

	public void start(){
		thread.start();
	}
	
	
	public static void main(String[] args) {
		Mine mine = new Mine(4);
		mine.start();
	}
}