package Util;

import java.math.BigInteger;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.IntStream;

/**
 * 常用二叉树算法的实现，以下是简单介绍：
 * 1、createTreeByHeap 输入树的val堆，输出二叉树的根节点
 * 2、createTreeByHeapExpressedByDict 输入val堆的(index, val)字典，输出二叉树的根节点(用于超大型树)
 * 3、createTreeByPreInOrder 输入先序遍历和中序遍历，输出二叉树的根节点
 * 4、preOrderTraversal 先序遍历，输出遍历结果的List
 * 5、preOrderTraversalIterator 先序遍历，返回遍历的迭代器
 * 6、inOrderTraversal 中序遍历，输出遍历结果的List
 * 7、inOrderTraversalIterator 中序遍历，返回遍历的迭代器
 * 8、postOrderTraversal 后序遍历，输出遍历结果的List
 * 9、postOrderTraversalIterator 后序遍历，返回遍历的迭代器
 * 10、breadthFirstTraversal 输入二叉树的根节点，输出广度优先BFS的遍历结果。结果是二维数组
 * 11、findNodeByVal 输入根节点和val值，查找对应节点并返回
 * 12、maxLevel 输入根节点，输出树的深度
 * 13、toValHeap 输入二叉树的根节点，输出val堆。（此方法和createTreeByHeap互逆）
 * 14、toValHeapToDict 输入二叉树的根节点，输出val堆对应的字典。（此方法和createTreeByHeapExpressedByDict互逆）
 * 15、toString 输入二叉树的根节点，输出这颗树的可视化字符串
 * @author William Wang. June 7th, 2020.
 * @version v2.0
 */
