import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
// 神策数据：神策数（求满足3**any*5**any*11**any的第i个数）
public class ShenCeShu {
    private static int pow(int a, int b) {
        return (int) Math.pow(a, b);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] maxCount = new int[3];
        maxCount[0] = n / 2;
        maxCount[1] = maxCount[2] = n / 4;
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i_3 = 0; i_3 <= maxCount[0]; i_3++) {
            for (int i_5 = 0; i_5 <= maxCount[1]; i_5++) {
                for (int i_11 = 0; i_11 <= maxCount[2]; i_11++) {
                    hashSet.add(pow(3, i_3) * pow(5, i_5) * pow(11, i_11));
                }
            }
        }
        if (n > hashSet.size() / 2) {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>((o1, o2) -> o2 - o1);
            priorityQueue.addAll(hashSet);
            for (int heapLen = priorityQueue.size(); heapLen > n; heapLen--) {
                priorityQueue.poll();
            }
            System.out.println(priorityQueue.peek());
        } else {
            PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(hashSet);
            for (int i = 0; i < n - 1; i++) {
                priorityQueue.poll();
            }
            System.out.println(priorityQueue.peek());
        }
    }
}
