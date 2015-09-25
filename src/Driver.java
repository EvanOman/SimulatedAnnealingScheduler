package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;


/* For plotting */
import indiji.mlplot.MLPlot;

public class Driver
{
	public static void main(String[] args)
	{
		/* Read params in from the param file */
		Params params;
		try
		{
			params = loadParams("scenario1_params.txt");
		} catch (IOException e1)
		{
			System.out.println("Unable to load params, exiting");
			return;
		}
		
		/* Making random tasks for our master task list */
		TaskList tList;
		try
		{
			tList = loadTaskList(params.windowLen);
		} catch (IOException e1)
		{
			System.out.println("Unable to load task list, exiting");
			return;
		}
		
		int maxIts = 1000000;

		Schedule sol1 = runSimAnn(tList, params.numMachines, params.windowLen, maxIts);

	}

	/* Starts with a random initial schedule */
	public static Schedule runSimAnn(TaskList masterTList, int numMachines, int windowLen, int maxIts)
	{
		Schedule initSched = new Schedule(makeMachineList(numMachines, windowLen), masterTList);
		return runSimAnn(initSched, masterTList, numMachines, windowLen, maxIts);
	}

	/* Starts with the supplied schedule */
	public static Schedule runSimAnn(Schedule initSched, TaskList masterTList, int numMachines, int windowLen,
			int maxIts)
	{
		Schedule currSched = initSched;
		double currScore = currSched.getScore();
		System.out.println("Initial Score: " + currScore);
		Random rg = new Random();
		List<Double> scores = new ArrayList<Double>(maxIts);
		String time = currentTime("M-dd-yyyy@HH:mm:ss");
		masterTList.plot(time + "-Availability");
		long startTime1 = System.nanoTime();
		for (int i = 0; i < maxIts; i++)
		{
			/*
			 * TODO: probably don't want to be initializing a new neighbor every
			 * iteration, some sort of copying would be swell
			 */

			Schedule neighbor = currSched.createNeighbor(makeMachineList(numMachines, windowLen), masterTList);
			double neighborScore = neighbor.getScore();

			if (neighborScore > currScore)
			{
				currSched = neighbor;
				currScore = neighborScore;
			} else if (neighborScore < currScore)
			{
				double randNum = rg.nextDouble();

				/* Calculates the current prob of accepting poor option */
				double prob = Math.exp((neighborScore - currScore) / coolingFunc(i, maxIts));

				if (randNum < prob)
				{
					currSched = neighbor;
					currScore = neighborScore;
				}
			}
			i++;
			scores.add(currScore);

		}
		long startTime2 = System.nanoTime();
		long timeSpan = startTime2 - startTime1;
		System.out.println(maxIts + " Iterates Took " + timeSpan / 1e9 + " Seconds ");

		plotData(scores, time + "-ScoreHistory");
		currSched.plot(time + "-Schedule");
		System.out.println("Final Score: " + currScore);
		return currSched;
	}

	public static double coolingFunc(int iterate, int maxIts)
	{
		/* Figures out what 100th we are in */
		double power = Math.floor((double) iterate / ((double) maxIts / 100.0));
		return 100 * Math.pow(.92, power);
	}

	public static void plotData(List<Double> points, String fName)
	{
		/* Create sequence data */
		double x[] = new double[points.size()];
		for (int i = 0; i < x.length; i++)
		{
			x[i] = (double) i;
		}

		/* Convert from List<Double> to double[] */
		double y[] = new double[points.size()];
		for (int i = 0; i < y.length; i++)
		{
			y[i] = points.get(i).doubleValue();
		}

		/* Create a new Plot */
		MLPlot p = new MLPlot();

		/* Draw data and set the title */
		p.linePlot(x, y, "blue", MLPlot.Style.Solid, MLPlot.Symbol.CircleFilled, "Score");
		p.setTitle("Simulated Annealing Solution History");

		/* Save Vector Graphic */
		p.save(new File(fName + ".svg"));

	}

	public static <T> List<T> listCopy(List<T> original)
	{
		List<T> ret = new ArrayList<T>();
		for (T thing : original)
		{
			ret.add(thing);
		}
		return ret;
	}

	public static String currentTime(String format)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(cal.getTime());
		return time;
	}

	public static List<Machine> makeMachineList(int numMachines, int windowLen)
	{
		/* We will use identical machines for now */
		List<Machine> ret = new ArrayList<Machine>();
		for (int i = 0; i < numMachines; i++)
		{
			ret.add(new Machine(windowLen));
		}

		return ret;
	}

	public static Params loadParams(String fName) throws IOException
	{
		/* Instantiate file reading machinery */
		FileInputStream fis = new FileInputStream(fName);
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		/* Each line is a new param */
		int numMachines = 	Integer.parseInt(br.readLine());
		int windowLen   = 	Integer.parseInt(br.readLine());
		int numTasks    =	Integer.parseInt(br.readLine());
		
		br.close();
		
		return new Params(numMachines, windowLen, numTasks);
	}

	public static TaskList loadTaskList(int windowLen) throws IOException
	{
		List<Task> runningList = new ArrayList<Task>();

		/* Instantiate file reading machinery */
		FileInputStream fis = new FileInputStream("scenario1_tasks.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));

		/* Now we read the file line by line */
		String line = null;
		while ((line = br.readLine()) != null)
		{
			/*
			 * Right now line is a comma serpared list in the order:
			 * id,priority,duration,sT,eT
			 */
			String[] params = line.split("\\,");
			int id = Integer.parseInt(params[0]);
			int pr = Integer.parseInt(params[1]);
			int du = Integer.parseInt(params[2]);
			int sT = Integer.parseInt(params[3]);
			int eT = Integer.parseInt(params[4]);
			runningList.add(new Task(id, du, sT, eT, pr));
		}

		br.close();
		return new TaskList(runningList, windowLen);
	}
}
