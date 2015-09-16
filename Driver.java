//package src;

public class Driver
{
    public static void main(String[] args)
    {
        System.out.println("Hello world!!");

        Permutation t = new Permutation(10);

        System.out.println(t);
        
        for (int i = 0; i < 10; i++)
        {
            t.randPermute();
            System.out.println(t);
        }

        TaskList tL = new TaskList(100,100);
    }
}
