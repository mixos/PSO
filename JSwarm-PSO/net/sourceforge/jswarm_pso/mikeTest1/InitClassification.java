package sourceforge.jswarm_pso.mikeTest1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import sourceforge.jswarm_pso.Neighborhood;
import sourceforge.jswarm_pso.Neighborhood1D;
import sourceforge.jswarm_pso.Swarm;

public class InitClassification {
	
	private static int NUMNER_OF_PARTICLES = 50;
	//public final static int NUMBER_OF_CLUSTERS = 5;
	public static int current_iteration;
	public static int numberOfIterations;
	public static int numTrainingData;
	public static int numTestData;
	public static int sumOfFold=0;

	public static void main(String[] args) throws Exception {
		//args[0]=inertiaDecrease - args[1]=particleIncrement - args[2]=filepath
		//args[3]=inertia value - args[4]=personal value - args[5]=social value
		//args[6]=iterations - args[7]=folds - args[8]=numberParticles
		//config some args
		double inert = 0.9;
		double pers = 0.3;
		boolean lbest_opt = false;
		double socl = 0.9;
		Integer itons = 200;
		Integer fofolds = 10;
		String apath = null;
		boolean inertia = false;
		boolean localFix = false;
	if(args.length==3){
		inertia = Boolean.parseBoolean(args[0]);
		localFix = Boolean.parseBoolean(args[1]);
		lbest_opt = Boolean.parseBoolean(args[2]);
	}
	if(args.length>3){
		apath = args[3];
		if(args[4]!=null && !args[4].isEmpty()){
			inert = Double.parseDouble(args[4]);
		}
		if(args[5]!=null && !args[5].isEmpty()){
			pers = Double.parseDouble(args[5]);
		}
		if(args[6]!=null && !args[6].isEmpty()){
			socl = Double.parseDouble(args[6]);
		}
		if(args[7]!=null && !args[7].isEmpty()){
			itons = Integer.parseInt(args[7]);
		}
		if(args[8]!=null && !args[8].isEmpty()){
			fofolds = Integer.parseInt(args[8]);
		}
		if(args[9]!=null && !args[9].isEmpty()){
			NUMNER_OF_PARTICLES=Integer.parseInt(args[9]);
		}
		//end config
	}
		
		Dataset.buildDataset(apath);		
		Utils.buildNominalMap(Dataset.data);
		//10-fold validation		
		numTestData = 	Dataset.data.numInstances()/10;
		numTrainingData = Dataset.data.numInstances()-numTestData;
		//
		int folds = fofolds;	
		for(int f=1;f<=folds;f++){
		Swarm swarm = new Swarm(NUMNER_OF_PARTICLES, new ClassificationParticle(), new ClassificationFitness(false));
		//Swarm swarm = new Swarm(Swarm.DEFAULT_NUMBER_OF_PARTICLES, new ClusteringParticle(), new ClusteringFitness(false));
		//System.out.println("dbg");
		
		// Use neighborhood
		if(lbest_opt){
			Neighborhood neigh = new Neighborhood1D(Swarm.DEFAULT_NUMBER_OF_PARTICLES / 5, true);
			swarm.setNeighborhood(neigh);
			swarm.setNeighborhoodIncrement(0.0);
		}
		
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
		swarm.setInertia(inert);
		swarm.setParticleIncrement(pers);
		swarm.setGlobalIncrement(socl);
		swarm.setVariablesUpdate(new InertiaDecrease(inertia,localFix,lbest_opt));

		numberOfIterations = itons;
		
		System.out.println("Completed configuration.");		
		// Optimize (and time it)
		
		//Start time
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		System.out.println("PSO Classification Started at: "+dateFormat.format(cal.getTime()));
		System.out.println("Working...");
		
	//int folds = 1;	
	//for(int f=1;f<=folds;f++){	
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
		
		Dataset.foldDataset();
	}//end folds
	System.out.println("Total: "+(sumOfFold/folds)+"%");
	

	}//end main

}// end class
