import java.util.*;
import java.util.stream.Collectors;

public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T> {
    String name;
    TreeNode<T> root;
    public BinarySearchTree (String name) {
        this.name = name;
    }

    public Iterator<T> iterator() {
        return new BinarySearchIterator<>(this.root);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("[" + this.name + "] ");
        stringRecur(string, this.root);
        return string.toString();
    }

    public void stringRecur(StringBuilder string, TreeNode<T> node) {
        if(!(node == null)) {
            string.append(node.value);
            if(node.Left != null) {
                stringRecur(string.append(" L:(" ), node.Left);
                string.append(")");
            }
            if(node.right != null) {
                stringRecur(string.append(" R:(" ), node.right);
                string.append(")");
            }
        }

    }

    private void addAll(List<T> asList) {
        root = new TreeNode<>(asList.get(0));
        if(asList.size() > 1) {
            for(T t : asList) {
                addRecur(root, t);
            }
        }
    }

    private TreeNode<T> addRecur(TreeNode<T> curr, T val) {
        if(curr == null) {
            return new TreeNode<>(val);
        } else {
            if(val.compareTo(curr.value) < 0) {
                curr.Left = addRecur(curr.Left, val);
            } else if(val.compareTo(curr.value) > 0) {
                curr.right = addRecur(curr.right, val);
            } else {
                return curr;
            }
        }
        return curr;
    }

    public static <T extends Comparable<T>> List<T> merge(BinarySearchTree<T> t1, BinarySearchTree<T> t2) {
        List<T> output = new ArrayList<>();
        Iterator<T> iter1 = t1.iterator();
        Iterator<T> iter2 = t2.iterator();
        T t3 = iter1.next();
        T t4 = iter2.next();
        while(iter1.hasNext() && iter2.hasNext()) {
            output.add(t3);
            t3 = iter1.next();
            output.add(t4);
            t4 = iter2.next();
        }
        if(iter1.hasNext()) {
            while(iter1.hasNext()) {
                output.add(t3);
                t3 = iter1.next();
            }
        }
        if(iter2.hasNext()) {
            while(iter2.hasNext()) {
                output.add(t4);
                t4 = iter2.next();
            }
        }
        output.add(t4);
        output.add(t3);
        output = output.stream().sorted().collect(Collectors.toList());
        return output;
    }



    public static void main(String[] args) {
        BinarySearchTree<Integer> t1 = new BinarySearchTree<>("Oak");
        t1.addAll(Arrays.asList(5, 3, 9, 0));
        BinarySearchTree<Integer> t2 = new BinarySearchTree<>("Maple");
// adds the elements to t2 in the order 9, 5, and then 10
        t2.addAll(Arrays.asList(1, 0, 10));
        System.out.println(t1); // see the expected output for exact format
        t1.forEach(System.out::println); // iteration in increasing order
        System.out.println(t2); // see the expected output for exact format
        t2.forEach(System.out::println); // iteration in increasing order
        BinarySearchTree<String> t3 = new BinarySearchTree<>("Cornucopia");
        t3.addAll(Arrays.asList("coconut", "apple", "banana", "plum", "durian",
                "no durians on this tree!", "tamarind"));
        System.out.println(t3); // see the expected output for exact format
        t3.forEach(System.out::println); // iteration in increasing order
        System.out.println(merge(t1, t2));
    }


}

class BinarySearchIterator <T extends Comparable<T>> implements Iterator<T>{
    TreeNode<T> current;
    Stack<TreeNode<T>> stack;

    public BinarySearchIterator(TreeNode<T> root) {
        this.stack = new Stack<>();
        this.current = root;
        while(current != null) {
            stack.push(current);
            current = current.Left;
        }
    }

    @Override
    public boolean hasNext() {
        return !(this.stack.isEmpty());

    }

    @Override
    public T next() {
        TreeNode<T> output = stack.pop();
        T tOutput = output.value;
        if(output.right != null) {
            output = output.right;
            while(output != null) {
                stack.push(output);
                output = output.Left;
            }
        }
        return tOutput;

    }

}

class TreeNode<T extends Comparable<T>> {
    TreeNode<T> right;
    TreeNode<T> Left;
    T value;

    TreeNode(T value1) {
        this.value = value1;
        this.Left = null;
        this.right = null;
    }

}
