package com.company;
import javax.print.attribute.HashPrintJobAttributeSet;
import java.util.*;

public class GridSearch {

    private static LinkedList<LinkedList<Integer>> paths = new LinkedList<LinkedList<Integer>>();
    private static HashMap<Integer, Node> nodeLookup = new HashMap<Integer, Node>();
    private static int gridWidth;
    private static int gridHeight;

    public static class Node {

        private int id;
        LinkedList<Node> adjacent = new LinkedList<Node>();
        LinkedList<Integer> path = new LinkedList<Integer>();

        private Node(int id){
            this.path.add(id);
            this.id = id;
        }
    }

    public static Node getNode(int id){
        return nodeLookup.get(id);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please type the number of rows for the grid: ");
        int rows = scanner.nextInt();
        gridHeight = rows;
        System.out.print("\nPlease type the number of column for the grid: ");
        int columns = scanner.nextInt();
        gridWidth = columns;
        int grid[][] = createGrid(rows, columns);

        printGrid(grid);

        Iterator<Integer> it = nodeLookup.keySet().iterator();
        while(it.hasNext()){
            Node node = nodeLookup.get(it.next());
            checkPathDFS(node.id);
        }

        System.out.println(paths.isEmpty());

        if(!paths.isEmpty()){
            System.out.print("Here are the following paths: \n");
            List<List<Integer>> tL = new ArrayList<List<Integer>>();
            Iterator<LinkedList<Integer>> listIT = paths.iterator();
                while(listIT.hasNext()) {
                    List<Integer> list = (List<Integer>)listIT.next().clone();
                    List<Integer> list2 = (List<Integer>)paths.get(paths.indexOf(list)).clone();
                    //System.out.print(list.toString() + " after sort ");
                    //List<Integer> list2 = list.subList(0, list.size());
                    Collections.sort(list2);
                    if(tL.contains(list2)){
                        //System.out.print("Path already used" + "\n");
                        continue;
                    }
                    tL.add(list2);
                    System.out.print(list.toString());
                    System.out.println();
                }
            }
        }



    public static boolean checkPathDFS(int id){
        Node start = getNode(id);
        HashSet<Integer> visited = new HashSet<Integer>();
        return checkPathDFS(start, visited);
    }

    private static boolean checkPathDFS(Node source, HashSet<Integer> visited){

        if(visited.contains(source.id)){
            return false;
        }

        int sum = pathSum(source.path);
        int area = gridWidth*gridHeight;

        if(sum == area) {

            if(source.path.size() >= (gridWidth-1)) {
                System.out.print("PATH FOUND" + source.path.toString() + "\n");
                LinkedList<Integer> listCopy = (LinkedList<Integer>)source.path.clone();
                paths.add(listCopy);
                clearNodePath(source);
                return false;
            }

        }
        else if(sum > area) {
            clearNodePath(source);
            return false;

        }

        visited.add(source.id);

        for(Node child : source.adjacent){
            if(!visited.contains(child.id)) {
                //System.out.print("Source node " + source.id + " : " + source.path.toString() + "  " + "Child node " + child.id + " : " + child.path.toString() + "\n");
                child.path.addAll(0, source.path);
                checkPathDFS(child, visited);
            }
        }
        clearNodePath(source);
        visited.remove(source.id);
        return false;
    }

    public static int pathSum(List<Integer> path){
        int sum = 0;
        Iterator<Integer> it = path.iterator();
        while(it.hasNext()){
            sum += it.next();
        }

        return sum;
    }

    public static void clearNodePath(Node node){
        node.path.clear();
        node.path.add(node.id);
    }

    public static int[][] createGrid(int rows, int cols){
        int grid[][] = new int[rows][cols];
        //Node nGrid[][] = new Node[rows][cols];
        List<Integer> gridList = new ArrayList<Integer>();
        int size = rows*cols;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++) {
                int num = (int) Math.floor(Math.random() * (size + 1));
                if(gridList.contains(num)){
                    j--;
                    continue;
                }
                gridList.add(num);
                grid[i][j] = num;
                Node s = new Node(num);
                nodeLookup.put(num, s);
            }
        }

        setAdjacentNodes(grid, rows, cols);
        return grid;
    }

    public static void setAdjacentNodes(int grid[][], int n, int m){

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Node node = nodeLookup.get(grid[i][j]);
                findAdjacent(grid, node, i, j, n, m);
            }
        }
    }

    public static void findAdjacent(int nGrid[][], Node node, int i, int j, int n, int m){

        if(i == 0){
            if(j == 0){

                node.adjacent.add(nodeLookup.get(nGrid[i][j+1]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j+1]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j]));
            }
            else if(j == m-1){

                node.adjacent.add(nodeLookup.get(nGrid[i][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j]));
            }
            else{

                node.adjacent.add(nodeLookup.get(nGrid[i][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j]));

                node.adjacent.add(nodeLookup.get(nGrid[i+1][j+1]));

                node.adjacent.add(nodeLookup.get(nGrid[i][j+1]));

            }
        }
        else if(i == n-1){
            if(j == 0){

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j]));

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j+1]));

                node.adjacent.add(nodeLookup.get(nGrid[i][j+1]));
            }
            else if(j == m-1){

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j]));

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i][j-1]));
            }
            else{

                node.adjacent.add(nodeLookup.get(nGrid[i][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j-1]));

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j]));

                node.adjacent.add(nodeLookup.get(nGrid[i-1][j+1]));

                node.adjacent.add(nodeLookup.get(nGrid[i][j+1]));

            }
        }
        else if(j == 0){

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j]));

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j+1]));

            node.adjacent.add(nodeLookup.get(nGrid[i][j+1]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j+1]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j]));
        }
        else if(j == m-1){

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j]));

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j-1]));

            node.adjacent.add(nodeLookup.get(nGrid[i][j-1]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j-1]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j]));
        }

        else{

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j-1]));

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j]));

            node.adjacent.add(nodeLookup.get(nGrid[i-1][j+1]));

            node.adjacent.add(nodeLookup.get(nGrid[i][j-1]));

            node.adjacent.add(nodeLookup.get(nGrid[i][j+1]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j-1]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j]));

            node.adjacent.add(nodeLookup.get(nGrid[i+1][j+1]));

        }
    }


    public static void printGrid(int grid[][]) {
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                //System.out.print(grid[i][j] + ":" + nodeLookup.get(grid[i][j]).adjacent.size() + " ");
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
