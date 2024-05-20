import heapq

class Node:
    def __init__(self, state, parent=None, g=0, h=0):
        self.state = state
        self.parent = parent
        self.g = g  # costo acumulado desde el nodo inicial hasta este nodo
        self.h = h  # estimación del costo desde este nodo hasta el objetivo
        self.f = g + h  # f = g + h, costo total estimado del camino desde el inicio hasta el objetivo

    def __lt__(self, other):
        return self.f < other.f

def astar(start, goal, neighbors, heuristic):
    open_set = []
    closed_set = set()

    start_node = Node(start, None, 0, heuristic(start, goal))
    heapq.heappush(open_set, start_node)

    while open_set:
        current_node = heapq.heappop(open_set)

        if current_node.state == goal:
            path = []
            while current_node:
                path.append(current_node.state)
                current_node = current_node.parent
            return path[::-1]

        closed_set.add(current_node.state)

        for neighbor in neighbors(current_node.state):
            if neighbor in closed_set:
                continue

            g = current_node.g + 1  # Costo para moverse a un vecino es 1
            h = heuristic(neighbor, goal)
            f = g + h
            neighbor_node = Node(neighbor, current_node, g, h)

            heapq.heappush(open_set, neighbor_node)

    return None

# Ejemplo de uso
def manhattan_distance(state, goal):
    return abs(state[0] - goal[0]) + abs(state[1] - goal[1])

def get_neighbors(state):
    x, y = state
    neighbors = [(x+1, y), (x-1, y), (x, y+1), (x, y-1)]  # Movimientos arriba, abajo, izquierda y derecha
    return [(nx, ny) for nx, ny in neighbors if 0 <= nx < 5 and 0 <= ny < 5]  # Limitar al tamaño del tablero

start = (0, 0)
goal = (4, 1)

path = astar(start, goal, get_neighbors, manhattan_distance)
print("Camino encontrado:", path)
