function findPathBFS(map, startX, startY, endX, endY) {
    const size = map.length;
    const offsets = { 0: [0, -1], 1: [1, 0], 2: [0, 1], 3: [-1, 0] };
    const oppositeDir = { 0: 2, 1: 3, 2: 0, 3: 1 };

    const visited = Array(size).fill(null).map(() => Array(size).fill(false));
    const previous = Array(size).fill(null).map(() => Array(size).fill(null));

    const queue = [{ x: startX, y: startY }];
    visited[startY][startX] = true;

    let closestNode = { x: startX, y: startY };
    let minManhattan = Math.abs(startX - endX) + Math.abs(startY - endY);

    while (queue.length > 0) {
        const curr = queue.shift();
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

        const tile = map[y][x];

        for (let dir = 0; dir < 4; dir++) {
            if (tile.ways[dir] !== 1 || tile.hasObstacle === dir) continue;

            const nx = x + offsets[dir][0];
            const ny = y + offsets[dir][1];

            if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                const neighbor = map[ny][nx];
                const oppDir = oppositeDir[dir];

                if (!visited[ny][nx] && neighbor.ways[oppDir] === 1 && neighbor.hasObstacle !== oppDir) {
                    visited[ny][nx] = true;
                    previous[ny][nx] = { x, y };
                    queue.push({ x: nx, y: ny });
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