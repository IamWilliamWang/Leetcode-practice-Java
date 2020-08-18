import java.util.*;
import java.util.stream.Collectors;

public class ValidateStackSequences {
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        // 把int[]转成List
        List<Integer> push = Arrays.stream(pushed).boxed().collect(Collectors.toList());
        List<Integer> pop = Arrays.stream(popped).boxed().collect(Collectors.toList());

        Stack<Integer> stack = new Stack<>();
        // push或者pop不为空
        while (!push.isEmpty() && !pop.isEmpty()) {
            if (stack.isEmpty() || !stack.peek().equals(pop.get(0))) // 入栈的条件：myStack是空的或者不满足出栈的条件
                stack.add(push.remove(0));
            else { // 出栈的条件：栈不为空，而且满足出栈的条件
                stack.pop();
                pop.remove(0);
            }
        }
        if (push.isEmpty()) {
            if (stack.size() != pop.size())
                return false;
            while (!stack.isEmpty()) {
                if (!stack.pop().equals(pop.remove(0))) // 不满足出栈顺序
                    return false;
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.print(new ValidateStackSequences().validateStackSequences(new int[]{1,2,3,4,5},new int[]{4,5,3,2,1}));
    }
}
