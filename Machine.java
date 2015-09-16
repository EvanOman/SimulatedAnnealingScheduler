/* This class represents the plan for a single machine */
public class Machine
{
    private int numSlots;
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
    }

    /* Returns the start index of the fitted task, -1 it doesnt fit*/
    public int addTask(Task newTask)
    {
        return -1;
    }
}
