package src;
public class Task
{
	private int id;
	private int duration;
	private int sTime;
	private int eTime;
	private int priority;

	/*
	 * Future
	 * 
	 * Task type
	 * 
	 */

	public Task(int id, int duration, int sTime, int eTime, int priority)
	{
		this.id = id;
		this.duration = duration;
		this.sTime = sTime;
		this.eTime = eTime;
		this.priority = priority;
	}

	public String toString()
	{
		return "TaskID:\t" + this.id + "\nDur:\t" + this.duration + "\nWindow:\t" + this.sTime + " - " + this.eTime;
	}

	public int getID()
	{
		return this.id;
	}

	public int getSTime()
	{
		return this.sTime;
	}

	public int getETime()
	{
		return this.eTime;
	}

	public int getDur()
	{
		return this.duration;
	}
	
	public int getPriority()
	{
		return this.priority;
	}
}
