import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/course-schedule-ii/
 * <p>
 * There are a total of n courses you have to take, labeled from 0 to n-1.
 * <p>
 * Some courses may have prerequisites, for example to take course 0 you have to first take course 1, which is expressed as a pair: [0,1]
 * <p>
 * Given the total number of courses and a list of prerequisite pairs, return the ordering of courses you should take to finish all courses.
 * <p>
 * There may be multiple correct orders, you just need to return one of them. If it is impossible to finish all courses, return an empty array.
 */
public class CourseSchedule2 {
    //topological sort can also be done by bfs ie. kahn algorithm
    //keep track of each vertices indegree (indegree array), pick vertices with indegree 0 and add it to a queue
    //poll from the queue, get the adjacent nodes, reduce the indegree of adjacent nodes by 1, if the indegree becomes 0, add it back to the queue
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = buildGraph(numCourses, prerequisites);
        boolean[] visited = new boolean[numCourses];
        boolean[] toDo = new boolean[numCourses];
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            //try each node as the starting point of DFS as it's not a complete graph
            if (!visited[i] && topologicalSort(visited, toDo, graph, i, stack)) {
                return new int[]{};
            }
        }
        return stack.stream().mapToInt(i -> i).toArray();
    }

    //return false if cycle found
    private boolean topologicalSort(boolean[] visited, boolean[] toDo, List<List<Integer>> graph, int startingCourse, ArrayDeque<Integer> stack) {
        if (visited[startingCourse]) {
            return false;
        }
        if (toDo[startingCourse]) {
            return true; // same node found in the recursion stack
        }
        toDo[startingCourse] = true;
        for (Integer adjacentNode : graph.get(startingCourse)) {
            boolean cycle = topologicalSort(visited, toDo, graph, adjacentNode, stack);
            if (cycle) {
                return true;
            }
        }
        toDo[startingCourse] = false;
        visited[startingCourse] = true;
        stack.push(startingCourse);
        return false;
    }

    private List<List<Integer>> buildGraph(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(numCourses);
        for (int i = 0; i < numCourses; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < prerequisites.length; i++) {
            graph.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        return graph;
    }
}
