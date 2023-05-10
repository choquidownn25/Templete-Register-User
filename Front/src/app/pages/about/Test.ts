const myArray = [1, 2, 9, 2, 5, 3, 5, 1, 5];
const n = 3;

// Construir matriz auxiliar
const auxMatrix: number[][] = [];
for (let i = 0; i < n; i++) {
  auxMatrix[i] = [];
  for (let j = 0; j < n; j++) {
    if (j === 0) {
      auxMatrix[i][j] = myArray[i * n + j];
    } else {
      let minSum = auxMatrix[i][j-1];
      if (i > 0) {
        minSum = Math.min(minSum, auxMatrix[i-1][j-1]);
      }
      if (i < n-1) {
        minSum = Math.min(minSum, auxMatrix[i+1][j-1]);
      }
      auxMatrix[i][j] = myArray[i * n + j] + minSum;
    }
  }
}

// Encontrar camino con la menor suma
let minSumPath: number[] = [];
let minSum = Infinity;
for (let i = 0; i < n; i++) {
  if (auxMatrix[i][n-1] < minSum) {
    minSum = auxMatrix[i][n-1];
    minSumPath = [i];
  } else if (auxMatrix[i][n-1] === minSum) {
    minSumPath.push(i);
  }
}
for (let j = n-2; j >= 0; j--) {
  let nextI = minSumPath[0];
  if (nextI > 0 && auxMatrix[nextI-1][j] < auxMatrix[nextI][j]) {
    nextI = nextI - 1;
  }
  if (nextI < n-1 && auxMatrix[nextI+1][j] < auxMatrix[nextI][j]) {
    nextI = nextI + 1;
  }
  minSumPath.unshift(nextI);
}

console.log(minSumPath.map(i => i+1).join(" "));
