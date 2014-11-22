package sourceforge.jswarm_pso.mikeTest1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sourceforge.jswarm_pso.Neighborhood;
import sourceforge.jswarm_pso.Neighborhood1D;
import sourceforge.jswarm_pso.Swarm;

public class InitClassification {
	
	private final static int NUMNER_OF_PARTICLES = 50;
	//public final static int NUMBER_OF_CLUSTERS = 5;
	public static int current_iteration;
	public static int numberOfIterations;
	public static int numTrainingData;
	public static int numTestData;
	public static int sumOfFold=0;

	public static void main(String[] args) throws Exception {		
		Dataset.buildDataset();		
		Utils.buildNominalMap(Dataset.data);
		//10-fold validation		
		numTestData = 	Dataset.data.numInstances()/10;
		numTrainingData = Dataset.data.numInstances()-numTestData;
		//
		Swarm swarm = new Swarm(NUMNER_OF_PARTICLES, new ClassificationParticle(), new ClassificationFitness(false));
		//Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new ClusteringParticle(), new ClusteringFitness(false));
		//System.out.println("dbg");
		
		// Use neighborhood
		//Neighborhood neigh = new Neighborhood1D(Swarm.DEFAULT_NUMBER_OF_PARTICLES / 5, true);
		//swarm.setNeighborhood(neigh);
		//swarm.setNeighborhoodIncrement(0.9);
		
		// Min / Max possition
		double[] minPos = Utils.minValues(Dataset.data);
		double[] maxPos = Utils.maxValues(Dataset.data);
		swarm.setMaxPosition(maxPos);
		swarm.setMinPosition(minPos);
		//swarm.setMaxMinVelocity(0.5);
		
		if(Utils.debug){
			System.out.println("Max Pos: "+swarm.getMaxPosition().length);
			System.out.println("Min Pos "+swarm.getMinPosition().length);
			System.out.println("Max Vel "+swarm.getMaxVelocity().length);
			System.out.println("Min Vel "+swarm.getMinVelocity().length);
			System.out.println("Dimensions "+Dataset.dimensions);
			System.out.println("Classes "+Dataset.classesNo);
			System.out.println("= "+Dataset.dimensions*Dataset.classesNo);
		}
		
		//optimization params
		swarm.setInertia(0.9);
		swarm.setParticleIncrement(0.5);
		swarm.setGlobalIncrement(0.8);
		swarm.setVariablesUpdate(new InertiaDecrease());

		numberOfIterations = 20;
		
		System.out.println("Completed configuration.");		
		// Optimize (and time it)
		
		//Start time
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("PSO Classification Started at: "+dateFormat.format(cal.getTime()));
		System.out.println("Working...");
		
	int folds = 1;	
	for(int f=1;f<=folds;f++){	
		for( int i = 0; i < numberOfIterations; i++ ){
			current_iteration = i+1;
			swarm.evolve();
		}	

		//End time.
		cal = Calendar.getInstance();
		System.out.println("PSO Classification finished at: "+dateFormat.format(cal.getTime()));
		
		// Print results
		System.out.println(swarm.toStringStats());
		System.out.println(swarm.printClasses());
		
		TestPhase test = new TestPhase();
		test.goTest(swarm.getBestPosition());
		
		//Dataset.foldDataset();
	}
	System.out.println("Total: "+sumOfFold/folds);
	

	}//end main

}// end class
