public class Task
{
    private int id;
    private int duration;
    private int sTime;
    private int eTime;
    
    /* Future 
    
    Task type

    */

    public Task(int id, int duration, int sTime, int eTime)
    {
        this.id = id;
        this.duration = duration;
        this.sTime = sTime;
        this.eTime = eTime;
    }

    public String toString()
    {
        return "TaskID:\t"+this.id+"\nDur:\t"+this.duration+"\nWindow:\t"+this.sTime+" - "+this.eTime;
    }


}
