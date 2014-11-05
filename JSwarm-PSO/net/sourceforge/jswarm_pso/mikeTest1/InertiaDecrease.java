package sourceforge.jswarm_pso.mikeTest1;

import sourceforge.jswarm_pso.Swarm;
import sourceforge.jswarm_pso.VariablesUpdate;

public class InertiaDecrease extends VariablesUpdate {

	public InertiaDecrease() {
		super();
	}	

	/**
	 * Update Swarm parameters here
	 * @param swarm : Swarm to update
	 */
	public void update(Swarm swarm) {		
		swarm.setInertia( 0.9 - ((0.9-0.4)*InitClassification.current_iteration)/InitClassification.numberOfIterations );
		//swarm.setInertia(0.99 * swarm.getInertia());
		
		//swarm.setGlobalIncrement(5 - ((5-1)*InitClassification.current_iteration)/InitClassification.numberOfIterations);
		//swarm.setParticleIncrement(1 + ((3-1)*InitClassification.current_iteration)/InitClassification.numberOfIterations);
		
		//debug
		if(Utils.debug){
			System.out.println("Inertia: "+swarm.getInertia());
			System.out.println("Global Incr: "+swarm.getGlobalIncrement());
			System.out.println("Particle Incr: "+swarm.getParticleIncrement());
		}
	}

}
