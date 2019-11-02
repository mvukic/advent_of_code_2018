import fs from 'fs';

function processNodes(numbers: number[]): number {
  // Destructure header values and remaining values
  const childrenLength = numbers.shift();
  const metadataLength = numbers.shift();

  // Metadata sum for this node
  let sum = 0;

  // Calculate children nodes metadata sums
  for (let i = 0; i < childrenLength; i++) {
    sum += processNodes(numbers);
  }

  // Calculate metadata sum for this node
  for (let i = 0; i < metadataLength; i++) {
    sum += numbers.shift();
  }
  return sum;
}

async function start() {
  const input: string = await fs.promises.readFile('./input/part1.txt', { encoding: 'utf8' });
  const numbers: number[] = input.split(' ').map((value: string) => +value);
  const metadataSum = processNodes(numbers);
  console.log('Metadata sum is', metadataSum);
}

start();
