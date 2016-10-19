package chargingScheduler;

import isula.aco.*;
import isula.aco.algorithms.*;
import isula.aco.algorithms.acs.*;
import isula.aco.algorithms.antsystem.*;
import isula.aco.algorithms.maxmin.*;
import isula.aco.exception.*;
import isula.image.*;
import isula.image.util.*;


//import isula.aco.Ant;
//import isula.aco.AntColony;
//import isula.aco.algorithms.acs.PseudoRandomNodeSelection;
//import isula.aco.algorithms.antsystem.StartPheromoneMatrix;
//import isula.aco.aco;
//import isula.aco.acoEnvironment;
//import isula.aco.config.ProblemConfiguration;
//import isula.aco.view.SchedulingFrame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.swing.UnsupportedLookAndFeelException;

/**
 * Applies the MAX-MIN Ant System algorithm to Flow-Shop Problem instance.
 *
 */
public class acostart {

    private static Logger logger = Logger.getLogger(acostart.class
            .getName());

    /**
     * Entry point for this solution.
     *
     * @param args Arguments for the application.
     */
    @SuppressWarnings("unchecked")
    public static void main(String... args) {
        logger.info("ACO FOR CAR SCHEDULLING");
        logger.info("=============================");

        try {
            String fileDataset = ProblemConfiguration.FILE_DATASET;

            double[][] problemRepresentation = getTaillardProblemFromFile(fileDataset, 20, 5);
            logger.info("Data file: " + fileDataset);

            ProblemConfiguration configurationProvider = new ProblemConfiguration(problemRepresentation);
            AntColony<Integer, acoEnvironment> colony = getAntColony(configurationProvider);
           acoEnvironment environment = new acoEnvironment(problemRepresentation);
            configurationProvider.setEnvironment(environment);

            AcoProblemSolver<Integer, acoEnvironment> solver = new AcoProblemSolver<>();
            solver.initialize(environment, colony, configurationProvider);

            solver.solveProblem();
            showSolution(problemRepresentation, solver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static AntColony<Integer, acoEnvironment> getAntColony(ProblemConfiguration configurationProvider) {
        return new AntColony<Integer, acoEnvironment>(configurationProvider.getNumberOfAnts()) {
            @Override
            protected Ant<Integer, acoEnvironment> createAnt(acoEnvironment environment) {
                ACO ant = new ACO(environment.getNumberOftimes());
                return ant;
            }
        };
    }

    private static void showSolution(final double[][] graph,
                                     final AcoProblemSolver<Integer, acoEnvironment> problemSolver)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, UnsupportedLookAndFeelException {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                .getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SchedulingFrame frame = new SchedulingFrame();
                frame.setSolutionMakespan(problemSolver.getBestSolutionCost());

                frame.setProblemGraph(graph);
                frame.setSolution(problemSolver.getBestSolution());
                frame.setVisible(true);

            }
        });
    }


    /**
     * Reads a scheduling instance in the format used in Taillard's benchmarks
     * @param fileName         File name
     * @param numberOftimes    times to consider.
     * @param numberOfcars cars available.
     * @return Matrix representation of the problem.
     * @throws IOException
     */
  

    /**
     * Reads a text file and returns a problem matrix.
     *
     * @param path File to read.
     * @return Problem matrix.
     * @throws IOException If an error produces while reading the file.
     */
    public static double[][] getProblemGraphFromFile(String path)
            throws IOException {
        double[][] graph = null;
        FileReader fr = new FileReader(path);
        BufferedReader buf = new BufferedReader(fr);
        String line;
        int index = 0;

        while ((line = buf.readLine()) != null) {
            if (index > 0) {
                String[] splitA = line.split(" ");
                LinkedList<String> split = new LinkedList<>();
                for (String s : splitA) {
                    if (!s.isEmpty()) {
                        split.add(s);
                    }
                }
                int column = 0;
                for (String anString : split) {
                    if (!anString.isEmpty()) {
                        graph[index - 1][column++] = Integer.parseInt(anString);
                    }
                }
            } else {
                String[] firstLine = line.split(" ");
                String numberOftimes = firstLine[0];
                String numberOfcars = firstLine[1];

                logger.info("numberOftimes=" + numberOftimes + ", numberOfcars="
                        + numberOfcars);

                if (graph == null) {
                    graph = new double[Integer.parseInt(numberOftimes)][Integer
                            .parseInt(numberOfcars)];
                }
            }
            index++;
        }

        buf.close();
        return graph;
    }
}