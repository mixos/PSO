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
	
	public static void buildDataset() throws Exception{
		File[] roots = File.listRoots();
		String os = System.getProperty("os.name");
		String sep = File.separator;
		String rt = os.startsWith("Windows")?roots[0].toString():File.separator;
		DataSource source = new DataSource(rt+"Users"+sep+"mchristopoulos"+sep+"Downloads"+sep+"PSO"+sep+"datasets-UCI"+sep+"UCI"+sep+"waveform-5000.arff");
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
		for(int j=0;j<data.numInstances()/10;j++){
			Instance temp = link.removeFirst();
			link.add(temp);
		}
		
		data=null;
		for(int k=0;k<link.size();k++){
			System.out.println("f");
			data.add(link.removeFirst());
		}
		
		if (data.classIndex() == -1){
			  data.setClassIndex(data.numAttributes() - 1);
		}
		
		
	}

}
