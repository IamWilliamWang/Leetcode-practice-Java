import java.util.*;
// 科大讯飞：最大环的长度
public class KedaAdjMatrix {
    private static HashMap<Integer, List<Integer>> adjMatrix = new HashMap<>();
    private static HashSet<Integer> visited = new HashSet<>();
    private static LinkedList<List<Integer>> circlesPath = new LinkedList<>();
    private static int maxCircleLength = 1;

    private static void dfs(int nowNode, ArrayList<Integer> path) {
        if (visited.contains(nowNode)) {  // 遇到环了
            List<Integer> circle = path.subList(path.indexOf(nowNode), path.size());
            circlesPath.add(circle);
            maxCircleLength = Math.max(maxCircleLength, circle.size());
            return;
        }
        path = (ArrayList<Integer>) path.clone();
        path.add(nowNode);
        visited.add(nowNode);
        List<Integer> nextNodes = adjMatrix.get(nowNode);
        for (int node : nextNodes)
            dfs(node, path);
        visited.remove(nowNode);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nodeCount = sc.nextInt(); // 节点数
        int edgeCount = sc.nextInt(); // 边数
        for (int i = 0; i < edgeCount; i++) {
            int from = sc.nextInt(); // 边的开始节点
            int to = sc.nextInt(); // 边的结束节点
            // defaultdict[from].append(to)
            List<Integer> list = adjMatrix.getOrDefault(from, new ArrayList<>());
            list.add(to);
            adjMatrix.put(from, list);
        }
        for (int i = 0; i < nodeCount; i++)
            dfs(i, new ArrayList<>());
        System.out.println(maxCircleLength);
    }
}
