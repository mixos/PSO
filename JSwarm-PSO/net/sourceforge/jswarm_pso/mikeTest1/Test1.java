package sourceforge.jswarm_pso.mikeTest1;

import java.io.File;
import java.util.HashMap;

import sourceforge.jswarm_pso.Particle;
import sourceforge.jswarm_pso.Swarm;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Test1 {
	
	private final static int NUMNER_OF_PARTICLES = 50;

	public static void main(String[] args) throws Exception {		
		/*File[] roots = File.listRoots();
		String os = System.getProperty("os.name");
		String sep = File.separator;
		String rt = os.startsWith("Windows")?roots[0].toString():File.separator;
		DataSource source = new DataSource(rt+"Users"+sep+"mchristopoulos"+sep+"Downloads"+sep+"PSO"+sep+"datasets-UCI"+sep+"UCI"+sep+"anneal.arff");
		Instances data = source.getDataSet();
		// setting class attribute if the data format does not provide this information
		// For example, the XRFF format saves the class attribute information as well
		if (data.classIndex() == -1){
		  data.setClassIndex(data.numAttributes() - 1);
		}*/
		Dataset.buildDataset();
		int dimensions = Dataset.data.numAttributes()-1;
		int classesNo = Dataset.data.attribute(Dataset.data.classIndex()).numValues();
		HashMap<String,HashMap<String,Double>> nMap = Utils.buildNominalMap(Dataset.data,dimensions,classesNo);
		//System.out.println(dimensions*classesNo);
		Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new MikeParticle(dimensions*classesNo), new MyFitnessFunction(false));
		//System.out.println("dbg");
		// Min / Max possition
		double[] minPos = Utils.minValues(Dataset.data,dimensions,classesNo);
		double[] maxPos = Utils.maxValues(Dataset.data,dimensions,classesNo);
		swarm.setMaxPosition(maxPos);
		swarm.setMinPosition(minPos);
		swarm.setInertia(0.95); // Optimization parameters

		int numberOfIterations = 1000;

		// Optimize (and time it)
		for( int i = 0; i < numberOfIterations; i++ ){			
			swarm.evolve();
		}

		// Print results
		System.out.println(swarm.toStringStats());
		System.out.println("End: Test 1");		
		

	}//end main

}// end class
