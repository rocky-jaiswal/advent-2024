import { sort } from 'ramda';
import { readFile } from './utils/readFile.ts';

const part1 = async () => {
  const input1 =
    'xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))';

  const txt = await readFile('resources/day3.txt');
  const input2 = txt.join('');

  // console.log(input2);

  const rg = new RegExp(/mul\(\d{1,3}\,\d{1,3}\)/g);
  const res = [...input1.matchAll(rg)];

  const muls = res
    .map((r) => r[0])
    .map((str) => [str.substring(4, str.length - 1)])
    .map((str) => str[0].split(','))
    .map((strArr) => parseInt(strArr[0]) * parseInt(strArr[1]))
    .reduce((acc, i) => acc + i, 0);

  return muls;
};

const diff = (a: number, b: number) => {
  return b - a;
};

const sortx = (arr: number[]) => {
  return sort(diff, arr);
};

const part2 = async () => {
  const input1 =
    "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";

  const txt = await readFile('resources/day3.txt');
  const input2 = txt.join('');

  const rg1 = new RegExp(/mul\(\d{1,3}\,\d{1,3}\)/g);
  const res = [...input2.matchAll(rg1)];

  const rg2 = new RegExp(/do\(\)/g);
  const allDos = [...input2.matchAll(rg2)];

  const rg3 = new RegExp(/don\'t\(\)/g);
  const allDonts = [...input2.matchAll(rg3)];

  const mulIndex = res.map((d) => d.index);

  const dosIndex = sortx(allDos.map((d) => d.index));
  const dontsIndex = sortx(allDonts.map((d) => d.index));

  const findClosestInstuction = (idx: number) => {
    const at = mulIndex[idx];

    const closestDo = sortx(
      dosIndex.map((d: number) => d - at).filter((n: number) => n < 0)
    )[0];

    const closestDont = sortx(
      dontsIndex.map((d: number) => d - at).filter((n: number) => n < 0)
    )[0];

    // console.log('-----');
    // console.log(input2.substring(at, at + 12));
    // console.log({
    //   at,
    //   closestDo,
    //   closestDont,
    //   res: Math.abs(closestDo ?? Infinity) < Math.abs(closestDont ?? Infinity),
    // });

    if (!closestDo && !closestDont) {
      return true;
    }

    return Math.abs(closestDo ?? Infinity) < Math.abs(closestDont ?? Infinity);
  };

  const muls = res
    .map((r) => r[0])
    .filter((_s, idx) => findClosestInstuction(idx))
    .map((str) => [str.substring(4, str.length - 1)])
    .map((str) => str[0].split(','))
    .map((x) => {
      // console.log(x);
      return x;
    })
    .map((strArr) => parseInt(strArr[0]) * parseInt(strArr[1]))
    .reduce((acc, i) => acc + i, 0);

  return muls;
};

part1().then(console.log);
part2().then(console.log);
