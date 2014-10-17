package sourceforge.jswarm_pso.mikeTest1;

import sourceforge.jswarm_pso.Particle;

public class ClusteringParticle extends Particle {

	public ClusteringParticle() {
		super(Dataset.dimensions*InitClassification.NUMBER_OF_CLUSTERS);
	}

}
