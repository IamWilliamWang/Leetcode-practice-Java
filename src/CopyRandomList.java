class Node {
    int val;
    Node next;
    Node random;
    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
public class CopyRandomList {
    public Node copyRandomList(Node head) {
        if(head==null) {
            return null;
        }
        Node node = head;
        while(node!=null) {
            Node current = new Node(node.val);
            current.next = node.next;
            node.next = current;
            node = node.next.next;
        }
        node = head;
        while(node!=null) {
            node.next.random = node.random==null?null:node.random.next;
            node = node.next.next;
        }
        node = head;
        Node pClone = head.next;
        Node pCloneHead = head.next;
        while(pClone!=null) {
            if(pClone.next==null) {
                node.next=null;
                break;
            }
            node.next = pClone.next;
            pClone.next = pClone.next.next;
            pClone = pClone.next;
            node = node.next;
        }
        return pCloneHead;
    }
    public static void main(String[] args) {
        Node head = new Node(7);
        head.next = new Node(13);
        Node node2 = head.next;
        node2.next = new Node(11);
        node2.random = head;
        Node node3 = node2.next;
        node3.next = new Node(10);
        Node node4 = node3.next;
        node3.random = node4;
        node4.next = new Node(1);
        node4.random = node3;
        Node node5 = node4.next;
        node5.random = head;
        var ans = new CopyRandomList().copyRandomList(new Node(-1));
    }
}
