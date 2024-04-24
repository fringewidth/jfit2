import sys
from scipy.optimize import curve_fit
from matplotlib import pyplot as plt
import pandas as pd
import numpy as np
from sklearn.metrics import r2_score

def model_log(n, a, b):
    return a * np.log()
 
def model_exp(n, a, b):
    return a * np.exp(b * n)

def model_poly(n, a, b):
    return a * n ** b

def model_linear(n, a, b):
    return a*n + b

def model_factorial(n, a, b):
    return a * np.sqrt(2*np.pi*n)*np.power(n/np.exp(1), n) + b


def main():
    csv_path = sys.argv[1]
    data = pd.read_csv(csv_path)
    x = data.iloc[:, 0]
    y = data.iloc[:, 1]

    models = [model_log, model_exp, model_poly, model_linear, model_factorial]
    params = [curve_fit(i, x, y)[0] for i in models]
    preds = [models[i](x, *params[i]) for i in range(len(models))]
    r2s = [r2_score(y, preds[i]) for i in range(len(models))]

    best_model = models[max(r2s)]

    fig, axs = plt.subplots(2)
    axs[0].scatter(x, y)
    axs[0].plot(x, best_model(x, *params[np.argmax(r2s)]))
    axs[1].plot(models, r2s)

if __name__ == '__main__':
    main()