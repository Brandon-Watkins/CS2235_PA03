package edu.isu.cs2235;

import edu.isu.cs2235.structures.impl.DoublyLinkedList;
import edu.isu.cs2235.structures.impl.LinkedDeque;
import edu.isu.cs2235.structures.impl.LinkedQueue;
import edu.isu.cs2235.structures.impl.LinkedStack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class representing a wait time simulation program.
 *
 * @author Isaac Griffith
 * @author Brandon Watkins
 */
public class Simulation <E extends Comparable> extends LinkedQueue<E> {

    private int arrivalRate;
    private int maxNumQueues;
    private Random r;
    private int numIterations = 50;
    // You will probably need more fields
    private String[] fixtureStrings = {"Linked Queue", "Linked Deque", "Linked Stack"};
    private Integer simDuration = 720;

    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the current time. This defaults to using 50 iterations.
     *
     * @param arrivalRate the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     */
    public Simulation(int arrivalRate, int maxNumQueues) {
        this.arrivalRate = arrivalRate;

        this.maxNumQueues = maxNumQueues;
        r = new Random();
    }

    /**
     * determines which fixture to add to the array list
     *
     * @param fixtureNumber refers to the list type (as ordered in the fixtureStrings field)
     * @return returns the fixture to create
     */
    public DoublyLinkedList<Integer> addFixture(int fixtureNumber){
        if (fixtureNumber == 0) return(new LinkedQueue<>());
        else if (fixtureNumber == 1) return(new LinkedDeque<>());
        else if (fixtureNumber == 2) return(new LinkedStack<>());
        else throw new IllegalArgumentException("fixtureNumber should be between 0 and 2.");
    }

    /**
     * Constructs a new simulation with the given arrival rate and maximum number of queues. The Random
     * number generator is seeded with the provided seed value, and the number of iterations is set to
     * the provided value.
     *
     * @param arrivalRate the integer rate representing the maximum number of new people to arrive each minute
     * @param maxNumQueues the maximum number of lines that are open
     * @param numIterations the number of iterations used to improve data
     * @param seed the initial seed value for the random number generator
     */
    public Simulation(int arrivalRate, int maxNumQueues, int numIterations, int seed) {
        this(arrivalRate, maxNumQueues);
        r = new Random(seed);
        this.numIterations = numIterations;
    }

    public static void main(String[] args){
        Simulation s = new Simulation(18, 10, 50, 1024);
        s.runSimulation();
    }

    /**
     * Executes the Simulation
     */
    public void runSimulation() {
        System.out.println("Wait Time Simulation Results:");

        // For each list structure (fixture)...
        for (int i = 0; i < fixtureStrings.length; i++) {
            // Display the List structure that was utilized.
            System.out.println("Utilizing " + fixtureStrings[i] + "s:");
            // Display the arrival rate
            System.out.println("Arrival Rate: " + arrivalRate + ".");

            // For each set of maximum number of lines available...
            for (int j = 1; j <= maxNumQueues; j++){
                ArrayList<DoublyLinkedList<Integer>> lines = new ArrayList();
                int averageWaitTime = 0;

                // For each iteration(50, for a good average)...
                for (int k = 0; k < numIterations; k++){
                    int waitTime = 0;
                    int leftLine = 0;
                    //Create the lines for the simulation
                    for (int h = 0; h < j; h++) lines.add(addFixture(i));

                    // For each minute in the simulation...
                    for (int currentTime = 0; currentTime < simDuration; currentTime++){

                        // Random number of people want to join a line
                        int numPeople = getRandomNumPeople(arrivalRate);
                        // For each new person, join the line that is currently shortest. Save the time that they enter.
                        for (int p = 0; p < numPeople; p++){
                            // Find shortest line
                            int shortestLine = 0;
                            for (int s = 1; s < j; s++) if (lines.get(s).size() < lines.get(shortestLine).size()) shortestLine = s;
                            // Enter shortest line
                            lines.get(shortestLine).enterLine(currentTime);
                        }

                        // For each line...
                        for (int n = 0; n < lines.size(); n++){
                            // For each of the first 2 people in each line...
                            for (int p = 0; p < 2 && lines.get(n).first() != null; p++){
                                // Determine wait time, leave line
                                int timeTheyEnteredLine = lines.get(n).leaveLine();
                                int waited = currentTime - timeTheyEnteredLine;
                                // Update average wait time
                                waitTime += waited;
                                // Update number of people cycled through the lines
                                leftLine++;
                            }
                        }
                    }

                    // Clear the lines for the simulation
                    lines.clear();
                    averageWaitTime += waitTime / leftLine;
                }

                // Update the Total Average Time spent waiting in line (by people who have already left the line)
                int totalAverageWaitTime = averageWaitTime / numIterations;
                String q = "" + j + " queue";
                if (j > 1) q += "s";
                System.out.println("Average wait time using " + q + ":  " + totalAverageWaitTime + ".");
            }
            System.out.println("");
        }
    }

    /**
     * returns a number of people based on the provided average
     *
     * @param avg The average number of people to generate
     * @return An integer representing the number of people generated this minute
     */
    //Don't change this method.
    private static int getRandomNumPeople(double avg) {
        Random r = new Random();
        double L = Math.exp(-avg);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        } while (p > L);
        return k - 1;
    }
}
