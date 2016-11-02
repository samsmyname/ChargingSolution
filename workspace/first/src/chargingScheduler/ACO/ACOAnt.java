package chargingScheduler.ACO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import isula.aco.Ant;
import isula.aco.AntPolicy;
import isula.aco.AntPolicyType;
import isula.aco.algorithms.antsystem.*;

import org.apache.commons.math3.ml.distance.EuclideanDistance;

public class ACOAnt extends Ant<Integer, ACOEnvironment>{

	private static final double VALUE_NOT_USED = 1.0;

    /**
     * Creates an Ant specialized in the Flow Shop Scheduling problem.
     *
     * @param graphLenght Number of rows of the Problem Graph.
     */
    public ACOAnt(int graphLenght) {
        super();
        this.setSolution(new Integer[graphLenght]);
        this.setVisited(new HashMap<Integer, Boolean>());
    }

    @Override
    public boolean isSolutionReady(ACOEnvironment environment) {
        return getCurrentIndex() == environment.getNumberOfJobs();
    }

    /**
     * Gets the makespan of the Ants built solution.
     *
     * @return Makespan of the solution.
     */
    @Override
    public double getSolutionCost(ACOEnvironment environment) {
        return getScheduleMakespan(getSolution(), environment.getProblemGraph());
    }

    @Override
    public Double getHeuristicValue(Integer solutionComponent,
                                    Integer positionInSolution, ACOEnvironment environment) {
        return VALUE_NOT_USED;
    }

    @Override
    public List<Integer> getNeighbourhood(ACOEnvironment environment) {
        List<Integer> neighbours = new ArrayList<>();
        for (int l = 0; l < environment.getNumberOfJobs(); l++) {
            neighbours.add(l);
        }
        return neighbours;
    }

    @Override
    public Double getPheromoneTrailValue(Integer solutionComponent,
                                         Integer positionInSolution, ACOEnvironment environment) {

        double[][] pheromoneMatrix = environment.getPheromoneMatrix();
        return pheromoneMatrix[solutionComponent][positionInSolution];
    }

    @Override
    public void setPheromoneTrailValue(Integer solutionComponent, Integer positionInSolution,
    		ACOEnvironment environment, Double value) {
        double[][] pheromoneMatrix = environment.getPheromoneMatrix();

        pheromoneMatrix[solutionComponent][positionInSolution] = value;
    }

    /**
     * Calculates the MakeSpan for the generated schedule.
     *
     * @param schedule Schedule
     * @param jobInfo  Job Info.
     * @return Makespan.
     */
    public static double getScheduleMakespan(Integer[] schedule, double[][] jobInfo) {
        int machines = jobInfo[0].length;
        double[] machinesTime = new double[machines];
        double tiempo = 0;

        for (Integer job : schedule) {
            for (int i = 0; i < machines; i++) {
                tiempo = jobInfo[job][i];
                if (i == 0) {
                    machinesTime[i] = machinesTime[i] + tiempo;
                } else {
                    if (machinesTime[i] > machinesTime[i - 1]) {
                        machinesTime[i] = machinesTime[i] + tiempo;
                    } else {
                        machinesTime[i] = machinesTime[i - 1] + tiempo;
                    }
                }
            }
        }
        return machinesTime[machines - 1];
    }

}
