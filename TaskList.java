import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TaskList implements Iterable<Task>
{
    private List<Task> tList; 
    private Permutation tPerm;

    /* Default constructor, makes a list of random tasks (which fit into the total window) */
    public TaskList(int totalWindowSize, int numTasks)
    {
        /* Makes a permutation which is just consecutive numbers */
        this.tPerm = new Permutation(totalWindowSize, true); 
        this.tList = new ArrayList<Task>(numTasks);

        /* For now we will make tasks with durations up to a third of the window size */
        Random rg = new Random();

        for (int i : this.tPerm)
        {
            int dur = rg.nextInt((totalWindowSize - 1) / 3);
            int sTime = rg.nextInt((int)((totalWindowSize - 1) * (2.0 / 3.0)));
            int eTime = Math.min(sTime + dur + 3, totalWindowSize - 1);
            tList.add(i, new Task(i, dur, sTime, eTime));
            System.out.println(tList.get(i));
        }
    }

    public TaskList(Permutation tPerm, TaskList masterList)
    {
        //this.tPerm = tPerm;
        //this.tList
    }
    

    @Override 
    public Iterator<Task> iterator()
    {
        return tList.iterator();
    }
}
