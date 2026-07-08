function findPathDijkstra(map, startX, startY, endX, endY) {
    const size = map.length;
    const offsets = { 0: [0, -1], 1: [1, 0], 2: [0, 1], 3: [-1, 0] };
    const oppositeDir = { 0: 2, 1: 3, 2: 0, 3: 1 };

    const distances = Array(size).fill(null).map(() => Array(size).fill(Infinity));
    const previous = Array(size).fill(null).map(() => Array(size).fill(null));

    distances[startY][startX] = 0;
    const queue = [{ x: startX, y: startY, dist: 0 }];

    let closestNode = { x: startX, y: startY };
    let minManhattan = Math.abs(startX - endX) + Math.abs(startY - endY);

    while (queue.length > 0) {
        queue.sort((a, b) => b.dist - a.dist);
        const curr = queue.pop();
        const { x, y } = curr;

        const distToTarget = Math.abs(x - endX) + Math.abs(y - endY);
        if (distToTarget < minManhattan) {
            minManhattan = distToTarget;
            closestNode = { x, y };
        }

        if (x === endX && y === endY) {
            closestNode = { x, y };
            break;
        }

        if (curr.dist > distances[y][x]) continue;

        const tile = map[y][x];

        for (let dir = 0; dir < 4; dir++) {

            if (tile.ways[dir] !== 1 || tile.hasObstacle === dir) continue;
            const nx = x + offsets[dir][0];
            const ny = y + offsets[dir][1];

            if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                const neighbor = map[ny][nx];
                const oppDir = oppositeDir[dir];

                if (neighbor.ways[oppDir] === 1 && neighbor.hasObstacle !== oppDir) {
                    const newDist = curr.dist + 1;

                    if (newDist < distances[ny][nx]) {
                        distances[ny][nx] = newDist;
                        previous[ny][nx] = { x, y };
                        queue.push({ x: nx, y: ny, dist: newDist });
                    }
                }
            }
        }
    }

    const path = [];
    let traceNode = closestNode;
    while (traceNode !== null) {
        path.push({ x: traceNode.x, y: traceNode.y });
        traceNode = previous[traceNode.y][traceNode.x];
    }
    return path.reverse();
}

findPathDijkstra(myMap, 0, 0, JAVA_MAP_SIZE - 1, JAVA_MAP_SIZE - 1);