package ch4_trees_graphs;

import java.util.ArrayList;

import org.junit.Test;

public class ch4_7_FindLCAInBinaryTree {

   /**
    * Design an algorithm to find the Lowest common ancestor of two nodes in a binary tree
    * Avoid sorting additional nodes in a data structure
    */

   // Sol1
   // With links to parents, we can compare two paths n1-root and n2-root. Their first intersection
   // node is
   // the lowest common ancestor.
   // time: O(n); space: O(n)

   // Without links to parents

   // Sol2
   // find two paths from root to node1 and node2. Compare the two paths
   // time: O(2n); space: O(n)
   public TreeNode findLCA2(TreeNode root, TreeNode a, TreeNode b) {
      ArrayList<TreeNode> pa = getPath(root, a), pb = getPath(root, b);
      if (pa == null || pb == null)
         return null;
      int i = 0, j = 0;
      while (i < pa.size() && j < pb.size()) {
         if (i + 1 < pa.size() && j + 1 < pb.size() && pa.get(i + 1) == pb.get(j + 1)) {
            i++;
            j++;
         } else
            break;
      }
      return pa.get(i);
   }

   private ArrayList<TreeNode> getPath(TreeNode root, TreeNode target) {
      return dfs(root, target, new ArrayList<TreeNode>());
   }

   // find a path from root to target node
   // Note how we record the valid path here. This is the most challenging part of this
   // seems-to-be-easy dfs
   private ArrayList<TreeNode> dfs(TreeNode root, TreeNode target, ArrayList<TreeNode> path) {
      if (root == null)
         return null;
      path.add(root);
      ArrayList<TreeNode> res = null;
      if (root == target)
         res = new ArrayList<TreeNode>(path);
      else {
         ArrayList<TreeNode> lp = dfs(root.left, target, path), rp = dfs(root.right, target, path);
         if (lp != null)
            res = lp;
         if (rp != null)
            res = rp;
      }
      path.remove(path.size() - 1);
      return res;
   }

   // Sol3 -- Has Bug
   // the bottom-up solution, avoid the duplicate check of some nodes
   // http://leetcode.com/2011/07/lowest-common-ancestor-of-a-binary-tree-part-i.html
   // The problem with this solution is that it can not handle the case that one node is
   // in the tree while another is not.
   // eg, given findLCA3(1,3,5), 3 is in the tree, while 5 is not. It returns 3, but in fact
   // we should return null.
   // time: O(n); space: O(1)
   public TreeNode findLCA3(TreeNode root, TreeNode a, TreeNode b) {
      if (root == null)
         return null;
      if (root == a || root == b)
         return root;
      TreeNode left = findLCA3(root.left, a, b);
      TreeNode right = findLCA3(root.right, a, b);
      if (left != null && right != null)
         return root;
      return left != null ? left : right;
   }

   // Sol4 -- To fix Bug in Sol3
   // We add two flags to mark whether two nodes are present
   // time: O(n); space: O(1)
   public TreeNode findLCA4(TreeNode root, TreeNode a, TreeNode b) {
      int[] visited = new int[1];
      TreeNode lca = finder(root, a, b, visited);
      return visited[0] == 2 ? lca : null;
   }

   private TreeNode finder(TreeNode root, TreeNode a, TreeNode b, int[] visited) {
      if (root == null || visited[0] == 2)
         return null;
      if (root == a || root == b) {
         if (root == a)
            visited[0]++;
         if (root == b)
            visited[0]++;
         finder(root.left, a, b, visited); // need to continue checking whether another node is in the tree
         finder(root.right, a, b, visited);
         return root;
      }
      TreeNode left = finder(root.left, a, b, visited);
      TreeNode right = finder(root.right, a, b, visited);
      if (left != null && right != null)
         return root;
      return left != null ? left : right;
   }

   @Test
   public void testBothTwoNodesPresent() {
      System.out.println("Test Two Nodes are present");
      int[] arr = new int[] { 1, 3, 5, 7, 9, 14, 23, 32 };
      TreeNode root = new TreeNode(arr);
      TreeNode a = root.left;
      TreeNode b = root.right;
      System.out.println(root.printTree());
      System.out.println("Node a: " + a);
      System.out.println("Node b: " + b);
      System.out.println("First Common Ancestor: " + findLCA2(root, a, b));
      System.out.println("First Common Ancestor: " + findLCA3(root, a, b));
      System.out.println("First Common Ancestor: " + findLCA4(root, a, b));
   }

   @Test
   public void testOneNodeAbsent() {
      System.out.println("\nTest one node is absent");
      int[] arr = new int[] { 1, 3, 5, 7, 9, 14, 23, 32 };
      TreeNode root = new TreeNode(arr);
      TreeNode a = new TreeNode(20);
      TreeNode b = root.right;
      System.out.println(root.printTree());
      System.out.println("Node a: " + a);
      System.out.println("Node b: " + b);
      System.out.println("First Common Ancestor: " + findLCA2(root, a, b));
      System.out.println("First Common Ancestor: " + findLCA3(root, a, b));
      System.out.println("First Common Ancestor: " + findLCA4(root, a, b));
   }

   @Test
   public void testTwoSameNodes() {
      System.out.println("\nTest two nodes are the same");
      int[] arr = new int[] { 1, 3, 5, 7, 9, 14, 23, 32 };
      TreeNode root = new TreeNode(arr);
      TreeNode a = root.right;
      TreeNode b = root.right;
      System.out.println(root.printTree());
      System.out.println("Node a: " + a);
      System.out.println("Node b: " + b);
      System.out.println("First Common Ancestor: " + findLCA2(root, a, b));
      System.out.println("First Common Ancestor: " + findLCA3(root, a, b));
      System.out.println("First Common Ancestor: " + findLCA4(root, a, b));
   }

}
