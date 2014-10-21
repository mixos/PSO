package sourceforge.jswarm_pso.mikeTest1;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import weka.core.Instances;

public class Utils {
	
	public static HashMap<String,HashMap<String,Double>> nMap;
	//public static double[] minF; 
	public static double[] maxF; 
	
	public static double[] minValues(Instances data){
		int dims = Dataset.dimensions;
		int clNo = Dataset.classesNo;
		double[] minArray = new double[dims];
		for(int j=0;j<dims;j++){		
			if(data.attribute(j).isNumeric()){				
				double minvalue = data.instance(0).value(j);
				for(int i=1;i<data.numInstances();i++){
					if(data.instance(i).value(j)<minvalue){
						minvalue = data.instance(i).value(j);
						minArray[j] = minvalue;
					}
				}
				//System.out.println("########## ATTRIBUTE "+j+" "+data.attribute(j).name()+" #########################");				
				//System.out.println("Min value of attribute "+j+": "+minvalue);
				}else{
					minArray[j]= 0;
				}
			}
		
		double[] minFull = new double[dims*clNo];
		for(int k = 0;k<dims*clNo;k++){
			minFull[k] = minArray[(k%dims)];
			//System.out.println(minFull[k]);
		}
		//minF=minFull;		
		return minFull;
	
	
	}
	
	public static double[] maxValues(Instances data){
		int dims = Dataset.dimensions;
		int clNo = Dataset.classesNo;
		double[] maxArray = new double[dims];
		for(int j=0;j<dims;j++){		
			if(data.attribute(j).isNumeric()){
				double maxvalue = data.instance(0).value(j);
				for(int i=1;i<data.numInstances();i++){
					if(data.instance(i).value(j)>maxvalue){
						maxvalue = data.instance(i).value(j);
						maxArray[j] = maxvalue;
					}
				}
				//System.out.println("########## ATTRIBUTE "+j+" "+data.attribute(j).name()+" #########################");
				//System.out.println("Max value of attribute "+j+": "+maxvalue);
				}else{
					maxArray[j]= 1;
				}
			}
		
		double[] maxFull = new double[dims*clNo];
		for(int k = 0;k<dims*clNo;k++){
			maxFull[k] = maxArray[(k%dims)];
			//System.out.println(maxFull[k]);
		}
		maxF=maxFull;
		return maxFull;
	}
	
	public static void buildNominalMap(Instances data){
		int dims = Dataset.dimensions;
		HashMap<String,Double> submap = new HashMap<String,Double>();
		HashMap<String,HashMap<String,Double>> map = new HashMap<String,HashMap<String,Double>>();  
		double count;
		for(int j=0;j<dims;j++){			
			if(data.attribute(j).isNominal()){
				Enumeration<?> en = data.attribute(j).enumerateValues();
				while(en.hasMoreElements()){
					count=0.0;
					String s = (String) en.nextElement();
					if(s.equals("?")){
						s = "'?'";
					}
					for(int i=0;i<data.numInstances();i++){
						if(s.equals(data.instance(i).toString(j))){
							count=count+1;
						}
					}//instances for
					double freq = (Double) count/data.numInstances();
					System.out.println(s+": "+freq);
					submap.put(s, Double.valueOf(freq));
					
				}//while
				map.put(data.attribute(j).name(), submap);
			}//if nominal
		}//atts for
		nMap=map;
	}

}
