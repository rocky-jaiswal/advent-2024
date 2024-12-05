import { readFile } from './utils/readFile.ts';

const part1 = async () => {
  const input1 =
    'xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))';

  const txt = await readFile('resources/day3.txt');
  const input2 = txt.join('');

  // console.log(input2);

  const rg = new RegExp(/mul\(\d{1,3}\,\d{1,3}\)/g);
  const res = [...input2.matchAll(rg)];

  const muls = res
    .map((r) => r[0])
    .map((str) => [str.substring(4, str.length - 1)])
    .map((str) => str[0].split(','))
    .map((strArr) => parseInt(strArr[0]) * parseInt(strArr[1]))
    .reduce((acc, i) => acc + i, 0);

  return muls;
};

part1().then(console.log);
// part2().then(console.log);
