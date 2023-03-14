package lab2;

import java.util.*;


//Homework AVL Trees
class Node {
    int key, balanceFactor;
    Node left, right, parent;

    public Node(int key) {
        this.key = key;
        this.balanceFactor = 0;
    }
}

public class AVLTree {


    private static final int N = 10000;
    Node root;

    private void updateBalanceFactor(Node node) {
        node.balanceFactor = height(node.right) - height(node.left);
    }

    private int height(Node node) {
        if (node == null)
            return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    private Node rightRotate(Node Node) {
        Node newRoot = Node.left;
        Node.left = newRoot.right;
        if (Node.left != null)
            Node.left.parent = Node;
        newRoot.right = Node;
        newRoot.parent = Node.parent;
        Node.parent = newRoot;

        updateBalanceFactor(Node);
        updateBalanceFactor(newRoot);

        return newRoot;
    }

    private Node leftRotate(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        if (node.right != null)
            node.right.parent = node;
        newRoot.left = node;
        newRoot.parent = node.parent;
        node.parent = newRoot;

        updateBalanceFactor(node);
        updateBalanceFactor(newRoot);

        return newRoot;
    }

    private Node balance(Node node) {
        updateBalanceFactor(node);

        if (node.balanceFactor == -2) {
            if (node.left.balanceFactor > 0)
                node.left = leftRotate(node.left);
            return rightRotate(node);
        } else if (node.balanceFactor == 2) {
            if (node.right.balanceFactor < 0)
                node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public void insert(int key) {
        root = insert(root, null, key);
    }

    private Node insert(Node node, Node parent, int key) {
        if (node == null)
            return new Node(key);

        if (key < node.key)
            node.left = insert(node.left, node, key);
        else if (key > node.key)
            node.right = insert(node.right, node, key);
        else
            return node;

        node = balance(node);

        if (node.parent == null)
            root = node;

        return node;

    }

    public Node search(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.key) {
                return node;
            } else if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }


    private static void searchTime(AVLTree tree, int key) {
        long start = System.nanoTime();
        Node node = tree.search(key);
        long end = System.nanoTime();
        if (node == null) {
            System.out.println("Key " + key + " not found");
        } else {
            System.out.println("Search time for key " + key + ": " + (end - start) + " nanoseconds  for AVL");
        }
    }

    public static void main(String[] args) {

        NodeBST treeBST = new NodeBST(8);
       // System.out.println("high");
        for (int i = 0  ;   i < 10 ; i++) { treeBST.insert(i);}
       // System.out.println("low");

        //treeBST.printLevels(treeBST);
        AVLTree tree = new AVLTree();

        // Insert N random keys
        long start = System.nanoTime();
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            int key = random.nextInt(N);
            tree.insert(key);
        }
        long end = System.nanoTime();
        System.out.println("Insertion time for " + N + " random keys: " + (end - start)/1000000 + " miliseconds AVL");
        //BST
         start = System.nanoTime();
         random = new Random();
        for (int i = 0; i < N; i++) {
            int key = random.nextInt(N);
            treeBST.insert(key);
        }
         end = System.nanoTime();
        System.out.println("Insertion time for " + N + " random keys: " + (end - start)/1000000 + " miliseconds BST");

        AVLTree.searchTime(tree,1000);
        treeBST.searchTime(treeBST,1000);

        tree = new AVLTree();

        // Insert N keys in increasing order
        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            tree.insert(i);
        }
        end = System.nanoTime();
        System.out.println("Insertion time for " + N + " keys in increasing order: " + (end - start)/1000000 + " miliseconds AVL");
        treeBST = new NodeBST(0);
        start = System.nanoTime();
        for (int i = 0; i < N; i++) {
            treeBST.insert(i);
        }
        end = System.nanoTime();
        System.out.println("Insertion time for " + N + " keys in increasing order: " + (end - start)/1000000 + " miliseconds BST");

        AVLTree.searchTime(tree,1000);
        treeBST.searchTime(treeBST,1000);
    }


}


class NodeBST {
        int key, balanceleft, balanceright;
        NodeBST left, right, parent;

        public NodeBST(int key) {
            this.key = key;
            left = null;
            right = null;
            parent = null;
        }

