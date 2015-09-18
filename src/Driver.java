package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.erichseifert.gral.data.DataTable;

public class Driver
{
	public static void main(String[] args)
	{
		/*
		 * This is where we will be generating the scenario and implementing the
		 * simulated annealing (for now)
		 */
		
		/* Some standard parameters */
		int numMachines = 10;
		int windowLen = 50;
		int numTasks = 100;
		int numIts = 5000;
		
		/* Making random tasks for our master task list */
		TaskList tList = new TaskList(windowLen, numTasks);
		
		
		/* This is our random initial schedule */
		Schedule currSched = new Schedule(makeMachineList(numMachines, windowLen), tList);
		double currScore = currSched.getScore();
		System.out.println("Initial Score: " + currScore);
		int i = 0;
		Random rg = new Random();
		List<Double> scores = new ArrayList<Double>();
		
		/* This is the main loop */
		while (i < numIts)
		{
			/* TODO: probably don't want to be initializing a new neighbor every iteration */
			Schedule neighbor = currSched.createNeighbor(makeMachineList(numMachines, windowLen), tList);
			double neighborScore = neighbor.getScore();
			
			if (neighborScore > currScore)
			{
				currSched = neighbor;
				currScore = neighborScore;
			}
			else if (neighborScore < currScore)
			{
				double randNum = rg.nextDouble();
				
				/* Calculates the current prob of accepting poor option */
				double prob = Math.exp( (neighborScore - currScore)/ coolingFunc(i, numIts));
				
				if (randNum < prob)
				{
					currSched = neighbor;
					currScore = neighborScore;
				}
			}
			i++;
			scores.add(currScore);
		}
		plotData(scores);
		System.out.println("Final Score: " + currScore);
	}
	
	public static double coolingFunc(int iterate, int maxIts)
	{
		/* Figures out what 100th we are in */
		double power = Math.floor((double) iterate / ((double) maxIts / 100.0));
		return 100 * Math.pow(.92, power);
	}
	
	public static void plotData(List<Double> points)
	{
		DataTable data = new DataTable(Integer.class, Double.class);
		
		for (int x = 0; x < points.size(); x++)
		{
			Double y = points.get(x);
//			System.out.println("Adding point (" + x + ", " + y + ")");
			data.add(x, y);
		}
		
		LinePlotTest frame = new LinePlotTest(data);
		frame.setVisible(true);
	}
	
	public static <T> List<T> listCopy(List<T> original)
	{
		
		return original;
	}
	
	public static List<Machine> makeMachineList(int numMachines, int windowLen/* maybe some scenario params*/)
	{
		/* We will use identical machines for now */
		return new ArrayList<Machine>(Collections.nCopies(numMachines, new Machine(windowLen)));
	}
 }
