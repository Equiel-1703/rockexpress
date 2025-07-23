const NumberFormattter = new Intl.NumberFormat('pt-BR', {
  minimumFractionDigits: 2, // Ensures you have at least 2 decimal places (e.g., .00)
  maximumFractionDigits: 2, // Ensures you don't have more than 2 decimal places
});

export default NumberFormattter;