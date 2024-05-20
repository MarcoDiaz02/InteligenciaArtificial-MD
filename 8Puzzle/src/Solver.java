import java.util.*;

public class Solver {

    private static final int[][] GOAL_STATE = {{0, 1, 2}, 
                                              {3, 4, 5}, 
                                              {6, 7, 8}};
    private static final int[][] MOVES = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    //                                      Up      Down     Left    Right
    
    private static final int[][] EASY = {{1, 2, 3}, 
                                        {4, 5, 6}, 
                                        {0, 7, 8}};
    private static final int[][] HARD = {{5, 1, 3}, 
                                        {0, 2, 6}, 
                                        {4, 7, 8}};
    private static int NODES = 0;

    public static void main(String[] args) {
        int[][] startState = HARD;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido al 8 Puzzle Solver");
        System.out.println("- - - - - - - - - - - -");
        System.out.println("Menu");
        System.out.println("[1] Algoritmo de Busqueda por anchura");
        System.out.println("[2] Algoritmo de Busqueda en profundidad");
        System.out.println("[3] - Algoritmo de Distancia Manhattan");
        
        boolean picked = false;
        
        do{
        	
        	try {
           	 int option = scanner.nextInt();
           	 System.out.println("");
           	switch (option) {
            case 1:
            	picked = true;
                solvePuzzle(startState, "A");
                break;
            case 2:
            	picked = true;
                solvePuzzle(startState, "P");
                break;
            case 3:
            	picked = true;
                solvePuzzle(startState, "DM");
                break;
            default:
                System.out.println("Ingrese una opcion valida");
        }
           	 
        	}catch(Exception e){
        		System.out.println("Ingrese una opcion valida");
        		scanner.next();
        	}
             
        }while(picked == false);
        
        scanner.close();

    }

    static void solvePuzzle(int[][] initialState, String method) {
    	 long startTime = System.currentTimeMillis();
         int searchCount = 0;
        List<int[][]> solution = switch (method) {
            case "A" -> bfs(initialState);
            case "P" -> dfs(initialState);
            case "DM" -> DistanciaManhattan(initialState);
            default -> {
                System.out.println("Error");
                yield null;
            }
        };
        searchCount = solution == null ? 0 : solution.size() - 1;
        
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (solution != null) {
            solution.forEach(Solver::printBoardState);
            System.out.println("La solución se encontro en " + elapsedTime + " ms");
            System.out.println("El Numero de movimientos que se realizaron: " + searchCount);
            System.out.println("Número de nodos generados: " + NODES);
        } else {
            System.out.println("No se encontro lo solucion");
        }
    }

    static boolean isGoalState(int[][] state) {
        return Arrays.deepEquals(state, GOAL_STATE);
    }

    static void printBoardState(int[][] state) {
        Arrays.stream(state).map(row -> Arrays.toString(row).replace(",", "")
                .replace("[", "").replace("]", "")).forEach(System.out::println);
        System.out.println();
        System.out.println("- - - - - - - - - - - - -");
        System.out.println();
  
    }
    
    static List<int[][]> getSuccessorStates(int[][] state) {
        List<int[][]> successors = new ArrayList<>();
        int zeroRow = -1, zeroCol = -1;

        outerLoop:
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                    break outerLoop;
                }
            }
        }

        for (int i = 0; i < MOVES.length; i++) {
            int newRow = zeroRow + MOVES[i][0];
            int newCol = zeroCol + MOVES[i][1];

            if (newRow >= 0 && newRow < state.length && newCol >= 0 && newCol < state[newRow].length) {
                int[][] newState = Arrays.stream(state).map(int[]::clone).toArray(int[][]::new);
                newState[zeroRow][zeroCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;
                successors.add(newState);
            }
        }

        return successors;
    }

    static List<int[][]> bfs(int[][] startState) {
        return searchBFS(startState);
    }

    static List<int[][]> dfs(int[][] startState) {
        return searchDFS(startState);
    }

    static List<int[][]> DistanciaManhattan(int[][] startState) {
        return searchDM(startState);
    }

    static List<int[][]> searchBFS(int[][] startState) {
        Queue<Node> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startState, null, 0));
        NODES++;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int[][] currentState = current.state;

            if (isGoalState(currentState)) {
                return buildPath(current);
            }

            for (int[][] successor : getSuccessorStates(currentState)) {
                String successorStr = Arrays.deepToString(successor);
                if (!visited.contains(successorStr)) {
                    queue.add(new Node(successor, current, 0));
                    visited.add(successorStr);
                    NODES++;
                }
            }
        }

        return null;
    }
    
    static List<int[][]> searchDFS(int[][] startState) {
        Deque<Node> stack = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        stack.push(new Node(startState, null, 0));
        NODES++;

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            int[][] currentState = current.state;

            if (isGoalState(currentState)) {
                return buildPath(current);
            }

            for (int[][] successor : getSuccessorStates(currentState)) {
                String successorStr = Arrays.deepToString(successor);
                if (!visited.contains(successorStr)) {
                    stack.push(new Node(successor, current, 0));
                    visited.add(successorStr);
                    NODES++;
                }
            }
        }

        return null;
    }

    //Funcion heuristica basada en la Distancia Manhattan para aplicar asi un costo
    static List<int[][]> searchDM(int[][] startState) {
        PriorityQueue<Node> queue = new PriorityQueue<>((n1, n2) -> Integer.compare(n1.cost, n2.cost));
        Set<String> visited = new HashSet<>();
        queue.add(new Node(startState, null, 0));
        NODES++;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int[][] currentState = current.state;

            if (isGoalState(currentState)) {
                return buildPath(current);
            }

            for (int[][] successor : getSuccessorStates(currentState)) {
                String successorStr = Arrays.deepToString(successor);
                int heuristicCost = calculateManhattanDistance(successor); //Heurística de la distancia de Manhattan
                int successorCost = current.cost + 1;

                if (!visited.contains(successorStr) || successorCost < current.cost) {
                    queue.add(new Node(successor, current, successorCost + heuristicCost)); //Sumar la heurística al costo acumulado
                    visited.add(successorStr);
                    NODES++;
                }
            }
        }

        return null;
    }

    //Calcula la heurística de la distancia de Manhattan
    static int calculateManhattanDistance(int[][] state) {
        int distance = 0;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                int value = state[i][j];
                if (value != 0) {
                    int targetRow = (value - 1) / state.length; //Fila en la que debería estar el número
                    int targetCol = (value - 1) % state.length; //Columna en la que debería estar el número
                    distance += Math.abs(i - targetRow) + Math.abs(j - targetCol);
                }
            }
        }
        return distance;
    }

    static List<int[][]> buildPath(Node end) {
        List<int[][]> path = new ArrayList<>();
        Node current = end;
        while (current != null) {
            path.add(current.state);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    static class Node {
        int[][] state;
        Node parent;
        int cost;

        Node(int[][] state, Node parent, int cost) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
        }
    }
}