function generateRandomWalkMap(size) {
    const map = Array(size).fill(null).map((_, y) =>
        Array(size).fill(null).map((_, x) => ({
            cordsX: x, cordsY: y, type: "closed", ways: [0, 0, 0, 0], hasObstacle: -1
        }))
    );

    const offsets = { 0: [0, -1], 1: [1, 0], 2: [0, 1], 3: [-1, 0] };
    const oppositeDir = { 0: 2, 1: 3, 2: 0, 3: 1 };

    let x = 0;
    let y = 0;
    const steps = size * size * 3;

    for (let i = 0; i < steps; i++) {
        const validDirs = [];
        for (let dir = 0; dir < 4; dir++) {
            const nx = x + offsets[dir][0];
            const ny = y + offsets[dir][1];
            if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                validDirs.push({ dir, nx, ny });
            }
        }

        if (validDirs.length > 0) {
            const move = validDirs[Math.floor(Math.random() * validDirs.length)];
            map[y][x].ways[move.dir] = 1;
            map[move.ny][move.nx].ways[oppositeDir[move.dir]] = 1;

            x = move.nx;
            y = move.ny;
        } else {
            x = Math.floor(Math.random() * size);
            y = Math.floor(Math.random() * size);
        }
    }
    return map;
}

function placeObstacles(map, obstacleCount) {
    const size = map.length;
    let placed = 0;
    let attempts = 0;
    const offsets = { 0: [0, -1], 1: [1, 0], 2: [0, 1], 3: [-1, 0] };

    while (placed < obstacleCount && attempts < obstacleCount * 10) {
        attempts++;
        const x = Math.floor(Math.random() * size);
        const y = Math.floor(Math.random() * size);
        const tile = map[y][x];

        if (tile.hasObstacle !== -1) continue;

        const openWays = [];
        tile.ways.forEach((val, dir) => {
            if (val === 1) {
                const nx = x + offsets[dir][0];
                const ny = y + offsets[dir][1];
                if (nx >= 0 && nx < size && ny >= 0 && ny < size) openWays.push(dir);
            }
        });

        if (openWays.length > 0) {
            tile.hasObstacle = openWays[Math.floor(Math.random() * openWays.length)];
            placed++;
        }
    }
}

const myMap = generateRandomWalkMap(JAVA_MAP_SIZE);
placeObstacles(myMap, JAVA_MAP_SIZE - 2);

myMap;