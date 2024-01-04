//线性回归分析
import LinearRegression from 'ml-regression'
  
// 训练数据  
let x = [1, 2, 3, 4, 5];  
let y = [2, 4, 6, 8, 10];  
  
let regression = new LinearRegression();  
regression.train(x, y);  
  
console.log('Intercept:', regression.intercept()); // 截距  
console.log('Slope:', regression.slope()); // 斜率  
console.log('Predicted y for x=6:', regression.predict(6)); // 预测值