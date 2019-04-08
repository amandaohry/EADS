package utility;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.DateUtils;

import dao.ServiceDAO;
import dao.ServiceVesselDAO;
import entity.Service;
import entity.ServiceVessel;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

//import org.apache.commons.lang.time.DateUtils;

public class model {
	
	public static SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-dd HH:MM:SS");  
	public static final int M = 10000;
	public static ArrayList<ServiceVessel> serviceVessels;
	public static ArrayList<Service> services;
	public static Date currentTime;
	public static IloNumVar[] b;
	
	public static void run() throws IOException {
		while (true) {//TODO: run every few minutes
			ServiceVesselDAO serviceVesselDao = new ServiceVesselDAO();
			serviceVessels = serviceVesselDao.getServiceVesselDetail();
			ServiceDAO serviceDao = new ServiceDAO();
			services = serviceDao.getServiceDetail();
			String[] locations = getLocationList(); //loation list of lat and long
			String[] mmsi = getBBList(); //list of bunker barges mmsi (unique id)
			
			//TODO: remove waiting time at depot
			currentTime = TimeUtility.getCurrentTime();
			int numS = 0; //number of services (including depot)
			int numK = 0; //number of bunker barges
			int numT = 0; //number of trips TODO: set it to fixed?
			try{
				IloCplex cplex = new IloCplex();
		        // create model and solve it
		        IloNumVar[][][][] x = new IloNumVar[numS][numS][numK][numT];//Xijkt where i, j are requests, k is bunker barge, t is trip number
		        IloNumVar[][][][] y = new IloNumVar[numS][numS][numK][numT];
		        IloNumVar[] b = new IloNumVar[numS]; ////num of minutes elapsed from the begining of the model run (call getCurrentTime from api to get model running time)
		        for (int i=0; i<numS; i++){
		        	for (int j=0; j<numS; j++){
		        		for (int k=0; k<numK; k++){
		        			x[i][j][k] = cplex.boolVarArray(numT);
		        		}
		        	}
		        }
		        for (int i=0; i<numS; i++){
		        	for (int j=0; j<numS; j++){
		        		for (int k=0; k<numK; k++){
		        			y[i][j][k] = cplex.boolVarArray(numT);
		        		}
		        	}
		        }
				//objective function
		        IloLinearNumExpr obj = cplex.linearNumExpr();
		        // add travelling cost & waiting time cost for bunker barge
				for (int k = 0; k < numK; k++){
		        	for (int t = 0; t < numT; t++){
		        		for (int i=0; i<numS; i++){
				        	for (int j=0; j<numS; j++){
				        		obj.addTerm(getTravelTime(i, j, k), x[i][j][k][t]);
//				        		obj.addTerm(1, y[i][j][k][t]);
				        	}
		        		}
		        	}
		        }
		        cplex.addMinimize(obj);
		        //Constrains:
		        //constrains for y
//		        for (int k = 0; k < numK; k++){
//		        	for (int t = 0; t < numT; t++){
//		        		for (int i=0; i<numS; i++){
//				        	for (int j=0; j<numS-1; j++){//dont count the waiting time at depot
//				        		IloLinearNumExpr expr = cplex.linearNumExpr();
//				        		expr.addTerm(1.0, y[i][j][k][t]);
//				        		cplex.addGe(expr, getWaitingTime(i, j, k));
//				        		cplex.addGe(expr,  0);
//				        	}
//		        		}
//		        	}
//		        }
		      //There is only 1 arc going out of node i in each trip for each vehicle (12)
		        for (int i = 0; i < numS; i++){
		        	for (int t = 0; t < numT; t++){
		        		IloLinearNumExpr expr = cplex.linearNumExpr();
		        		for (int k = 0; k < numK; k++) {
		        			for (int j = 0; j < numS; j++){
			        			expr.addTerm(1.0, x[i][j][k][t]);
				        	}
		        		}
			        	cplex.addEq(expr, 1.0);
		        	}
		        }
		        // all routes must include the depot as the start point (13)
		        for (int t = 0; t < numT; t++){//for all trips
		        	for (int k = 0; k < numK; k++){//and all bunker barges
		        		IloLinearNumExpr expr = cplex.linearNumExpr();
			        	for (int j = 0; j < numS; j++){
		        			expr.addTerm(1.0, x[0][j][k][t]);
			        	}
			        	cplex.addEq(expr, 1.0);
		        	}
		        }
		        //all routes must include the depot as the end point (15)
		        for (int t = 0; t < numT; t++){//for all trips
		        	for (int k = 0; k < numK; k++){//and all bunker barges
		        		IloLinearNumExpr expr = cplex.linearNumExpr();
			        	for (int j = 0; j < numS; j++){
		        			expr.addTerm(1.0, x[j][0][k][t]);
			        	}
			        	cplex.addEq(expr, 1.0);
		        	}
		        }
		        //in each trip t by each vehicle k, any nodes j which has 1 arc going in must have 1 arc going out (14)
		        for (int k = 0; k < numK; k++) {
		        	for (int t = 0; t < numT; t++) {
		        		IloLinearNumExpr expr = cplex.linearNumExpr();
		        		for (int j = 0; j < numS; j++) {
		        			for (int i = 0; i < numS; i++) {
			        			expr.addTerm(1.0,  x[i][j][k][t]);
			        		}
		        			for (int i = 0; i < numS; i++) {
			        			expr.addTerm(-1.0,  x[j][i][k][t]);
			        		}
		        			cplex.addEq(expr, 0);
		        		}
		        	}
		        }
		        // begin time of service j must be greater than the begin time + service time + travel time of predecessor node i (16)
		        for (int k = 0; k < numK; k++){
		        	for (int t = 0; t < numT; t++){
		        		for (int i=0; i<numS; i++){
				        	for (int j=0; j<numS; j++){
				        		IloLinearNumExpr expr = cplex.linearNumExpr();
				        		expr.addTerm(1.0, b[j]);
				        		expr.addTerm(-1.0, b[i]);
				        		expr.addTerm(-M, x[i][j][k][t]);
				        		cplex.addGe(expr, getServiceTime(i, k) + getTravelTime(i, j, k) - M);
				        	}
		        		}
		        	}
		        }
		        //time window
		        for (int i = 1; i < numS; i++) {//only check time window in nodes other than depot
		        	cplex.addGe(b[i], (int) ((services.get(i).getRequestTime().getTime() - currentTime.getTime()) / (60 * 1000)));
//		        	cplex.addLe(b[i], (int) ((services.get(i).getRequestTime().getTime() - currentTime.getTime()) / (60 * 1000)));
		        }
		        //capacity
		        for (int t = 0; t < numT; t++){//for all trips
		            for (int k = 0; k < numK; k++){//and all bunker barges
		              IloLinearNumExpr expr = cplex.linearNumExpr();
		              for (int i = 0; i < numS; i++){
		                for  (int j = 0; j < numS; j++){
		                  expr.addTerm(services.get(i).getRequestedFuel(), x[i][j][k][t]);
		                }
		              }
		              cplex.addLe(expr, serviceVessels.get(k).getCapacity());
		            }
		         }
		         //can't go from i to i
		        for (int k = 0; k < numK; k++){
		        	for (int t = 0; t < numT; t++){
		        		for (int i=0; i<numS; i++){
		        			cplex.addEq(x[i][i][k][t], 0);
		        		}
		        	}
		        }
		        
		        
		        
			} catch (IloException e) {
		        System.err.println("Concert exception caught: " + e);
		    }
	        System.out.println("Successfully Run");
	        
		}
	}