        public void insert(int key) {
            if (key < this.key) {
                if (left == null) {
                    left = new NodeBST(key);
                    left.parent = this;
                } else {
                    left.insert(key);
                }
            } else {
                if (right == null) {
                    right = new NodeBST(key);
                    right.parent = this;
                } else {
                    right.insert(key);
                }
            }
        }
    public NodeBST search(NodeBST root,int  key) {
        NodeBST node = root;
        while (node != null) {
            if (key == node.key) {
                return node;
            } else if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }


    public static void searchTime(NodeBST tree, int key) {
        long start = System.nanoTime();
        NodeBST node = tree.search(tree,key);
        long end = System.nanoTime();
        if (node == null) {
            System.out.println("Key " + key + " not found");
        } else {
            System.out.println("Search time for key " + key + ": " + (end - start) + " nanoseconds BST");
        }
    }



    public static void printLeafs(NodeBST nodeBST) { //optional
            if (nodeBST == null) {
                return;
            }
            if (nodeBST.left == null && nodeBST.right == null) {
                System.out.println(nodeBST.key);
            } else {
                printLeafs(nodeBST.left);
                printLeafs(nodeBST.right);
            }
        }

        private int isBalancedNode(NodeBST nodeBST) {
            if (nodeBST == null) {
                return 0;
            }
            int leftHeight = isBalancedNode(nodeBST.left);
            int rightHeight = isBalancedNode(nodeBST.right);
            if (leftHeight < 0 || rightHeight < 0 || Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            }
            return Math.max(leftHeight, rightHeight) + 1; // true if return>0

        }


        //  ive used Math.abs because the result sometimes was negative meaning its not the closest node then.
        private static int searchClosestNode(NodeBST nodeBST, int key) {
            int closest = 0;
            if (nodeBST == null) {
                return closest;
            }
            if (nodeBST.key == key) {
                return key;
            } else if (key < nodeBST.key) {
                if (Math.abs(nodeBST.key - key) < Math.abs(closest - key)) {
                    closest = nodeBST.key;
                }
                return searchClosestNode(nodeBST.left, key);
            } else {
                if (Math.abs(nodeBST.key - key) < Math.abs(closest - key)) {
                    closest = nodeBST.key;
                }
                return searchClosestNode(nodeBST.right, key);
            }
        }

        public static boolean checkExistTwoNodesWithSum(NodeBST root, int s) {
            List<Integer> values = new ArrayList<>();
            Stack<NodeBST> stack = new Stack<>();
            NodeBST current = root;

            while (current != null || !stack.isEmpty()) {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                current = stack.pop();
                values.add(current.key);
                current = current.right;
            }

            int i = 0;
            int j = values.size() - 1;
            while (i < j) {
                int sum = values.get(i) + values.get(j);
                if (sum == s) {
                    return true;
                } else if (sum < s) {
                    i++;
                } else {
                    j--;
                }
            }
            return false;
        }


        public static void printLevels(NodeBST nodeBST) {  // ex 6
            if (nodeBST == null) {
                return;
            }

            Queue<NodeBST> queue = new LinkedList<>(); // we add them in order in a queue recursively and then print them;
            queue.add(nodeBST);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                System.out.println("");

                for (int i = 0; i < levelSize; i++) {
                    NodeBST currNodeBST = queue.poll();
                    System.out.print(currNodeBST.key + " ");

                    if (currNodeBST.left != null) {
                        queue.add(currNodeBST.left);
                    }
                    if (currNodeBST.right != null) {
                        queue.add(currNodeBST.right);
                    }
                }
            }
        }

        public static void printPathFromTo(NodeBST root, NodeBST nodeBST1, NodeBST nodeBST2) {
            NodeBST lca = lowestCommonAncestor(root, nodeBST1, nodeBST2);
            if (lca == null) {
                System.out.println("No path exists between " + nodeBST1.key + " and " + nodeBST2.key);
                return;
            }
            List<NodeBST> path1 = new ArrayList<>();
            getPath(lca, nodeBST1, path1);
            List<NodeBST> path2 = new ArrayList<>();
            getPath(lca, nodeBST2, path2);
            Collections.reverse(path1);
            for (NodeBST nodeBST : path1) {
                System.out.print(nodeBST.key + " ");
            }
            for (NodeBST nodeBST : path2) {
                System.out.print(nodeBST.key + " ");
            }
        }

