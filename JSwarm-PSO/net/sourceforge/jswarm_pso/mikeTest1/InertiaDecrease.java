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
		swarm.setInertia( 0.99 * swarm.getInertia() );
	}

}
