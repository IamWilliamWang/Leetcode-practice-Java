package Util;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public class TreeUtil {
    /**
     * 根据heap创建二叉树，返回根部节点
     * @param numberList 储存数据的heap
     * @param <T>
     * @return
     */
    public static <T> TreeNode<T> createTreeByHeap(List<T> numberList) {
        ArrayList<TreeNode<T>> nodeHeap = new ArrayList<>(numberList.size());
        for (var x : numberList) {
            if (x != null)
                nodeHeap.add(new TreeNode<>(x));
            else
                nodeHeap.add(null);
        }
        assert numberList.size() == nodeHeap.size();
        for (var i : IntStream.range(0, nodeHeap.size()).toArray()) {
            if (nodeHeap.get(i) == null)
                continue;
            if ((i + 1) * 2 - 1 < nodeHeap.size())
                nodeHeap.get(i).left = nodeHeap.get((i + 1) * 2 - 1);
            if ((i + 1) * 2 < nodeHeap.size())
                nodeHeap.get(i).right = nodeHeap.get((i + 1) * 2);
        }
        return nodeHeap.isEmpty() ? null : nodeHeap.get(0);
    }

    /**
     * 利用树的先序遍历和中序遍历结果来创建对应的二叉树
     * @param preorder 先序遍历结果
     * @param inorder  中序遍历结果
     * @param <T>
     * @return
     */
    public static <T> TreeNode<T> createTreeByPreInOrder(List<T> preorder, List<T> inorder) {
        if (preorder == null || inorder == null)
            return null;
        var root = new TreeNode<>(preorder.get(0));
        var inorderRootIndex = inorder.indexOf(preorder.get(0));
        var inorderLeftTree = inorder.subList(0, inorderRootIndex);
        if (inorderRootIndex > 0)
            root.left = createTreeByPreInOrder(preorder.subList(1, 1 + inorderLeftTree.size()), inorderLeftTree);
        var inorderRightTree = inorder.subList(inorderRootIndex + 1, inorder.size());
        if (1 + inorderLeftTree.size() < preorder.size())
            root.right = createTreeByPreInOrder(preorder.subList(1 + inorderLeftTree.size(), preorder.size()), inorderRightTree);
        return root;
    }

    /**
     * 先序遍历，返回遍历结果
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> List<T> preOrderTraversal(TreeNode<T> rootNode) {
        var result = new ArrayList<T>();
        result.add(rootNode.val);
        if (rootNode.left != null)
            result.addAll(preOrderTraversal(rootNode.left));
        if (rootNode.right != null)
            result.addAll(preOrderTraversal(rootNode.right));
        return result;
    }

    /**
     * 先序遍历，返回指向节点的迭代器（速度慢，不推荐使用）
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> Iterator<TreeNode<T>> preOrderTraversalIterator(TreeNode<T> rootNode) {
        if (rootNode == null)
            return null;
        return new Iterator<>() {
            private int status = 0;
            private Iterator<TreeNode<T>> leftTreeIt;
            private Iterator<TreeNode<T>> rightTreeIt;

            @Override
            public boolean hasNext() {
                if (leftTreeIt == null)
                    leftTreeIt = preOrderTraversalIterator(rootNode.left);
                if (rightTreeIt == null)
                    rightTreeIt = preOrderTraversalIterator(rootNode.right);
                if (status == 0)
                    return true;
                if (status == 1 && (leftTreeIt == null || !leftTreeIt.hasNext()))
                    status++;
                if (status == 2 && (rightTreeIt == null || !rightTreeIt.hasNext()))
                    status++;
                if (status > 2)
                    return false;
                return true;
            }

            @Override
            public TreeNode<T> next() {
                if (!hasNext())
                    return null;
                if (status == 0) {
                    status++;
                    return rootNode;
                }
                if (status == 1)
                    return leftTreeIt.next();
                if (status == 2)
                    return rightTreeIt.next();
                return null;
            }
        };
    }

    /**
     * 中序遍历，返回遍历结果
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> List<T> inOrderTraversal(TreeNode<T> rootNode) {
        var result = new ArrayList<T>();
        var stack = new Stack<TreeNode<T>>();
        var nowNode = rootNode;
        while (nowNode != null) {
            stack.push(nowNode);
            nowNode = nowNode.left;
        }
        while (!stack.isEmpty()) {
            var node = stack.pop();
            result.add(node.val);
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
        }
        return result;
    }

    /**
     * 中序遍历，返回指向节点的迭代器（速度慢，不推荐使用）
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> Iterator<TreeNode<T>> inOrderTraversalIterator(TreeNode<T> rootNode) {
        if (rootNode == null)
            return null;
        return new Iterator<>() {
            private int status = 0;
            private Iterator<TreeNode<T>> leftTreeIt;
            private Iterator<TreeNode<T>> rightTreeIt;

            @Override
            public boolean hasNext() {
                if (leftTreeIt == null)
                    leftTreeIt = inOrderTraversalIterator(rootNode.left);
                if (rightTreeIt == null)
                    rightTreeIt = inOrderTraversalIterator(rootNode.right);
                if (status == 0 && (leftTreeIt == null || !leftTreeIt.hasNext()))
                    status++;
                if (status == 1)
                    return true;
                if (status == 2 && (rightTreeIt == null || !rightTreeIt.hasNext()))
                    status++;
                if (status > 2)
                    return false;
                return true;
            }

            @Override
            public TreeNode<T> next() {
                if (!hasNext())
                    return null;
                if (status == 0)
                    return leftTreeIt.next();
                if (status == 1) {
                    status++;
                    return rootNode;
                }
                if (status == 2)
                    return rightTreeIt.next();
                return null;
            }
        };
    }

    /**
     * 后序遍历，返回遍历结果
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> List<T> postOrderTraversal(TreeNode<T> rootNode) {
        var result = new ArrayList<T>();
        if (rootNode.left != null)
            result.addAll(postOrderTraversal(rootNode.left));
        if (rootNode.right != null)
            result.addAll(postOrderTraversal(rootNode.right));
        result.add(rootNode.val);
        return result;
    }

    /**
     * 后序遍历，返回指向节点的迭代器（速度慢，不推荐使用）
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> Iterator<TreeNode<T>> postOrderTraversalIterator(TreeNode<T> rootNode) {
        if (rootNode == null)
            return null;
        return new Iterator<>() {
            private int status = 0;
            private Iterator<TreeNode<T>> leftTreeIt;
            private Iterator<TreeNode<T>> rightTreeIt;

            @Override
            public boolean hasNext() {
                if (leftTreeIt == null)
                    leftTreeIt = postOrderTraversalIterator(rootNode.left);
                if (rightTreeIt == null)
                    rightTreeIt = postOrderTraversalIterator(rootNode.right);
                if (status == 0 && (leftTreeIt == null || !leftTreeIt.hasNext()))
                    status++;
                if (status == 1 && (rightTreeIt == null || !rightTreeIt.hasNext()))
                    status++;
                if (status == 2)
                    return true;
                if (status > 2)
                    return false;
                return true;
            }

            @Override
            public TreeNode<T> next() {
                if (!hasNext())
                    return null;
                if (status == 2) {
                    status++;
                    return rootNode;
                }
                if (status == 0)
                    return leftTreeIt.next();
                if (status == 1)
                    return rightTreeIt.next();
                return null;
            }
        };
    }

    /**
     * 广度优先遍历，返回二维数组，一维是每层的元素List，二维是第几层
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> breadthFirstTraversal(TreeNode<T> rootNode) {
        var resultList = new ArrayList<List<T>>();
        if (rootNode == null)
            return resultList;
        Queue<TreeNode<T>> queueNode = new ArrayDeque<>();
        Queue<Integer> queueLevel = new ArrayDeque<>();
        queueNode.offer(rootNode);
        queueLevel.offer(1);
        while (!queueNode.isEmpty()) {
            var node = queueNode.poll();
            var level = queueLevel.poll();
            if (level > resultList.size()) {
                var newList = new ArrayList<T>();
                newList.add(node.val);
                resultList.add(newList);
            } else {
                resultList.get(resultList.size() - 1).add(node.val);
            }
            if (node.left != null) {
                queueNode.offer(node.left);
                queueLevel.offer(level + 1);
            }
            if (node.right != null) {
                queueNode.offer(node.right);
                queueLevel.offer(level + 1);
            }
        }
        return resultList;
    }

    /**
     * 根据val寻找节点，如果没找到就返回null
     * @param rootNode 根节点
     * @param val      要找的节点储存的val
     * @param <T>
     * @return
     */
    public static <T> TreeNode<T> findNodeByVal(TreeNode<T> rootNode, T val) {
        var iterator = preOrderTraversalIterator(rootNode);
        while (iterator.hasNext()) {
            var node = iterator.next();
            if (node.val.equals(val))
                return node;
        }
        return null;
    }

    /**
     * 计算二叉树的最大层数
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> int maxLevel(TreeNode<T> rootNode) {
        if (rootNode == null)
            return 0;
        return breadthFirstTraversal(rootNode).size();
    }

    private static <T> void assign(List<T> valList, int index, T element, int maxLevel) {
        if (index >= valList.size()) {
            for (var i : IntStream.range(0, (int) Math.pow(2, maxLevel) - 1 - valList.size()).toArray())
                valList.add(null);
        }
        valList.set(index, element);
    }

    /**
     * 将根节点代表的树转换成存储各个节点的val的堆
     * @param rootNode 根节点
     * @param <T>
     * @return
     */
    public static <T> List<T> toValHeap(TreeNode<T> rootNode) {
        if (rootNode == null)
            return new ArrayList<>();
        var maxLevel = 1;
        var valList = new ArrayList<T>();
        for (var i : IntStream.range(0, (int) Math.pow(2, maxLevel) - 1).toArray())
            valList.add(null);
        Queue<TreeNode<T>> queueNode = new ArrayDeque<>();
        Queue<Integer> queueLevel = new ArrayDeque<>();
        Queue<Integer> queueIndex = new ArrayDeque<>();
        queueNode.offer(rootNode);
        queueLevel.offer(1);
        queueIndex.offer(0);
        while (!queueNode.isEmpty()) {
            var node = queueNode.poll();
            var level = queueLevel.poll();
            var index = queueIndex.poll();
            maxLevel = Math.max(maxLevel, level);
            assign(valList, index, node.val, maxLevel);
            if (node.left != null) {
                queueNode.offer(node.left);
                queueLevel.offer(level + 1);
                queueIndex.offer((index + 1) * 2 - 1);
            }
            if (node.right != null) {
                queueNode.offer(node.right);
                queueLevel.offer(level + 1);
                queueIndex.offer((index + 1) * 2);
            }
        }
        return valList;
    }

    /**
     * 将二叉树转换为字符串，空缺的位置用'X'代替
     * @param rootNode 要转化为字符串的节点
     * @param <T>
     * @return
     */
    public static <T> String toString(TreeNode<T> rootNode) {
        return toString(rootNode, "X");
    }

    /**
     * 将二叉树转换为字符串
     * @param rootNode    要转化为字符串的节点
     * @param nonReplacer 如果遍历到空缺的节点，用什么代替
     * @param <T>
     * @return
     */
    public static <T> String toString(TreeNode<T> rootNode, String nonReplacer) {
        if (rootNode == null)
            return "";
        var valList = toValHeap(rootNode);
        var string = new StringBuilder();
        for (var i : IntStream.range(0, valList.size()).toArray()) {
            if (valList.get(i) != null)
                string.append(valList.get(i) + " ");
            else
                string.append(nonReplacer + " ");
            int log2 = (int) (Math.log(i + 2) / Math.log(2));
            if ((int) (Math.pow(2, log2)) == (i + 2))
                string.append("\n");
        }
        var lines = string.toString().trim().split("\n");
        for (var i : IntStream.range(0, lines.length - 1).toArray()) {
            var spaceBeforeRow = new StringBuilder();
            for (var j : IntStream.range(0, (lines[lines.length - 1].length() - lines[i].length()) / 2).toArray())
                spaceBeforeRow.append(" ");
            lines[i] = spaceBeforeRow.toString() + lines[i];
        }
        return String.join("\n", lines);
    }

    /**
     * 用于测试新方法的正确性
     * @param args
     */
    public static void main(String[] args) {
        // 测试createTreeByPreInOrder
        System.out.println("------ testing createTreeByPreInOrder ------");
        var preorder = new ArrayList<Integer>();
        Collections.addAll(preorder, -5, -1, -4, -5, -5, 0, -1, -3, -5, -4, 2);
        var inorder = new ArrayList<Integer>();
        Collections.addAll(inorder, -1, -5, -5, -5, -4, -1, -3, -5, -4, 0, 2);
        var root = TreeUtil.createTreeByPreInOrder(preorder, inorder);
        // 测试toString
        System.out.println("------ testing toString ------");
        System.out.println(root);
        // 测试createTreeByHeap
        System.out.println("------ testing createTreeByHeap ------");
        System.out.println(root = TreeUtil.createTreeByHeap(preorder));
        // 测试breadthFirstTraversal
        System.out.println("------ testing breadthFirstTraversal ------");
        System.out.println(TreeUtil.breadthFirstTraversal(root));
        // 测试findNodeByVal
        System.out.println("------ testing findNodeByVal ------");
        System.out.println(TreeUtil.findNodeByVal(root, -3));
        // 测试inOrderTraversal
        System.out.println("------ testing inOrderTraversal ------");
        System.out.println("root's inOrder:");
        System.out.println(TreeUtil.inOrderTraversal(root));
        // 测试inOrderTraversalIterator
        System.out.println("------ testing inOrderTraversalIterator ------");
        var inOrderIt = TreeUtil.inOrderTraversalIterator(root);
        while (inOrderIt.hasNext())
            System.out.print(inOrderIt.next().val + ", ");
        System.out.println();
        // 测试maxLevel
        System.out.println("------ testing maxLevel ------");
        System.out.println(TreeUtil.maxLevel(root));
        // 测试postOrderTraversal
        System.out.println("------ testing postOrderTraversal ------");
        System.out.println("root's postOrder:");
        System.out.println(TreeUtil.postOrderTraversal(root));
        var postOrderIt = TreeUtil.postOrderTraversalIterator(root);
        while (postOrderIt.hasNext())
            System.out.print(postOrderIt.next().val + ", ");
        System.out.println();
        // 测试preOrderTraversal
        System.out.println("------ testing preOrderTraversal ------");
        System.out.println("root's preOrder:");
        System.out.println(TreeUtil.preOrderTraversal(root));
        var preOrderIt = TreeUtil.preOrderTraversalIterator(root);
        while (preOrderIt.hasNext())
            System.out.print(preOrderIt.next().val + ", ");
        System.out.println();
        // 测试for-each
        System.out.println("------ testing for-each ------");
        System.out.print("root's for-each:");
        for (var node : root)
            System.out.print(node.val + ", ");
        System.out.println();
        // 测试toValHeap
        System.out.println("------ testing toValHeap ------");
        System.out.println(TreeUtil.toValHeap(root));
    }
}
