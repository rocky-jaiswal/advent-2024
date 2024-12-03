import { readFile } from './utils/readFile.ts';

const day1_part1 = async () => {
  const contents = await readFile('resources/day1_1.txt');

  const matx = contents.map((c, i) =>
    c
      .trim()
      .split(/\s+/)
      .map((v) => ({ [i]: parseInt(v) }))
  );

  const left = matx
    .map((m) => m[0])
    .sort((e1, e2) => Object.values(e1)[0] - Object.values(e2)[0]);

  const right = matx
    .map((m) => m[1])
    .sort((e1, e2) => Object.values(e1)[0] - Object.values(e2)[0]);

  const dist = left
    .map((l, i) => Object.values(l)[0] - Object.values(right[i])[0])
    .map((i) => Math.abs(i))
    .reduce((acc, i) => acc + i, 0);

  return { left, right, dist };
};

const day1_part2 = async () => {
  const contents = await readFile('resources/day1_2.txt');

  const matx = contents.map((c) => c.trim().split(/\s+/));

  const left = matx.map((m) => m[0]);

  const right = matx
    .map((m) => m[1])
    .reduce((acc, i) => {
      if (acc[i]) {
        acc[i] = acc[i] + 1;
      } else {
        acc[i] = 1;
      }
      return acc;
    }, {} as Record<string, number>);

  const score = left
    .map((l) => parseInt(l) * (right[l] ?? 0))
    .reduce((acc, i) => acc + i, 0);

  return { left, right, score };
};

day1_part1().then(console.log);
day1_part2().then(console.log);
