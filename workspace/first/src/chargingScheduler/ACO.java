package chargingScheduler;

import isula.aco.Ant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * An Ant that belongs to Colony in the context of ACO.
 *
 */
public class ACO extends Ant<Integer, acoEnvironment> {

    private static final double VALUE_NOT_USED = 1.0;

    /**
     * Creates an Ant specialized in the Scheduling problem.
     *
     */
    public ACO(int graphLenght) {
        super();
        this.setSolution(new Integer[graphLenght]);
        this.setVisited(new HashMap<Integer, Boolean>());
    }

    @Override
    public boolean isSolutionReady(acoEnvironment environment) {
        return getCurrentIndex() == environment.getNumberOftimes();
    }

    /**
     * Gets the makespan of the Ants built solution.
     *
     * @return Makespan of the solution.
     */
    @Override
    public double getSolutionCost(acoEnvironment environment) {
        return getScheduleMakespan(getSolution(), environment.getProblemGraph());
    }

    @Override
    public Double getHeuristicValue(Integer solutionComponent,
                                    Integer positionInSolution, acoEnvironment environment) {
        return VALUE_NOT_USED;
    }

    @Override
    public List<Integer> getNeighbourhood(acoEnvironment environment) {
        List<Integer> neighbours = new ArrayList<>();
        for (int l = 0; l < environment.getNumberOftimes(); l++) {
            neighbours.add(l);
        }
        return neighbours;
    }

    
    /**
     * Calculates the MakeSpan for the generated schedule.
     *
     */
    public static double getScheduleMakespan(Integer[] schedule, double[][] timeInfo) {
        int cars = timeInfo[0].length;
        double[] carsTime = new double[cars];
        double tiempo = 0;

        for (Integer time : schedule) {
            for (int i = 0; i < cars; i++) {
                tiempo = timeInfo[time][i];
                if (i == 0) {
                    carsTime[i] = carsTime[i] + tiempo;
                } else {
                    if (carsTime[i] > carsTime[i - 1]) {
                    	carsTime[i] = carsTime[i] + tiempo;
                    } else {
                    	carsTime[i] = carsTime[i - 1] + tiempo;
                    }
                }
            }
        }
        return carsTime[cars - 1];
    }

	@Override
	public Double getPheromoneTrailValue(Integer arg0, Integer arg1, acoEnvironment arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPheromoneTrailValue(Integer arg0, Integer arg1, acoEnvironment arg2, Double arg3) {
		// TODO Auto-generated method stub
		
	}

}