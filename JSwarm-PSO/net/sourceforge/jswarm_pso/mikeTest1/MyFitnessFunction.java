package sourceforge.jswarm_pso.mikeTest1;

import java.io.File;

import sourceforge.jswarm_pso.FitnessFunction;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class MyFitnessFunction extends FitnessFunction {

	public MyFitnessFunction() {
		// TODO Auto-generated constructor stub
	}

	public MyFitnessFunction(boolean maximize) {
		super(maximize);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double evaluate(double[] position) {			
			int dimensions = Dataset.data.numAttributes()-1;
			int classesNo = Dataset.data.attribute(Dataset.data.classIndex()).numValues();
			double val = 0;
			for(double pos:position){
				val = val+pos-0.5234;
			}
			return val;		
	}

}
