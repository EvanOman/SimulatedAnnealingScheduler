package src;

//package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Permutation implements Iterable<Integer>
{
	private int numItems;
	private List<Integer> permArr;
	private List<Integer> sortedArr;

	public Permutation(List<Integer> permArr)
	{
		this.numItems = permArr.size();
		this.permArr = new ArrayList<Integer>(permArr);
		this.sortedArr = getSortedArr();
	}

	public Permutation(int numItems)
	{
		this.numItems = numItems;
		this.permArr = filled0s(this.numItems);

		consecPerm();
		this.randPermute();

		this.sortedArr = getSortedArr();
	}

	public Permutation(int numItems, boolean isConsec)
	{
		this.numItems = numItems;
		this.permArr = filled0s(this.numItems);

		consecPerm();

		/* Need to scramble the list if we want a random permutation */
		if (!isConsec)
		{
			this.randPermute();
		}

		this.sortedArr = getSortedArr();
	}

	private void consecPerm()
	{
		/* Populate permArr with integers 0 -> n-1 */
		for (int i = 0; i < this.numItems; i++)
		{
			this.permArr.set(i, i);
		}

	}

	private List<Integer> getSortedArr()
	{
		List<Integer> ret = getPermArr();
		Collections.sort(ret);
		return ret;
	}

	private List<Integer> filled0s(int numItems)
	{
		List<Integer> out = Arrays.asList(new Integer[numItems]);
		return out;
	}

	@Override
	public Iterator<Integer> iterator()
	{
		return permArr.iterator();
	}

	public void randPermute()
	{
		/* Make a random permutation */
		Collections.shuffle(permArr);
	}

	public Permutation makeMutation()
	{
		/* We will use swap mutations with 1 to 15 swaps per mutation */
		List<Integer> mutPermArr = this.getPermArr();

		Random rg = new Random();

		int numSwaps = rg.nextInt(15);

		for (int i = 0; i < numSwaps; i++)
		{
			/* This is my hack for getting two random, ind. indices */
			int ind1 = rg.nextInt(mutPermArr.size());
			int ind2 = ind1;
			while (ind1 == ind2)
			{
				ind2 = rg.nextInt(mutPermArr.size());
			}

			int tmp = mutPermArr.get(ind1);
			mutPermArr.set(ind1, mutPermArr.get(ind2));
			mutPermArr.set(ind2, tmp);
		}
		return new Permutation(mutPermArr);
	}

	@Override
	public Permutation clone()
	{
		return new Permutation(this.permArr);
	}

	public List<Integer> getPermArr()
	{
		List<Integer> out = new ArrayList<Integer>();

		for (int i : this.permArr)
		{
			out.add(i);
		}
		return out;
	}

	/* Returns the size(number of elements) of the permArr */
	public int getSize()
	{
		return this.numItems;
	}

	/* Gives the integer at index "index" */
	public int getInt(int index)
	{
		return this.permArr.get(index);
	}

	/* Adds the newInt to the end of the list while respecting uniqueness */
	public void addInt(int newInt)
	{
		this.permArr.add(newInt);
		this.sortedArr = getSortedArr();
	}

	/* Uses binrary search to find the inInt */
	public boolean hasInt(int inInt)
	{
		/*
		 * If the inInt is in the list then the binary search should return an
		 * index >= 0
		 */
		return (Collections.binarySearch(this.sortedArr, inInt) >= 0);
	}

	/*
	 * Uses a crossover technique to vreate an offspring permutation from this
	 * permutation and perm2
	 */
	// TODO: Implement crossover (or remove if needed)
	public Permutation crossover(Permutation perm2)
	{
		return perm2;
	}

	public String toString()
	{
		return Arrays.toString(permArr.toArray());
	}
}
