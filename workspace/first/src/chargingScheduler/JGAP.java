package chargingScheduler;


import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class JGAP {
	
	public JGAP(int numberCars) throws InvalidConfigurationException
	{
		Configuration conf = new DefaultConfiguration();

		  int targetAmount = 20;
		  FitnessFunction myFunc = new ScheduleFitness( targetAmount );

		  
		  conf.setFitnessFunction( myFunc );

		  Gene[] scheduleGenes = new Gene[ 24 ];
		  
		  for (int i=0; i<24; i++)
		  {
			  scheduleGenes[i] = new IntegerGene(conf, 0, numberCars );  
		  }

		  Chromosome scheduleChromosone = new Chromosome(conf, scheduleGenes );

		  conf.setSampleChromosome( scheduleChromosone );

		  conf.setPopulationSize( 500 );

		  // TODO: Add the code following below in this example here

	}

	  
}
