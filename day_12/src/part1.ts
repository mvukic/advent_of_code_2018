import fs from 'fs';

interface PatternSearchResult {
  exists: boolean;
  rule?: Rule;
}

interface Rule {
  pattern: string;
  value: string;
}

interface Description {
  state: string;
  rules: Rule[];
}

function parseInput(input: string): Description {
  const lines = input.split('\r\n');
  const state = lines[0].match(/^initial state: ([#\.]+)$/)[1];

  const rules: Rule[] = lines.slice(2).map((rule) => {
    const [pattern, value] = rule.split(' => ');
    return { pattern, value };
  });

  return { state, rules };
}

function getOrDefault(values: string[], index: number): string {
  return index < 0 || index >= values.length ? '.' : values[index];
}

function findEvolution(pattern: string, rules: Rule[]): PatternSearchResult {
  const rule = rules.find((r) => r.pattern === pattern);
  return {
    exists: !!rule,
    rule,
  };
}

function evolve(pots: string[], rules: Rule[]): string[] {
  const nextState = [...pots];
  for (let i = 0; i < pots.length; i++) {
    const selection = [
      getOrDefault(pots, i - 2),
      getOrDefault(pots, i - 1),
      getOrDefault(pots, i),
      getOrDefault(pots, i + 1),
      getOrDefault(pots, i + 2),
    ];
    const evolutionResult = findEvolution(selection.join(''), rules);
    if (evolutionResult.exists) {
      nextState[i] = evolutionResult.rule.value;
    }
  }
  return nextState;
}

function processState(description: Description, generations: number, offset: number): string[] {
  const padding = Array(offset).fill('.');
  let state: string[] = [
    ...padding,
    ...description.state,
    ...padding,
  ];

  for (let gen = 1; gen <= generations; gen++) {
    state = [...evolve(state, description.rules)];
  }

  return state;
}

async function start() {
  const input: string = await fs.promises.readFile('./input/part1.txt', { encoding: 'utf8' });
  const description = parseInput(input);
  const offset = 40;
  const finalState = processState(description, 20, offset);
  console.log(finalState.join(''));
  const prefix = finalState.slice(0, 49).join('');
  const state = finalState.slice(49, -1).join('');
  console.log(prefix);
  console.log(state);
}

start();
