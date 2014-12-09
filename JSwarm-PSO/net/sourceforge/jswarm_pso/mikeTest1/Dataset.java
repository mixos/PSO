package sourceforge.jswarm_pso.mikeTest1;

import java.io.File;
import java.util.LinkedList;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Dataset {
	
	public static Instances data;
	public static Integer dimensions;
	public static Integer classesNo;
	
	public static void buildDataset(String arffpath) throws Exception{
		DataSource source;
		if("".equals(arffpath)){
			File[] roots = File.listRoots();
			String os = System.getProperty("os.name");
			String sep = File.separator;
			String rt = os.startsWith("Windows")?roots[0].toString():File.separator;
			//DataSource source = new DataSource(rt+"Users"+sep+"mchristopoulos"+sep+"Downloads"+sep+"PSO"+sep+"datasets-UCI"+sep+"UCI"+sep+"waveform-5000.arff");
			//DataSource source = new DataSource(rt+"Users"+sep+"mchristopoulos"+sep+"Downloads"+sep+"PSO"+sep+"datasets-UCI"+sep+"UCI"+sep+"sonar.arff");
			source = new DataSource(rt+"Users"+sep+"mchristopoulos"+sep+"Downloads"+sep+"PSO"+sep+"datasets-UCI"+sep+"UCI"+sep+"lymph.arff");
			//DataSource source = new DataSource("D:\\UOA\\Diplwmatiki\\PSO\\arffFiles\\UCI\\waveform-5000.arff");
			//DataSource source = new DataSource("C:\\Users\\mike\\Downloads\\PSO\\WekaFiles\\UCI\\sonar.arff");
		}else{
			if(arffpath.endsWith(".arff")){
				source = new DataSource(arffpath);
			}else{
				source = new DataSource(arffpath+".arff");
			}
		}
		
		data = source.getDataSet();
		// setting class attribute if the data format does not provide this information
		// For example, the XRFF format saves the class attribute information as well
		if (data.classIndex() == -1){
		  data.setClassIndex(data.numAttributes() - 1);
		}
		//make them static
		dimensions = data.numAttributes()-1;
		classesNo = data.attribute(data.classIndex()).numValues();
		
	}
	
	public static void foldDataset(){
		LinkedList<Instance> link = new LinkedList<Instance>();
		for(int i=0;i<data.numInstances();i++){
			link.add(data.instance(i));
		}
		
		//for(int j=link.size()-1;j>=link.size()-(link.size()/10);j--){
		for(int j=0;j<(data.numInstances()/10);j++){
			Instance temp = link.removeFirst();
			link.add(temp);
		}
		
		//data=null;
		for(int k=0;k<link.size();k++){
			Instance temp2 = link.get(0);
			data.delete(k);
			data.add(temp2);
		}
		
		if (data.classIndex() == -1){
			  data.setClassIndex(data.numAttributes() - 1);
		}
		
		
	}

}
