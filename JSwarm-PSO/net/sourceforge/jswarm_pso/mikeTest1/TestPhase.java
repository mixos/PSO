package sourceforge.jswarm_pso.mikeTest1;

import java.util.ArrayList;

public class TestPhase {
	
	public TestPhase(){
		
	}
	
	public void goTest(double[] bestPos){
		int clNo = Dataset.classesNo;
		int dims = Dataset.dimensions;
		double total = 0;
		ArrayList<double[]> centers = new ArrayList<double[]>();
		
		//separate bestposition centers
		for(int i=1;i<=clNo;i++){			
			double[] center = new double[dims];
			int start = (i-1)*dims;
			int end = start+dims-1;
			for(int j=start;j<=end;j++){
				center[j-start] = bestPos[j];
			}
			centers.add(center);
		}
		
		//validate
		for(int i=InitClassification.numTrainingData;i<Dataset.data.numInstances();i++){
		//for(int i=0;i<Dataset.data.numInstances();i++){
			int theClass = (int) Dataset.data.instance(i).classValue();
			double[] thePos = new double[dims];
			for(int d=0;d<dims;d++){					
				if(Dataset.data.attribute(d).isNominal()){
					if(Utils.debug){
						System.out.println("eeep");
					}
					thePos[d] = Utils.nMap.get(Dataset.data.attribute(d).name()).get(Dataset.data.instance(i).toString(d));
				}else{
					thePos[d] = Dataset.data.instance(i).value(d);
				}
			}
			
			double min = Double.POSITIVE_INFINITY;
			int minCl = -1;
			for(int j=0;j<clNo;j++){
				double[] ctemp3 = centers.get(j);												
				double currDist = euclideanDist(ctemp3,thePos);
				if(currDist<min){
					min=currDist;
					minCl = j;
				}
			}
			//System.out.println("a: "+minCl);
			//System.out.println("b: "+theClass);
			if(minCl==theClass){
				total++;
			}
		}
		InitClassification.sumOfFold += (total*100)/InitClassification.numTestData;
		System.out.println(total+" correctly classified instances");
		System.out.println("out of "+(Dataset.data.numInstances()-InitClassification.numTrainingData)+" test instances.");
		System.out.println("Current phase sum: "+((total*100)/InitClassification.numTestData)+"%");
		
	}//end goTest
	
	private double euclideanDist(double[] cpos, double[] dpos){
		double temp2 = 0.0;
		for(int i=0;i<cpos.length;i++){
			double temp1 = Math.pow(cpos[i]-dpos[i],2);
			temp2 += temp1;
		}
		return Math.sqrt(temp2);
	}
	
	private double distance(double[] cpos, double[] dpos){
		double temp2 = 0.0;
		for(int i=0;i<cpos.length;i++){
			double temp1 = Math.pow(cpos[i]-dpos[i],2);
			temp2 += temp1;
		}
		return temp2;
	}

}
