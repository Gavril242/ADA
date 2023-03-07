package lab2;

import java.util.*;

//Homework make BST all functions and such;


public class BST_class {

    public static int height = 0;

    public static class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
            left = null;
            right = null;
        }


        public static void printLeafs(Node node) { //optional
            if (node == null) {
                return;
            }
            if (node.left == null && node.right == null) {
                System.out.println(node.key);
            } else {
                printLeafs(node.left);
                printLeafs(node.right);
            }
        }

        private int isBalancedNode(Node node) {
            if (node == null) {
                return 0;
            }
            int leftHeight = isBalancedNode(node.left);
            int rightHeight = isBalancedNode(node.right);
            if (leftHeight < 0 || rightHeight < 0 || Math.abs(leftHeight - rightHeight) > 1) {
                return -1;
            }
            return Math.max(leftHeight, rightHeight) + 1; // true if return>0

        }


        //  ive used Math.abs because the result sometimes was negative meaning its not the closest node then.
        private static int searchClosestNode(Node node, int key) {
            int closest = 0;
            if (node == null) {
                return closest;
            }
            if (node.key == key) {
                return key;
            } else if (key < node.key) {
                if (Math.abs(node.key - key) < Math.abs(closest - key)) {
                    closest = node.key;
                }
                return searchClosestNode(node.left, key);
            } else {
                if (Math.abs(node.key - key) < Math.abs(closest - key)) {
                    closest = node.key;
                }
                return searchClosestNode(node.right, key);
            }
        }

        public static boolean checkExistTwoNodesWithSum(Node root, int s) {
            List<Integer> values = new ArrayList<>();
            Stack<Node> stack = new Stack<>();
            Node current = root;

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


        public static void printLevels(Node node) {  // ex 6
            if (node == null) {
                return;
            }

            Queue<Node> queue = new LinkedList<>(); // we add them in order in a queue recursively and then print them;
            queue.add(node);

            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                System.out.println("");

                for (int i = 0; i < levelSize; i++) {
                    Node currNode = queue.poll();
                    System.out.print(currNode.key + " ");

                    if (currNode.left != null) {
                        queue.add(currNode.left);
                    }
                    if (currNode.right != null) {
                        queue.add(currNode.right);
                    }
                }
            }
        }

        public static void printPathFromTo(Node root, Node node1, Node node2) {
            Node lca = lowestCommonAncestor(root, node1, node2);
            if (lca == null) {
                System.out.println("No path exists between " + node1.key + " and " + node2.key);
                return;
            }
            List<Node> path1 = new ArrayList<>();
            getPath(lca, node1, path1);
            List<Node> path2 = new ArrayList<>();
            getPath(lca, node2, path2);
            Collections.reverse(path1);
            for (Node node : path1) {
                System.out.print(node.key + " ");
            }
            for (Node node : path2) {
                System.out.print(node.key + " ");
            }
        }

        private static Node lowestCommonAncestor(Node root, Node node1, Node node2) {
            if (root == null) {
                return null;
            }
            if (root == node1 || root == node2) {
                return root;
            }
            Node left = lowestCommonAncestor(root.left, node1, node2);
            Node right = lowestCommonAncestor(root.right, node1, node2);
            if (left != null && right != null) {
                return root;
            } else if (left != null) {
                return left;
            } else {
                return right;
            }
        }

        private static boolean getPath(Node root, Node node, List<Node> path) {
            if (root == null) {
                return false;
            }
            path.add(root);
            if (root == node) {
                return true;
            }
            if (getPath(root.left, node, path) || getPath(root.right, node, path)) {
                return true;
            }
            path.remove(path.size() - 1);
            return false;
        }

//---------------------------------------------

        public static void printPathsWithSum(Node root, int s) {
            List<Integer> path = new ArrayList<>();
            printPathsWithSumHelper(root, s, path, 0);
        }

        private static void printPathsWithSumHelper(Node node, int s, List<Integer> path, int sumSoFar) {
            if (node == null) {
                return;
            }

            sumSoFar += node.key;
            path.add(node.key);

            if (sumSoFar == s && node.left == null && node.right == null) {
                // Print the path
                for (int i = 0; i < path.size(); i++) {
                    if(path.get(i)!=s) //i get a bug where it shows the node itself many times
                    System.out.print(path.get(i) + " ");
                }
                System.out.println();
            }

            printPathsWithSumHelper(node.left, s, path, sumSoFar);
            printPathsWithSumHelper(node.right, s, path, sumSoFar);

            // Backtrack
            path.remove(path.size() - 1);
            printPathsWithSumHelper(node.left, s, new ArrayList<>(), 0);
            printPathsWithSumHelper(node.right, s, new ArrayList<>(), 0);
        }

    }

//-------------------------------------------------------------------


    //print leafs, tree, add , remove, optimize and more ----- tasks
    public static void main(String[] args) {

        //we can replace this mess with a simple add method
        Node root = new Node(8);
        root.left = new Node(2);
        root.right = new Node(15);
        root.left.left = new Node(1);
        root.left.right = new Node(5);
        root.right.left = new Node(10);
        root.right.right = new Node(20);
        root.left.right.left = new Node(4);
        root.left.right.right = new Node(7);
        root.right.right.left = new Node(18);
        root.right.right.right = new Node(22);
        root.left.right.left.left = new Node(3);

        System.out.println("Leaf nodes:");
        BST_class.Node.printLeafs(root);

        // testing isBalancedNode method
        int result = root.isBalancedNode(root);
        if (result >= 0) {
            System.out.println("Tree is balanced");
        } else {
            System.out.println("Tree is not balanced");
        }

        // testing searchClosestNode method for  7
        int closestNode = BST_class.Node.searchClosestNode(root, 7);
        System.out.println("Closest node to 7 is: " + closestNode);

        // testing searchClosestNode method for 11
        closestNode = BST_class.Node.searchClosestNode(root, 11);
        System.out.println("Closest node to 11 is: " + closestNode);

        // testing checkExistTwoNodesWithSum method
        boolean result2 = BST_class.Node.checkExistTwoNodesWithSum(root, 21);
        if (result2) {
            System.out.println("There are two nodes with sum 21");
        } else {
            System.out.println("There are no two nodes with sum 21");
        }

        // test printLevels method
        System.out.println("Nodes by level:");
        BST_class.Node.printLevels(root);

        // test printPathFromTo

        System.out.println("\nPath from 1 to 20:");
        BST_class.Node.printPathFromTo(root, root.left.left, root.right.right);

        // test printPathsWithSum method
        System.out.println("\nPaths with sum 22:");

        BST_class.Node.printPathsWithSum(root, 22);


    }


}



