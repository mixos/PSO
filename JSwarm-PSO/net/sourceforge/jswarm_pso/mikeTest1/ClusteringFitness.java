package sourceforge.jswarm_pso.mikeTest1;

import java.util.ArrayList;

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
		int clNo = InitExecution.NUMBER_OF_CLUSTERS;
		int dims = Dataset.dimensions;
		double[] Ck = new double[clNo];
		int[] counter = new int[clNo];
		double centerDist = 0.0;
		ArrayList<double[]> centers = new ArrayList<double[]>();
				
		for(int i=1;i<=clNo;i++){			
			double[] center = new double[dims];
			int start = (i-1)*dims;
			int end = start+dims-1;
			for(int j=start;j<=end;j++){
				center[j-start] = position[j];
			}
			centers.add(center);
		}
		for(int i=1;i<=clNo;i++){
			double[] ctemp1 = centers.get(i);
			for(int j=i+1;j<=clNo;j++){
				double[] ctemp2 = centers.get(j);
				for(int k=0;k<ctemp1.length;k++){
					centerDist += Math.pow(ctemp1[k]-ctemp2[k],2);
				}
			}
			
		}
		
		for(int i=0;i<Dataset.data.numInstances();i++){
			double min = Double.POSITIVE_INFINITY;
			int minCl = 1;
			for(int j=1;j<=clNo;j++){
				double[] ctemp3 = centers.get(j);
				double[] thePos = new double[dims];
				for(int d=0;d<=dims;d++){					
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
		}
		
		return 0;
	}
	
	private double euclideanDist(double[] cpos, double[] dpos){
		double temp2 = 0.0;
		for(int i=0;i<cpos.length;i++){
			double temp1 = Math.pow(cpos[i]-dpos[i],2);
			temp2 += temp1;
		}
		return Math.sqrt(temp2);
	}

}