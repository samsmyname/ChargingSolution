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
	private static final int MAX_ALLOWED_EVOLUTIONS = 100;
	
	String finalSolution = "";

	public JGAP(List<Car> cars) throws InvalidConfigurationException {
		int numberCars = cars.size();

		Configuration conf = new DefaultConfiguration();
		Configuration.reset();
		int targetAmount = 20;
		FitnessFunction myFunc = new JGAPFitness(targetAmount, cars);

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
			System.out.println("Best So far: " + population.getFittestChromosome());
		}

		bestSolutionSoFar = population.getFittestChromosome();

		
		for (int i = 0; i < 24; i++) {
			finalSolution += JGAPFitness.getCarAtGene(bestSolutionSoFar, i) + " ";
			System.out.print("");
		}
		
		System.out.println("Final Solution: " + finalSolution);
		
		UI.displaySchedules(finalSolution);
	}
}
