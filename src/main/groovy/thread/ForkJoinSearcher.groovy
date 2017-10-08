package thread

/**
 * Created by sajedur on 9/1/2016.
 */
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

public class ForkJoinSearcher extends RecursiveTask<Boolean> {
    int[] arr;
    int searchableElement;

    ForkJoinSearcher(int[] arr, int search) {
        this.arr = arr;
        this.searchableElement = search;
    }

    @Override
    protected Boolean compute() {
        int mid = (0 + arr.length) / 2;
        System.out.println(Thread.currentThread().getName() + " says : After splliting the arry length is :: " + arr.length + " Midpoint is " + mid);
        if (arr[mid] == searchableElement) {
            System.out.println(" FOUND !!!!!!!!!");
            return true;
        } else if (mid == 1 || mid == arr.length) {
            System.out.println("NOT FOUND !!!!!!!!!");
            return false;
        } else if (searchableElement < arr[mid]) {
            System.out.println(Thread.currentThread().getName() + " says :: Doing Left-search");
            int[] left = Arrays.copyOfRange(arr, 0, mid);
            ForkJoinSearcher forkTask = new ForkJoinSearcher(left, searchableElement);
            forkTask.fork();
            return forkTask.join();
        } else if (searchableElement > arr[mid]) {
            System.out.println(Thread.currentThread().getName() + " says :: Doing Right-search");
            int[] right = Arrays.copyOfRange(arr, mid, arr.length);
            ForkJoinSearcher forkTask = new ForkJoinSearcher(right, searchableElement);
            forkTask.fork();
            return forkTask.join();
        }
        return false;
    }
}

public class BinarySearch {
    int[] arr = new int[100];

    public BinarySearch() {
        init();
    }

    private void init() {
        for (int i = 0; i < arr.length; i++) {
            arr[i];
        }
        Arrays.sort(arr);
    }

    public void createForJoinPool(int search) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(50);
        ForkJoinSearcher searcher = new ForkJoinSearcher(this.arr, search);
        Boolean status = forkJoinPool.invoke(searcher);
        System.out.println(" Element ::" + search + " has been found in array? :: " + status);
    }

    public static void main(String[] args) {
        BinarySearch search = new BinarySearch();
        search.createForJoinPool(10);
        System.out.println("**********************");
        search.createForJoinPool(104);
    }
}