public class TreeUtil {
    /**
     * 根据heap创建二叉树，返回根部节点
     *
     * @param numberList 储存数据的heap
     * @return
     */
    public static <T> TreeNode<T> createTreeByHeap(List<T> numberList) {
        ArrayList<TreeNode> nodeHeap = new ArrayList<>(numberList.size());
        for (T x : numberList) {
            if (x != null)
                nodeHeap.add(new TreeNode<>(x));
            else
                nodeHeap.add(null);
        }
        assert numberList.size() == nodeHeap.size();
        for (int i : IntStream.range(0, nodeHeap.size()).toArray()) {
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
     * 根据使用dict表达的heap创建二叉树，返回根部节点
     *
     * @param numberDict 储存数据的heap对应的dict（index: val）
     * @return
     */
    public static <T> TreeNode<T> createTreeByHeapExpressedByDict(Map<BigInteger, T> numberDict) {
        Map<BigInteger, TreeNode<T>> nodeHeapDict = new HashMap<>();
        for (Entry<BigInteger, T> entry : numberDict.entrySet()) // 把index: val变成index: TreeNode(val)
            nodeHeapDict.put(entry.getKey(), new TreeNode<>(entry.getValue()));
        for (BigInteger i : numberDict.keySet()) {
            // (i + 1) * 2 - 1
            BigInteger leftTreeIndex = i.add(BigInteger.ONE).multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(-1));
            if (nodeHeapDict.containsKey(leftTreeIndex)) // 连接每个元素的左子树
                nodeHeapDict.get(i).left = nodeHeapDict.get(leftTreeIndex);
            // (i + 1) * 2
            BigInteger rightTreeIndex = i.add(BigInteger.ONE).multiply(BigInteger.valueOf(2));
            if (nodeHeapDict.containsKey(rightTreeIndex)) // 连接每个元素的右子树
                nodeHeapDict.get(i).right = nodeHeapDict.get(rightTreeIndex);
        }
        return nodeHeapDict.isEmpty() ? null : nodeHeapDict.get(BigInteger.ZERO);
    }

    /**
     * 利用树的先序遍历和中序遍历结果来创建对应的二叉树
     *
     * @param preorder 先序遍历结果
     * @param inorder  中序遍历结果
     * @return
     */
    public static <T> TreeNode<T> createTreeByPreInOrder(List<T> preorder, List<T> inorder) {
        if (preorder == null || inorder == null)
            return null;
        TreeNode<T> root = new TreeNode<>(preorder.get(0));
        int inorderRootIndex = inorder.indexOf(preorder.get(0));
        List<T> inorderLeftTree = inorder.subList(0, inorderRootIndex);
        if (inorderRootIndex > 0)
            root.left = createTreeByPreInOrder(preorder.subList(1, 1 + inorderLeftTree.size()), inorderLeftTree);
        List<T> inorderRightTree = inorder.subList(inorderRootIndex + 1, inorder.size());
        if (1 + inorderLeftTree.size() < preorder.size())
            root.right = createTreeByPreInOrder(preorder.subList(1 + inorderLeftTree.size(), preorder.size()),
                    inorderRightTree);
        return root;
    }

    /**
     * 先序遍历，返回遍历结果
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> List<T> preOrderTraversal(TreeNode<T> rootNode) {
        ArrayList<T> result = new ArrayList<>();
        result.add(rootNode.val);
        if (rootNode.left != null)
            result.addAll(preOrderTraversal(rootNode.left));
        if (rootNode.right != null)
            result.addAll(preOrderTraversal(rootNode.right));
        return result;
    }

    /**
     * 先序遍历，返回指向节点的迭代器（速度慢，不推荐使用）
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> Iterator<TreeNode<T>> preOrderTraversalIterator(TreeNode<T> rootNode) {
        if (rootNode == null)
            return null;
        return new Iterator<TreeNode<T>>() {
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
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> List<T> inOrderTraversal(TreeNode<T> rootNode) {
        ArrayList<T> result = new ArrayList<>();
        Stack<TreeNode<T>> stack = new Stack<>();
        TreeNode<T> nowNode = rootNode;
        while (nowNode != null) {
            stack.push(nowNode);
            nowNode = nowNode.left;
        }
        while (!stack.isEmpty()) {
            TreeNode<T> node = stack.pop();
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
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> Iterator<TreeNode<T>> inOrderTraversalIterator(TreeNode<T> rootNode) {
        if (rootNode == null)
            return null;
        return new Iterator<TreeNode<T>>() {
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
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> List<T> postOrderTraversal(TreeNode<T> rootNode) {
        ArrayList<T> result = new ArrayList<>();
        if (rootNode.left != null)
            result.addAll(postOrderTraversal(rootNode.left));
        if (rootNode.right != null)
            result.addAll(postOrderTraversal(rootNode.right));
        result.add(rootNode.val);
        return result;
    }

    /**
     * 后序遍历，返回指向节点的迭代器（速度慢，不推荐使用）
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> Iterator<TreeNode<T>> postOrderTraversalIterator(TreeNode<T> rootNode) {
        if (rootNode == null)
            return null;
        return new Iterator<TreeNode<T>>() {
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
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> List<List<T>> breadthFirstTraversal(TreeNode<T> rootNode) {
        ArrayList<List<T>> resultList = new ArrayList<>(); // 二维list。形式是[[第一层的所有val], [第二层的所有val]......]
        if (rootNode == null) // 空树返回空的list
            return resultList;
        Queue<TreeNode<T>> queueNode = new ArrayDeque<>(); // 保存节点的队列
        Queue<Integer> queueLevel = new ArrayDeque<>(); // 保存节点对应的层号
        queueNode.offer(rootNode); // 入队列root
        queueLevel.offer(1); // root的层号是1
        while (!queueNode.isEmpty()) { // 队列不为空时循环
            TreeNode<T> node = queueNode.poll(); // 出队列一个节点
            Integer level = queueLevel.poll(); // 出队列节点的层号
            if (level > resultList.size()) { // 当发现当前节点的层号在resultList中没有出现
                ArrayList<T> newList = new ArrayList<>(); // 新建一个保存第level层的list：[第level层的所有val]
                newList.add(node.val); // 当前节点的val加进去
                resultList.add(newList); // 把这个list加到二维list中
            } else { // 当前层号已经出现过
                resultList.get(resultList.size() - 1).add(node.val); // 将resultList最后一行拿出来，加入当前节点的val
            }
            if (node.left != null) { // 如果有左子树，入队列
                queueNode.offer(node.left);
                queueLevel.offer(level + 1);
            }
            if (node.right != null) { // 如果有右子树，入队列
                queueNode.offer(node.right);
                queueLevel.offer(level + 1);
            }
        }
        return resultList;
    }

    /**
     * 根据val寻找节点，如果没找到就返回null
     *
     * @param rootNode 根节点
     * @param val      要找的节点储存的val
     * @return
     */
    public static <T> TreeNode<T> findNodeByVal(TreeNode<T> rootNode, T val) {
        Iterator<TreeNode<T>> iterator = preOrderTraversalIterator(rootNode);
        while (iterator.hasNext()) {
            TreeNode<T> node = iterator.next();
            if (val.equals(node.val))
                return node;
        }
        return null;
    }

    /**
     * 计算二叉树的最大层数
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> int maxLevel(TreeNode<T> rootNode) {
        if (rootNode == null)
            return 0;
        return breadthFirstTraversal(rootNode).size();
    }

    private static <T> void assign(List<T> valList, int index, T element, int maxLevel) {
        if (index >= valList.size()) {
            for (int i : IntStream.range(0, (int) Math.pow(2, maxLevel) - 1 - valList.size()).toArray())
                valList.add(null);
        }
        valList.set(index, element);
    }

    /**
     * 将根节点代表的树转换成存储各个节点的val的堆
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> List<T> toValHeap(TreeNode<T> rootNode) {
        if (rootNode == null)
            return new ArrayList<>();
        int maxLevel = 1;
        ArrayList<T> valList = new ArrayList<>();
        for (int i : IntStream.range(0, (int) Math.pow(2, maxLevel) - 1).toArray())
            valList.add(null);
        Queue<TreeNode<T>> queueNode = new ArrayDeque<>();
        Queue<Integer> queueLevel = new ArrayDeque<>();
        Queue<Integer> queueIndex = new ArrayDeque<>();
        queueNode.offer(rootNode);
        queueLevel.offer(1);
        queueIndex.offer(0);
        while (!queueNode.isEmpty()) {
            TreeNode<T> node = queueNode.poll();
            Integer level = queueLevel.poll();
            Integer index = queueIndex.poll();
            if (node == null || level == null || index == null)
                return new ArrayList<>();
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
     * 将根节点代表的树转换成存储各个节点的val的堆，再把堆转换成dict（index: TreeNode）
     *
     * @param rootNode 根节点
     * @return
     */
    public static <T> Map<BigInteger, T> toValHeapToDict(TreeNode<T> rootNode) {
        if (rootNode == null)
            return new HashMap<>();
        int maxLevel = 1;
        HashMap<BigInteger, T> valDict = new HashMap<>();
        Queue<TreeNode<T>> queueNode = new ArrayDeque<>();
        Queue<Integer> queueLevel = new ArrayDeque<>();
        Queue<BigInteger> queueIndex = new ArrayDeque<>();
        queueNode.offer(rootNode);
        queueLevel.offer(1);
        queueIndex.offer(BigInteger.ZERO);
        while (!queueNode.isEmpty()) {
            TreeNode<T> node = queueNode.poll();
            Integer level = queueLevel.poll();
            BigInteger index = queueIndex.poll();
            if (node == null || level == null || index == null)
                return new HashMap<>();
            maxLevel = Math.max(maxLevel, level);
            valDict.put(index, node.val);
            if (node.left != null) {
                queueNode.offer(node.left);
                queueLevel.offer(level + 1);
                // (index + 1) * 2 - 1
                BigInteger offerNumber = index.add(BigInteger.ONE).multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(-1));
                queueIndex.offer(offerNumber);
            }
            if (node.right != null) {
                queueNode.offer(node.right);
                queueLevel.offer(level + 1);
                // (index + 1) * 2
                BigInteger offerNumber = index.add(BigInteger.ONE).multiply(BigInteger.valueOf(2));
                queueIndex.offer(offerNumber);
            }
        }
        return valDict;
    }

