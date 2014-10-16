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
		Dataset.buildDataset();		
		Utils.buildNominalMap(Dataset.data);
		Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new MyParticle(), new MyFitnessFunction(false));
		//System.out.println("dbg");
		// Min / Max possition
		double[] minPos = Utils.minValues(Dataset.data);
		double[] maxPos = Utils.maxValues(Dataset.data);
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
