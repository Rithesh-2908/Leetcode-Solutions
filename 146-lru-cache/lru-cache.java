import java.util.*;

class LRUCache {

    class Node {
        int key, value;
        Node prev, next;
        
        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    private int capacity;
    private HashMap<Integer, Node> map;
    private Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>();
        
        head = new Node(0, 0); // dummy
        tail = new Node(0, 0); // dummy
        
        head.next = tail;
        tail.prev = head;
    }

    // remove node
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // insert at front
    private void insert(Node node) {
        node.next = head.next;
        node.prev = head;
        
        head.next.prev = node;
        head.next = node;
    }

    public int get(int key) {
        if (!map.containsKey(key)) return -1;
        
        Node node = map.get(key);
        remove(node);
        insert(node);
        
        return node.value;
    }

    public void put(int key, int value) {
        
        if (map.containsKey(key)) {
            remove(map.get(key));
        }
        
        Node node = new Node(key, value);
        insert(node);
        map.put(key, node);
        
        if (map.size() > capacity) {
            Node lru = tail.prev;
            remove(lru);
            map.remove(lru.key);
        }
    }
}