//package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Permutation implements Iterable<Integer>
{
    private int numItems;
    private List<Integer> permArr;


    public Permutation(int numItems)
    {
        this.numItems = numItems;
        this.permArr = new ArrayList<Integer>(this.numItems);

        /* Populate permArr with integers 0 -> n-1 */
        for (int i = 0; i < this.numItems; i++)
        {
            this.permArr.add(i, i);
        }

        this.randPermute();
    }

    @Override 
    public Iterator<Integer> iterator()
    {
        return permArr.iterator();
    }

    public void randPermute()
    {
        /* Make a random permutation  */
        Collections.shuffle(permArr);
    }

    public Permutation makeMutation()
    {
        return this;   
    }

    public Permutation crossover(Permutation perm2)
    {
        return perm2;
    }

    public String toString()
    {
        return Arrays.toString(permArr.toArray());
    }
}
