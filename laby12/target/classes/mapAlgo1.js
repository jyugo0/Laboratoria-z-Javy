//typ (top, right, bottom, left)
const BASE_TILES = [
    { type: "4way", ways: [1, 1, 1, 1] },
    { type: "3way", ways: [1, 1, 0, 1] },
    { type: "2way", ways: [1, 0, 1, 0] },
    { type: "curved", ways: [1, 1, 0, 0] },
    { type: "closed", ways: [1, 0, 0, 0] }
];

function generateTileVariants() {
    const variants = [];
    BASE_TILES.forEach(tile => {
        const seen = new Set();
        for (let i = 0; i < 4; i++) {
            let ways = [...tile.ways];
            for (let j = 0; j < i; j++) {
                ways.unshift(ways.pop());
            }

            const key = ways.join(',');
            if (!seen.has(key)) {
                seen.add(key);
                variants.push({ type: tile.type, ways: ways, rotation: i * 90 });
            }
        }
    });
    return variants;
}

const ALL_VARIANTS = generateTileVariants();

function generateMap(size) {
    const map = Array(size).fill(null).map(() => Array(size).fill(null));

    for (let y = 0; y < size; y++) {
        for (let x = 0; x < size; x++) {

            const topNeighbor = y > 0 ? map[y-1][x].ways[2] : undefined;
            const leftNeighbor = x > 0 ? map[y][x-1].ways[1] : undefined;
            const validVariants = ALL_VARIANTS.filter(v => {
                const matchesTop = topNeighbor === undefined || v.ways[0] === topNeighbor;
                const matchesLeft = leftNeighbor === undefined || v.ways[3] === leftNeighbor;
                return matchesTop && matchesLeft;
            });

            const selected = validVariants[Math.floor(Math.random() * validVariants.length)];

            map[y][x] = {
                cordsX: x,
                cordsY: y,
                type: selected.type,
                ways: [...selected.ways],
                hasObstacle: -1
            };
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

                if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    openWays.push(dir);
                }
            }
        });

        if (openWays.length > 0) {
            const blockedDir = openWays[Math.floor(Math.random() * openWays.length)];
            tile.hasObstacle = blockedDir;
            placed++;
        }
    }
}

const myMap = generateMap(JAVA_MAP_SIZE);
placeObstacles(myMap, JAVA_MAP_SIZE-3);

myMap;