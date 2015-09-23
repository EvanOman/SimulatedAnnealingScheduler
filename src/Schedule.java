package src;
import java.util.List;
/* For plotting */
import indiji.mlplot.MLPlot;
import java.io.File;

/* The schedule object maintains a list of Machines */
public class Schedule
{
	private List<Machine> machineList;
	private TaskList tList;
	private Permutation perm;
	private double score = 0;

	/* Default constructor simply has a random perm */
	public Schedule(List<Machine> machineList, TaskList tList)
	{
		this.machineList = machineList;
		this.perm = new Permutation(tList.getSize());
		this.tList = new TaskList(this.perm, tList);
		createSchedule();
	}

	/* Takes in a specific permutation to use */
	public Schedule(List<Machine> machineList, TaskList tList, Permutation perm)
	{
		this.machineList = machineList;
		this.perm = perm;
		this.tList = new TaskList(this.perm, tList);
		createSchedule();
	}

	/* Inserts tasks into each of the machines in the given order */
	private void createSchedule()
	{
		for (Task task : tList)
		{
			for (Machine machine : machineList)
			{
				if (machine.addTask(task))
				{
					score += task.getPriority();
					break;
				}
			}
		}

	}

	/* Creates a neighboring schedule */
	public Schedule createNeighbor(List<Machine> mList, TaskList masterList)
	{
		Permutation newPerm = perm.makeMutation();
		return new Schedule(mList, masterList, newPerm);
	}

	public double getScore()
	{
		return score;
	}
	
	/* Creates a visual representation of the schedule */
	public void plot(String fName)
	{
		/* Create a single matrix from all of the collection plans */
		int numWindows = machineList.get(0).getNumSlots();
		int numMachines = machineList.size();
		double[][] collMat = new double[numWindows][numMachines];
		for (int i = 0; i < numMachines; i++)
		{
			int[] row = machineList.get(i).getCollectionPlan();
			for (int j = 0; j < numWindows; j++)
			{
				collMat[j][i] = (double) row[j];
			}
		}
		
		MLPlot p = new MLPlot();
		p.imagesc(collMat);
		p.setTitle("Schedule");

		/* Save Vector Graphic */
		p.save(new File(fName + ".svg"));
	}

}
