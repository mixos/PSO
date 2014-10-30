package sourceforge.jswarm_pso.mikeTest1;

import java.io.File;

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

}
