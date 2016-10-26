package chargingScheduler;

import java.util.List;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.FitnessFunction;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.IChromosome;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;

public class JGAP {
	private static final int MAX_ALLOWED_EVOLUTIONS = 50;

	public JGAP(List<Car> cars) throws InvalidConfigurationException {
		int numberCars = cars.size();

		Configuration conf = new DefaultConfiguration();
		Configuration.reset();
		int targetAmount = 20;
		FitnessFunction myFunc = new ScheduleFitness(targetAmount, cars);

		conf.setFitnessFunction(myFunc);

		Gene[] scheduleGenes = new Gene[24];
		for (int i = 0; i < 24; i++) {
			scheduleGenes[i] = new IntegerGene(conf, 0, numberCars);
		}

		Chromosome scheduleChromosone = new Chromosome(conf, scheduleGenes);

		conf.setSampleChromosome(scheduleChromosone);

		conf.setPopulationSize(500);

		Genotype population = Genotype.randomInitialGenotype(conf);

		IChromosome bestSolutionSoFar;

		System.out.println("Calculating the best solution...");

		for (int i = 0; i < MAX_ALLOWED_EVOLUTIONS; i++) {
			population.evolve();
		}

		bestSolutionSoFar = population.getFittestChromosome();

		System.out
				.println("The best solution contained the following schedule: ");

		String finalSolution = "";
		for (int i = 0; i < 24; i++) {
			finalSolution += ScheduleFitness.getCarAtGene(bestSolutionSoFar, i) + " ";
		}
		ChargingSheduleGUI csGUI = new ChargingSheduleGUI(finalSolution);
		
		System.out.println("Final Solution: "+finalSolution);
		
		
		System.out.println("");
		System.out.println("The best solution schedule had the following fitness value: ");
		System.out.println(myFunc.getFitnessValue(bestSolutionSoFar));
	}
}
