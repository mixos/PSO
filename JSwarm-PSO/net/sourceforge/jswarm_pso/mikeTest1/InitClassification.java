package sourceforge.jswarm_pso.mikeTest1;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import sourceforge.jswarm_pso.Particle;
import sourceforge.jswarm_pso.Swarm;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class InitClassification {
	
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

		int numberOfIterations = 100;
		
		System.out.println("Completed configuration.");		
		// Optimize (and time it)
		
		//Start time
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("PSO Classification Started at: "+dateFormat.format(cal.getTime()));
		System.out.println("Working...");
		
		for( int i = 0; i < numberOfIterations; i++ ){
			swarm.evolve();
		}

		//End time
		cal = Calendar.getInstance();
		System.out.println("PSO Classification finished at: "+dateFormat.format(cal.getTime()));
		
		// Print results
		System.out.println(swarm.toStringStats());
		System.out.println(swarm.printClasses());
		

	}//end main

}// end class