        private static NodeBST lowestCommonAncestor(NodeBST root, NodeBST nodeBST1, NodeBST nodeBST2) {
            if (root == null) {
                return null;
            }
            if (root == nodeBST1 || root == nodeBST2) {
                return root;
            }
            NodeBST left = lowestCommonAncestor(root.left, nodeBST1, nodeBST2);
            NodeBST right = lowestCommonAncestor(root.right, nodeBST1, nodeBST2);
            if (left != null && right != null) {
                return root;
            } else if (left != null) {
                return left;
            } else {
                return right;
            }
        }

        private static boolean getPath(NodeBST root, NodeBST nodeBST, List<NodeBST> path) {
            if (root == null) {
                return false;
            }
            path.add(root);
            if (root == nodeBST) {
                return true;
            }
            if (getPath(root.left, nodeBST, path) || getPath(root.right, nodeBST, path)) {
                return true;
            }
            path.remove(path.size() - 1);
            return false;
        }

//---------------------------------------------

        public static void printPathsWithSum(NodeBST root, int s) {
            List<Integer> path = new ArrayList<>();
            printPathsWithSumHelper(root, s, path, 0);
        }

        private static void printPathsWithSumHelper(NodeBST nodeBST, int s, List<Integer> path, int sumSoFar) {
            if (nodeBST == null) {
                return;
            }

            sumSoFar += nodeBST.key;
            path.add(nodeBST.key);

            if (sumSoFar == s && nodeBST.left == null && nodeBST.right == null) {
                // Print the path
                for (int i = 0; i < path.size(); i++) {
                    if (path.get(i) != s) //i get a bug where it shows the node itself many times
                        System.out.print(path.get(i) + " ");
                }
                System.out.println();
            }

            printPathsWithSumHelper(nodeBST.left, s, path, sumSoFar);
            printPathsWithSumHelper(nodeBST.right, s, path, sumSoFar);

            // Backtrack
            path.remove(path.size() - 1);
            printPathsWithSumHelper(nodeBST.left, s, new ArrayList<>(), 0);
            printPathsWithSumHelper(nodeBST.right, s, new ArrayList<>(), 0);
        }


//-------------------------------------------------------------------


        //print leafs, tree, add , remove, optimize and more ----- tasks
        public static void main(String[] args) {

            //we can replace this mess with a simple add method
            NodeBST root = new NodeBST(8);
            root.left = new NodeBST(2);
            root.right = new NodeBST(15);
            root.left.left = new NodeBST(1);
            root.left.right = new NodeBST(5);
            root.right.left = new NodeBST(10);
            root.right.right = new NodeBST(20);
            root.left.right.left = new NodeBST(4);
            root.left.right.right = new NodeBST(7);
            root.right.right.left = new NodeBST(18);
            root.right.right.right = new NodeBST(22);
            root.left.right.left.left = new NodeBST(3);

            System.out.println("Leaf nodes:");
            NodeBST.printLeafs(root);

            // testing isBalancedNode method
            int result = root.isBalancedNode(root);
            if (result >= 0) {
                System.out.println("Tree is balanced" + result);
            } else {
                System.out.println("Tree is not balanced" + result);
            }

            // testing searchClosestNode method for  7
            int closestNode = NodeBST.searchClosestNode(root, 7);
            System.out.println("Closest node to 7 is: " + closestNode);

            // testing checkExistTwoNodesWithSum method
            boolean result2 = NodeBST.checkExistTwoNodesWithSum(root, 21);
            if (result2) {
                System.out.println("There are two nodes with sum 21");
            } else {
                System.out.println("There are no two nodes with sum 21");
            }

            // test printLevels method
            System.out.println("Nodes by level:");
            NodeBST.printLevels(root);

            // test printPathFromTo

            System.out.println("\nPath from 1 to 20:");
            NodeBST.printPathFromTo(root, root.left.left, root.right.right);

            // test printPathsWithSum method
            System.out.println("\nPaths with sum 22:");

            NodeBST.printPathsWithSum(root, 22);


        }


    }