    /**
     * 将二叉树转换为字符串，空缺的位置用'X'代替
     *
     * @param rootNode 要转化为字符串的节点
     * @return
     */
    public static <T> String toString(TreeNode<T> rootNode) {
        return toString(rootNode, "X");
    }

    /**
     * 将二叉树转换为字符串
     *
     * @param rootNode    要转化为字符串的节点
     * @param nonReplacer 如果遍历到空缺的节点，用什么代替
     * @return
     */
    public static <T> String toString(TreeNode<T> rootNode, String nonReplacer) {
        if (rootNode == null)
            return "";
        List<T> valList = toValHeap(rootNode);
        StringBuilder string = new StringBuilder();
        for (int i : IntStream.range(0, valList.size()).toArray()) {
            if (valList.get(i) != null) {
                string.append(valList.get(i));
                string.append(" ");
            }
            else {
                string.append(nonReplacer);
                string.append(" ");
            }
            int log2 = (int) (Math.log(i + 2) / Math.log(2));
            if ((int) (Math.pow(2, log2)) == (i + 2))
                string.append("\n");
        }
        String[] lines = string.toString().trim().split("\n");
        for (int i : IntStream.range(0, lines.length - 1).toArray()) {
            StringBuilder spaceBeforeRow = new StringBuilder();
            for (int j : IntStream.range(0, (lines[lines.length - 1].length() - lines[i].length()) / 2).toArray())
                spaceBeforeRow.append(" ");
            lines[i] = spaceBeforeRow.toString() + lines[i];
        }
        return String.join("\n", lines);
    }

