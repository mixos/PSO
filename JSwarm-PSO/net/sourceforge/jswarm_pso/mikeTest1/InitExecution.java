package sourceforge.jswarm_pso.mikeTest1;

import java.io.File;
import java.util.HashMap;

import sourceforge.jswarm_pso.Particle;
import sourceforge.jswarm_pso.Swarm;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class InitExecution {
	
	private final static int NUMNER_OF_PARTICLES = 50;
	public final static int NUMBER_OF_CLUSTERS = 5;

	public static void main(String[] args) throws Exception {		
		Dataset.buildDataset();		
		Utils.buildNominalMap(Dataset.data);
		Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new ClassificationParticle(), new ClassificationFitness(false));
		//Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new ClusteringParticle(), new ClusteringFitness(false));
		//System.out.println("dbg");
		// Min / Max possition
		double[] minPos = Utils.minValues(Dataset.data);
		double[] maxPos = Utils.maxValues(Dataset.data);
		swarm.setMaxPosition(maxPos);
		swarm.setMinPosition(minPos);
		swarm.setInertia(0.95); // Optimization parameters

		int numberOfIterations = 1000;
		
		System.out.println("Completed configuration.");
		System.out.println("PSO Process Started...");
		// Optimize (and time it)
		for( int i = 0; i < numberOfIterations; i++ ){
			swarm.evolve();
		}

		// Print results
		System.out.println(swarm.toStringStats());
		System.out.println(swarm.printClasses());
		System.out.println("PSO finished.");
		

	}//end main

}// end class
