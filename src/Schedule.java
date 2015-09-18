package src;
import java.util.ArrayList;
import java.util.List;

/* The schedule object maintains a list of Machines */
public class Schedule
{
	private List<Machine> machineList = new ArrayList<Machine>();
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
	public Schedule createNeighbor()
	{
		Permutation newPerm = perm.makeMutation();
		return new Schedule(machineList, tList, newPerm);
	}

	public double getScore()
	{
		return score;
	}

}
