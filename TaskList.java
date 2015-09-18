import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TaskList implements Iterable<Task>
{
	private List<Task> tList;

	/* Default constructor, just makes an empty list */
//	public TaskList()
//	{
//		this.tList = new ArrayList<Task>();
//	}

	/*
	 * TODO: This task generation should probably be moved to a SCENARIO class
	 */
	/* makes a list of random tasks (which fit into the total window) */
	public TaskList(int totalWindowSize, int numTasks)
	{
		/* Makes a permutation which is just consecutive numbers */
		Permutation tPerm = new Permutation(numTasks, true);
		this.tList = new ArrayList<Task>(Arrays.asList(new Task[numTasks]));

		/*
		 * For now we will make tasks with durations up to a third of the window
		 * size
		 */
		Random rg = new Random();

		for (int i : tPerm)
		{
			int dur = rg.nextInt((totalWindowSize - 1) / 3) + 1;
			int sTime = rg.nextInt((int) ((totalWindowSize - 1) * (2.0 / 3.0)));
			int eTime = Math.min(sTime + dur + 3, totalWindowSize - 1);

			/* For now the priority and the ID will be the same */
			tList.set(i, new Task(i, dur, sTime, eTime, i));
		}
	}

	public TaskList(Permutation tPerm, TaskList masterList)
	{
		this.tList = masterList.getList();
		
		for (int i = 0; i < tPerm.getSize(); i++)
		{
			this.tList.set(tPerm.getInt(i), masterList.getTask(i));
		}
	}

	public void addTask(Task t)
	{
		this.tList.add(t);
	}

	public Task getTask(int index)
	{
		return this.tList.get(index);
	}

	public int getSize()
	{
		return this.tList.size();
	}

	public List<Task> getList()
	{
		List<Task> output = new ArrayList<Task>();
		
		for (Task t : this)
		{
			output.add(t);
		}
		return output;
	}
	
	public String toString()
	{
		String output = "";
		for (Task task : this)
		{
			output += task.toString() + "\n";
		}
		return output;
	}

	@Override
	public Iterator<Task> iterator()
	{
		return tList.iterator();
	}
}
