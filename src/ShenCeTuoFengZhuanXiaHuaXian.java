import java.util.Scanner;
// 神策数据：驼峰转下划线（AString -> a_string）
public class ShenCeTuoFengZhuanXiaHuaXian {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        StringBuilder ans = new StringBuilder();
        int startAt = -1;
        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                if (startAt != -1) {
                    ans.append(input.substring(startAt, i).toLowerCase());
                    ans.append('_');
                }
                startAt = i;
            }
        }
        ans.append(input.substring(startAt).toLowerCase());
        System.out.println(ans);
    }
}