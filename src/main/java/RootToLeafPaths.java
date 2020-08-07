import common.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/binary-tree-paths/
 * <p>
 * Given a binary tree, return all root-to-leaf paths.
 * <p>
 * Input:
 * <pre>
 *    1
 *  /   \
 * 2     3
 *  \
 *   5
 * </pre>
 * Output: ["1->2->5", "1->3"]
 * <p>
 * Explanation: All root-to-leaf paths are: 1->2->5, 1->3
 */
public class RootToLeafPaths {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        DFS(root, res, new StringBuilder());
        return res;
    }

    private void DFS(TreeNode root, List<String> result, StringBuilder curPath) {
        if (root != null) {
            int length = curPath.length();
            if (curPath.length() == 0) {
                curPath.append(root.val);
            } else {
                curPath.append("->").append(root.val);
            }
            if (root.left == null && root.right == null) {
                //if this is leaf node, add the current path to result
                result.add(curPath.toString());
            } else {
                DFS(root.left, result, curPath);
                DFS(root.right, result, curPath);
            }
            //this node is done, remove this node from the current path
            curPath.setLength(length);
        }
    }
}