    /**
     * 用于测试新方法的正确性
     *
     * @param args
     */
    public static void main(String[] args) {
        // 测试createTreeByPreInOrder
        System.out.println("------ testing createTreeByPreInOrder ------");
        ArrayList<Integer> preorder = new ArrayList<>();
        Collections.addAll(preorder, -5, -1, -4, -5, -5, 0, -1, -3, -5, -4, 2);
        ArrayList<Integer> inorder = new ArrayList<>();
        Collections.addAll(inorder, -1, -5, -5, -5, -4, -1, -3, -5, -4, 0, 2);
        TreeNode<Integer> root = TreeUtil.createTreeByPreInOrder(preorder, inorder);
        // 测试toString
        System.out.println("------ testing toString ------");
        System.out.println(root);
        // 测试createTreeByHeap
        System.out.println("------ testing createTreeByHeap ------");
        System.out.println(root = TreeUtil.createTreeByHeap(preorder));
        if (root == null)
            return;
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
        Iterator<TreeNode<Integer>> inOrderIt = TreeUtil.inOrderTraversalIterator(root);
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
        Iterator<TreeNode<Integer>> postOrderIt = TreeUtil.postOrderTraversalIterator(root);
        while (postOrderIt.hasNext())
            System.out.print(postOrderIt.next().val + ", ");
        System.out.println();
        // 测试preOrderTraversal
        System.out.println("------ testing preOrderTraversal ------");
        System.out.println("root's preOrder:");
        System.out.println(TreeUtil.preOrderTraversal(root));
        Iterator<TreeNode<Integer>> preOrderIt = TreeUtil.preOrderTraversalIterator(root);
        while (preOrderIt.hasNext())
            System.out.print(preOrderIt.next().val + ", ");
        System.out.println();
        // 测试for-each
        System.out.println("------ testing for-each ------");
        System.out.print("root's for-each:");
        for (TreeNode<Integer> node : root)
            System.out.print(node.val + ", ");
        System.out.println();
        // 测试toValHeap
        System.out.println("------ testing toValHeap ------");
        System.out.println(TreeUtil.toValHeap(root));
    }
}
