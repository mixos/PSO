package sourceforge.jswarm_pso.mikeTest1;

import sourceforge.jswarm_pso.Particle;

public class ClusteringParticle extends Particle {

	public ClusteringParticle() {
		super(Dataset.dimensions*InitExecution.NUMBER_OF_CLUSTERS);
	}

}
