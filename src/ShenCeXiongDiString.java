import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
// 神策数据：兄弟字符串（解释：a字符串中交换俩字符变成b）
public class ShenCeXiongDiString {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String in1 = sc.nextLine();
        HashSet<Character> in1Set = new HashSet<>();
        String in2 = sc.nextLine();
        if (in1.length() != in2.length()) {
            System.out.println("false");
            return;
        }
        ArrayList<Integer> diffIdxes = new ArrayList<>();
        for (int i = 0; i < in1.length(); i++) {
            in1Set.add(in1.charAt(i));
            if (in1.charAt(i) != in2.charAt(i))
                diffIdxes.add(i);
        }
        if (diffIdxes.size() == 2) {
            int idx1 = diffIdxes.get(0), idx2 = diffIdxes.get(1);
            if (in1.charAt(idx1) == in2.charAt(idx2) && in1.charAt(idx2) == in2.charAt(idx1)) {
                System.out.println("true");
                return;
            }
            System.out.println("false");
            return;
        }
        if (diffIdxes.size() == 0 && in1Set.size() != in1.length()) {
            System.out.println("true");
            return;
        }
        System.out.println("false");
    }
}
