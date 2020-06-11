import Util.Tuple;

import java.util.*;

public class EquationsPossible {
    public boolean equationsPossible(String[] equations) {
        if (equations == null || equations.length == 0)
            return true;
        //相等的公式放前面，先处理相等后处理不等
        var equalEquations = new ArrayList<String>();
        var notEqualEquations = new ArrayList<String>();
        for (var equation : equations) {
            if (equation.substring(1, 3).equals("=="))
                equalEquations.add(equation);
            else
                notEqualEquations.add(equation);
        }
        var equationsList = new ArrayList<>(equalEquations);
        equationsList.addAll(notEqualEquations);
        //开始处理所有的equation
        var equalSetList = new ArrayList<HashSet<Character>>();
        for (var equation : equationsList) {
            if (equation.substring(1, 3).equals("==")) {//处理等于的情况
                if (equation.charAt(0) == equation.charAt(equation.length() - 1))//自己一定等于自己
                    continue;
                Integer leftIndex = null, rightIndex = null;
                for (var i = 0; i < equalSetList.size(); i++) {
                    var equalSet = equalSetList.get(i);
                    if (equalSet.contains(equation.charAt(0)))
                        leftIndex = i;
                    if (equalSet.contains(equation.charAt(equation.length() - 1)))
                        rightIndex = i;
                }
                if (leftIndex != null && rightIndex != null && !leftIndex.equals(rightIndex)) {
                    var union = equalSetList.get(leftIndex);
                    union.addAll(equalSetList.get(rightIndex));
                    if (leftIndex < rightIndex) {
                        equalSetList.remove((int) rightIndex);
                        equalSetList.remove((int) leftIndex);
                    } else {
                        equalSetList.remove((int) leftIndex);
                        equalSetList.remove((int) rightIndex);
                    }
                    equalSetList.add(union);
                } else if (leftIndex == null && rightIndex == null) {
                    equalSetList.add(new HashSet<>(new Tuple<>(equation.charAt(0), equation.charAt(equation.length() - 1))));
                    continue;
                } else if (leftIndex == null) {
                    equalSetList.get(rightIndex).add(equation.charAt(0));
                } else if (rightIndex == null) {
                    equalSetList.get(leftIndex).add(equation.charAt(equation.length() - 1));
                }

            } else if (equation.substring(1, 3).equals("!=")) {
                if (equation.charAt(0) == equation.charAt(equation.length() - 1))
                    return false;
                Integer leftIndex = null, rightIndex = null;
                for (int i = 0; i < equalSetList.size(); i++) {
                    var equalSet = equalSetList.get(i);
                    if (equalSet.contains(equation.charAt(0)))
                        leftIndex = i;
                    if (equalSet.contains(equation.charAt(equation.length() - 1)))
                        rightIndex = i;
                }
                if (leftIndex != null && rightIndex != null && leftIndex.equals(rightIndex))
                    return false;
                else if (leftIndex == null && rightIndex == null) {
                    equalSetList.addAll(new Tuple<>(new HashSet<>(new Tuple<>(equation.charAt(0))), new HashSet<>(new Tuple<>(equation.charAt(equation.length() - 1)))));
                    continue;
                } else if (leftIndex == null)
                    equalSetList.add(new HashSet<>(new Tuple<>(equation.charAt(0))));
                else if (rightIndex == null)
                    equalSetList.add(new HashSet<>(new Tuple<>(equation.charAt(equation.length() - 1))));
            } else throw new IllegalArgumentException("只支持包含'=='或'!='的式子");
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a==b", "b!=a"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"b==a", "a==b"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a==b", "b==c", "a==c"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a==b", "b!=c", "c==a"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"c==c", "b==d", "x!=z"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a!=a"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"b!=c", "a==b", "e!=d", "b!=f", "a!=b"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a==b", "b!=c", "c==a"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"c==c", "f!=a", "f==b", "b==c"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"a==b", "b!=c", "c==a"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"b!=f", "c!=e", "f==f", "d==f", "b==f", "a==f"}));
        System.out.println(new EquationsPossible().equationsPossible(new String[]{"i!=c", "i!=f", "k==j", "g==e", "h!=e", "h==d", "j==e", "k==a", "i==h"}));
    }
}
