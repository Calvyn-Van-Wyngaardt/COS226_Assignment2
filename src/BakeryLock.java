package src;
public class BakeryLock
{
	private volatile boolean[] flag;
	private volatile int[] label;

	public BakeryLock(int numThreads)
	{
		flag = new boolean[numThreads];
		label = new int[numThreads];
		for (int i = 0; i < numThreads; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}

	public void lock()
	{
		String name = Thread.currentThread().getName();
		int id = Integer.parseInt(name.substring(7));
		flag[id] = true;
		label[id] = max(label) + 1;
        //int k = 0;
        for (int k = 0; k != id; k++) {
            while ((k != id) && flag[k] && ((label[k] < label[id]) || (label[k] == label[id] && k < id))) {}
        }
	}

	private int max(int[] labelArr)
	{
		int max = labelArr[0];
		for (int i = 1; i < labelArr.length; i++) {
			if (labelArr[i] > max)
				max = labelArr[i];
		}
		return max;
	}

	public void unlock()
	{
		String name = Thread.currentThread().getName();
		int id = Integer.parseInt(name.substring(7, 8));
		flag[id] = false;
	}

}
