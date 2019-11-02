import fs from 'fs';

function processNodes(numbers: number[]): number {
  // Destructure header values and remaining values
  const childrenLength = numbers.shift();
  const metadataLength = numbers.shift();

  if (childrenLength > 0) {
    // Calculate values for children nodes
    const childrenNodeValues: number[] = [];
    for (let i = 0; i < childrenLength; i++) {
      childrenNodeValues.push(processNodes(numbers));
    }
    // Get metadata values
    const metadata: number[] = [];
    for (let i = 0; i < metadataLength; i++) {
      metadata.push(numbers.shift());
    }

    return metadata
      // Map values into indexes
      .map((value) => value - 1)
      // Filter onyl positive and in range indexes
      .filter((value) => value >= 0 && value < childrenNodeValues.length)
      // Get node values at those indexes
      .map((index) => childrenNodeValues[index])
      // Sum those values
      .reduce((acc, curr) => acc + curr, 0);
  } else {
    //  Return sum of nodes metadata entries
    let sum = 0;
    for (let i = 0; i < metadataLength; i++) {
      sum += numbers.shift();
    }
    return sum;
  }

}

async function start() {
  const input: string = await fs.promises.readFile('./input/part1.txt', { encoding: 'utf8' });
  const numbers: number[] = input.split(' ').map((value: string) => +value);
  const metadataSum = processNodes(numbers);
  console.log('Metadata sum is', metadataSum);
}

start();
