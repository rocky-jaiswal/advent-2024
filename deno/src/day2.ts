import { readFile } from './utils/readFile.ts';

const removeElem = (elems: number[], idx: number) => {
  if (idx === 0) {
    return elems.slice(1);
  }

  if (idx === elems.length - 1) {
    return elems.slice(0, elems.length - 1);
  }

  return elems.slice(0, idx).concat(elems.slice(idx + 1, elems.length));
};

const isListSafe = (elems: number[]) => {
  let safe = true;
  let prog = 'inc';

  elems.forEach((num, idx) => {
    if (elems[0] === elems[1]) {
      safe = false;
    }

    if (idx === 0 && elems[idx + 1] && num < elems[idx + 1]) {
      prog = 'inc';
    }
    if (idx === 0 && elems[idx + 1] && num > elems[idx + 1]) {
      prog = 'dec';
    }

    if (
      prog === 'inc' &&
      elems[idx + 1] &&
      (num > elems[idx + 1] ||
        Math.abs(num - elems[idx + 1]) < 1 ||
        Math.abs(num - elems[idx + 1]) > 3)
    ) {
      safe = false;
    }

    if (
      prog === 'dec' &&
      elems[idx + 1] &&
      (num < elems[idx + 1] ||
        Math.abs(num - elems[idx + 1]) < 1 ||
        Math.abs(num - elems[idx + 1]) > 3)
    ) {
      safe = false;
    }
  });

  return safe;
};

const part1 = async () => {
  const contents = await readFile('resources/day2_2.txt');

  const matx = contents.map((c) =>
    c
      .trim()
      .split(/\s+/)
      .map((v) => parseInt(v))
  );

  let safeCount = 0;

  matx.forEach((elems) => {
    const safe = isListSafe(elems);

    if (safe) {
      safeCount += 1;
    }
  });

  return { safeCount };
};

const part2 = async () => {
  const contents = await readFile('resources/day2_2.txt');

  const matx = contents.map((c) =>
    c
      .trim()
      .split(/\s+/)
      .map((v) => parseInt(v))
  );

  let safeCount = 0;

  matx.forEach((elems) => {
    let safe = false;

    elems.forEach((_num, idx) => {
      if (isListSafe(elems)) {
        safe = true;
      } else {
        // console.log(removeElem(elems, idx));
        // console.log(removeElem(elems, idx + 1));

        safe =
          safe ||
          isListSafe(removeElem(elems, idx)) ||
          isListSafe(removeElem(elems, idx + 1));
      }
    });

    if (safe) {
      safeCount += 1;
    }
  });

  return { safeCount };
};

part1().then(console.log);
part2().then(console.log);
