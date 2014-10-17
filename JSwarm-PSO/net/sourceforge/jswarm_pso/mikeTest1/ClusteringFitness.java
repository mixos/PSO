package sourceforge.jswarm_pso.mikeTest1;

import java.util.ArrayList;
import java.util.HashMap;

import sourceforge.jswarm_pso.FitnessFunction;

public class ClusteringFitness extends FitnessFunction {

	public ClusteringFitness() {
		// TODO Auto-generated constructor stub
	}

	public ClusteringFitness(boolean maximize) {
		super(maximize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double evaluate(double[] position) {
		//config params
		int clNo = InitExecution.NUMBER_OF_CLUSTERS;
		int dims = Dataset.dimensions;
		int[] Ck = new int[clNo];
		double distFromCenter = 0.0;
		double centerDist = 0.0;
		ArrayList<double[]> centers = new ArrayList<double[]>();
		HashMap<Integer,Integer> pMap = new HashMap<Integer,Integer>();
		
		//isolate centers of a particle and add them to a list
		for(int i=1;i<=clNo;i++){			
			double[] center = new double[dims];
			int start = (i-1)*dims;
			int end = start+dims-1;
			for(int j=start;j<=end;j++){
				center[j-start] = position[j];
			}
			centers.add(center);
		}
		
		//for each center calculate its distance with all the others and sum it
		for(int i=0;i<clNo;i++){
			double[] ctemp1 = centers.get(i);
			for(int j=i+1;j<clNo;j++){
				double[] ctemp2 = centers.get(j);
				/*for(int k=0;k<ctemp1.length;k++){
					centerDist += Math.pow(ctemp1[k]-ctemp2[k],2);
				}*/
				centerDist += distance(ctemp1,ctemp2);
			}
			
		}
		
		//find how many instances belong to each cluster
		for(int i=0;i<Dataset.data.numInstances();i++){
			double min = Double.POSITIVE_INFINITY;
			int minCl = 1;
			for(int j=0;j<clNo;j++){
				double[] ctemp3 = centers.get(j);
				double[] thePos = new double[dims];
				for(int d=0;d<dims;d++){					
					if(Dataset.data.attribute(d).isNominal()){
						thePos[d] = Utils.nMap.get(Dataset.data.attribute(d).name()).get(Dataset.data.instance(i).toString(d));
					}else{
						thePos[d] = Dataset.data.instance(i).value(d);
					}
				}				
				double currDist = euclideanDist(ctemp3,thePos);
				//if(j==1){min = currDist;}
				if(currDist<min){
					min=currDist;
					minCl = j;
				}
			}
			Ck[minCl]++;
			pMap.put(i, minCl);
		}
		
		//real fitness
		for(int i=0;i<Dataset.data.numInstances();i++){
			int theClusterNumber = pMap.get(i);
			double[] theCluster = centers.get(theClusterNumber);
			double[] thePos = new double[dims];
			for(int d=0;d<dims;d++){					
				if(Dataset.data.attribute(d).isNominal()){
					thePos[d] = Utils.nMap.get(Dataset.data.attribute(d).name()).get(Dataset.data.instance(i).toString(d));
				}else{
					thePos[d] = Dataset.data.instance(i).value(d);
				}
			}
			distFromCenter += distance(theCluster,thePos)/Ck[theClusterNumber];
		}
		
		double finalValue = distFromCenter/centerDist;
		return finalValue;
	}
	
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