	private static ArrayList<Service> processRequests(String[] requestsRaw) {
		return null;
	}

	public static Date getCurrentTime(){
		//get current time from api getCurrentTime method
		Date d1 = null;
		try {
			d1 = format.parse("xxx");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d1;

	}	

	public static String[] getLocationList(){
		return null;
		//get a list of service locations (isServiceLocation = True) from api getLocationList method

	}

	public static String[] getServiceList(){
		return null;
		//get a list of service requests id from api getService method

	}

	public static String[] getBBList(){
		return null;
		//get a list of bunker barges mmsi from api getVessel method

	}

	public static int getTravelTime (int i, int j, int k){
		//get travelling time of bunker barge k btwn request i and j from api getTravelTime method
		//firstly get lat and lon of location in requrest i, j
		String iRequestId = services.get(i).getRequestID();
		String jRequestId = services.get(i).getRequestID();
		String mmsi = serviceVessels.get(k).getMMSI();
		//then request from api the travel time in minutes
		return TimeUtility.getTravelTime(iRequestId, jRequestId, mmsi);
		
	}

	public static int getServiceTime (int i, int k) {
		 int iFlowRate = serviceVessels.get(k).getPumpRate(); //get pump rate of bunker barge from api. (under getServiceVesselDetail method)
		 int iAmount = services.get(i).getRequestedFuel();//get from getServiceDetail method 
		 int si = (iAmount / iFlowRate) * 60;//calculate service time in minutes for i
		 return si;
	 }
	
//	public static int getWaitingTime (int i, int j, int k){
//		
////		if (j == 0) {
////			return 0;//depot doesn't count as having waiting time
////		}else if (i == 0) {
////			int si = getServiceTime(i, k);
////			Date beginTimei = DateUtils.addMinutes(currentTime, b[i]);
////
////			Date bj = DateUtils.addMinutes(beginTimei, si + getTravelTime(i, j, k));
////			int wj = (int) ((bj.getTime() - ej.getTime()) / (60 * 1000));
////			return wj;
////		}
////		String mmsi = mmsi[k];
////		String iRequestId = requrests[i];
////		String jRequestId = requrests[j];
//		int si = getServiceTime(i, k);
//		Date beginTimei = DateUtils.addMinutes(currentTime, b[i]);
//		Date ej = null; //get j's expected service start time from api getServiceDetail method
//
//		Date bj = DateUtils.addMinutes(beginTimei, si + getTravelTime(i, j, k));
//		int wj = (int) ((bj.getTime() - ej.getTime()) / (60 * 1000));
//		return wj;
//	}
}


