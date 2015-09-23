package src;

public class Params
{
	/* Some standard parameters */
	public int numMachines;
	public int windowLen;
	public int numTasks;
	
	public Params(int numMachines, int windowLen, int numTasks)
	{
		this.numMachines = numMachines;
		this.windowLen = windowLen;
		this.numTasks = numTasks;
	}
}
