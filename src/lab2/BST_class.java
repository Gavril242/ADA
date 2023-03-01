package lab2;

import java.util.*;

//Homework make BST all functions and such;



public class BST_class {

        public static int height=0;

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
        }

//-------------------------------------------------------------------
    public static void printPathFromTo(Node root, Node node1, Node node2) {
        List<Node> path = new ArrayList<>();
        if (findPath(root, node1, path) && findPath(root, node2, path)) {
            for (int i = path.size() - 1; i >= 0; i--) {
                System.out.print(path.get(i).key + " ");
            }
        } else {
            System.out.println("No path exists between " + node1.key + " and " + node2.key);
        }
    }

    private static boolean findPath(Node root, Node node, List<Node> path) {
        if (root == null) {
            return false;
        }
        path.add(root);
        if (root == node) {
            return true;
        }
        if (findPath(root.left, node, path) || findPath(root.right, node, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

//---------------------------------------------



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


                root.printLeafs(root);
                root.isBalancedNode(root);
                System.out.println("node root with value 10 was found:"+ Node.searchClosestNode(root,10));

                root.printLevels(root);

            }

    }



