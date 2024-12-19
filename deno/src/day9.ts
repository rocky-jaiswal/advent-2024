import { range, slice } from 'ramda';
import { readFile } from './utils/readFile.ts';

class DiskFile {
  #id: number;
  #occSpace: number;
  #freeSpaceAfter: number;

  constructor(id: number, occSp: number, freeSp: number) {
    this.#id = id;
    this.#occSpace = occSp;
    this.#freeSpaceAfter = freeSp;
  }

  public get id() {
    return this.#id;
  }

  public get occSpace() {
    return this.#occSpace;
  }

  public get freeSpaceAfter() {
    return this.#freeSpaceAfter;
  }

  public toString() {
    return `${this.#id}-${this.#occSpace}-${this.#freeSpaceAfter}`;
  }
}

const checksum = (disk: string[]) => {
  return disk
    .map((str, index) => {
      if (str === '.') {
        return 0.0;
      } else {
        return parseFloat(str) * index;
      }
    })
    .reduce((acc, i) => acc + i, parseFloat('0'));
};

const flip = (mutStr: string[]) => {
  let leftIndex = -1;
  let rightIndex = mutStr.length;

  while (true) {
    while (true) {
      leftIndex += 1;
      if (leftIndex === mutStr.length) {
        return mutStr;
      }
      if (leftIndex >= rightIndex) {
        return mutStr;
      }
      if (mutStr[leftIndex] === '.') {
        break;
      }
    }

    while (true) {
      rightIndex -= 1;
      if (rightIndex === -1) {
        return mutStr;
      }
      if (rightIndex <= leftIndex) {
        return mutStr;
      }
      if (mutStr[rightIndex] !== '.') {
        break;
      }
    }

    mutStr[leftIndex] = mutStr[rightIndex];
    mutStr[rightIndex] = '.';
  }
};

const moveUp = (
  id: number,
  spaceNeeded: number,
  idIsAt: number,
  mutStr: string[]
) => {
  const space = range(0, spaceNeeded)
    .map((_) => '.')
    .join('');

  let freeIdx = -1;

  for (let index = 0; index < mutStr.length; index++) {
    if (slice(index, spaceNeeded + index, mutStr).join('') === space) {
      freeIdx = index;
      break;
    }
  }

  if (freeIdx !== -1 && freeIdx < idIsAt) {
    // console.log(freeIdx);
    // console.log(idIsAt);

    for (let index = 0; index < spaceNeeded; index++) {
      mutStr[freeIdx + index] = `${id}`;
    }

    for (let index = 0; index < spaceNeeded; index++) {
      mutStr[idIsAt + index] = '.';
    }
  }

  return mutStr;
};

const part1 = async () => {
  const contents = await readFile('resources/day9_1.txt');

  const nums = contents[0].split('');
  const diskFiles: Array<DiskFile> = [];
  let idx = 0;

  nums.forEach((_num: string, index: number) => {
    if (index % 2 === 0) {
      const df = new DiskFile(
        idx,
        parseInt(nums[index].trim()),
        parseInt(nums[index + 1] ?? 0)
      );
      diskFiles.push(df);
      idx += 1;
    }
  });

  const disk: Array<string | number> = [];

  diskFiles.forEach((df) => {
    range(0, df.occSpace).forEach((_) => disk.push(df.id));
    range(0, df.freeSpaceAfter).forEach((_) => disk.push('.'));
  });

  let mutStr = disk.map((d) => `${d}`);

  console.log('-------');

  mutStr = flip(mutStr);
  // console.log(mutStr.join(''));

  return checksum(mutStr);
};

const part2 = async () => {
  const contents = await readFile('resources/day9_2.txt');

  const nums = contents[0].split('');
  const diskFiles: Array<DiskFile> = [];
  let idx = 0;

  nums.forEach((_num: string, index: number) => {
    if (index % 2 === 0) {
      const df = new DiskFile(
        idx,
        parseInt(nums[index].trim()),
        parseInt(nums[index + 1] ?? 0)
      );
      diskFiles.push(df);
      idx += 1;
    }
  });

  const disk: Array<string> = [];
  const diskIdSpace: Record<number, number> = {};
  const diskIdLocn: Record<number, number> = {};
  const diskIds: Array<number> = [];

  diskFiles.forEach((df) => {
    diskIds.push(df.id);
    diskIdSpace[df.id] = df.occSpace;
    diskIdLocn[df.id] = disk.length;

    range(0, df.occSpace).forEach((_) => disk.push(`${df.id}`));
    range(0, df.freeSpaceAfter).forEach((_) => disk.push('.'));
  });

  let mutStr = disk;

  console.log('-------');

  diskIds.reverse().forEach((id: number) => {
    mutStr = moveUp(id, diskIdSpace[id], diskIdLocn[id], mutStr);
  });

  return checksum(mutStr);
};

// part1().then(console.log);
part2().then(console.log);
