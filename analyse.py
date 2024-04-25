import sys
from scipy.optimize import curve_fit
from matplotlib import pyplot as plt
import pandas as pd
import numpy as np
from sklearn.metrics import r2_score

def model_log(n, a, b):
    return a * np.log(n) + b
 
def model_exp(n, a, b):
    return a * np.exp(b * n)

def model_poly(n, a, b):
    return a * n ** b

def model_linear(n, a, b):
    return a*n + b

def model_factorial(n, a, b):
    return a * np.sqrt(2*np.pi*n)*np.power(n/np.exp(1), n) + b

def model_linearithmic(n, a, b):
    return a * n * np.log(n) + b


def main():
    print("In python")
    csv_path = sys.argv[1]
    data = pd.read_csv(csv_path)
    x = data.iloc[:, 0]
    y = data.iloc[:, 1]

    models = [model_log, model_poly, model_linear]
    params = [curve_fit(i, x, y)[0] for i in models]
    preds = [models[i](x, *params[i]) for i in range(len(models))]
    r2s = [r2_score(y, preds[i]) for i in range(len(models))]

    best_model = np.argmax(r2s)
    print("Best model: ", best_model)

    print("Plotting curve...")
    best_params = params[best_model]
    if best_model == 0:
        leg = f"$y = {best_params[0]} \cdot \log(x) + {best_params[1]}$"
    elif best_model == 1:
        leg = f"$y = {best_params[0]} \cdot x^{{{best_params[1]}}}$"
    elif best_model == 2:
        leg = f"$y = {best_params[0]} \cdot x + {best_params[1]}$"

    plt.scatter(x, y)
    plt.plot(x, preds[best_model])
    plt.legend(["Measured Data", leg])
    plt.show()
    best_model = models[best_model]
    print("Best params: ", params[best_model])
    
if __name__ == '__main__':
    main()
