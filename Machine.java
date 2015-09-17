import java.util.Arrays;

/* This class represents the plan for a single machine */
public class Machine
{
    private int numSlots;
    private TaskList tList = new TaskList();
    private int[] collectionPlan;

    /* For future:
     *
     * private int beginTime;
     * private int endTime;
     * private OrbitDynamics machOrbitDyn;
     * private int[] 
     */
    
    public Machine(int numSlots)
    {
        this.numSlots = numSlots;
        this.collectionPlan = new int[this.numSlots];
        Arrays.fill(this.collectionPlan, -1);
    }

    /* Attempts to add a task to the task list */
    /* 
     * TODO: This will be drastically different when we move to the sat. scheduling 
     * Instead of making decisions based on the given scheduling window of the task
     * we will be making visibility calculations and trying to fit the task into the 
     * machine's schedule based on those
     *
     */
    public boolean  addTask(Task task)
    {
        int sTime = task.getSTime();
        int eTime = task.getETime();
        int dur = task.getDur();
        int id = task.getID();
        boolean foundFit = false;
        for (int i = sTime; i <= eTime - (dur - 1); i++)
        {
            /* If it fits, we will schedule this task */
            if (doesFit(sTime, eTime, dur))
            {
                tList.addTask(task);
            }
        }

        return foundFit;
    }

    private boolean doesFit(int sTime, int eTime, int dur)
    {
        for (int j = sTime; j < sTime + (dur - 1); j++)
        {
            if (this.collectionPlan[j] != -1)
            {
                return false;
            }
        }
        return true;        
    }
}
