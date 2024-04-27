import sys
import numpy as np
import pandas as pd
from matplotlib import pyplot as plt
from scipy.optimize import curve_fit
from sklearn.metrics import r2_score

def logarithmic(n, a, b):
    return np.log(a*n) + b

def linearithmic(n, a, b):
    return a * n * np.log(n) + b
 
def polynomial(n, a, b):
    return a * n ** b

def clean(data, cutoff = 1.5):
    q1 = data.iloc[:, 1].quantile(0.25)
    q3 = data.iloc[:, 1].quantile(0.75)
    iqr = q3 - q1
    data_cl = data[(data.iloc[:, 1] < (q3  + cutoff * iqr)) & (data.iloc[:, 1] > (q1 - cutoff * iqr))]
    return data_cl

def smoothen(data, window = 16):
    if data.shape[0] // 2 < window:
        return data
    data_sm = data.rolling(window=window).mean()
    data_sm.dropna(inplace = True)
    return data_sm

def selective_fit(model, x, y):
    if model is logarithmic or model is linearithmic:
        bounds = ([0, -np.inf], [np.inf, np.inf])
    elif model is polynomial:
        bounds = ([0, 1], [np.inf, np.inf])
    
    return curve_fit(model, x, y, bounds=bounds)

def get_eq(model, params, acc = 16):
    params = [round(i, acc) for i in params]
    if model is logarithmic:
        return f"t = log({params[0]}n) + {params[1]}"
    elif model is linearithmic:
        return f"t = {params[0]}nlog(n) + {params[1]}"
    elif model is polynomial:
        return f"t = {params[0]}n^({params[1]})"

def main():
    data = pd.read_csv("runningTimes.csv")
    data_proc = smoothen(clean(data))
    xp = data_proc.iloc[:, 0]
    yp = data_proc.iloc[:, 1]
    x = data.iloc[:, 0]
    y = data.iloc[:, 1]
    models = [logarithmic, linearithmic, polynomial]
    params= [selective_fit(i, x, y)[0] for i in models]
    coeff_det = [r2_score(y, models[i](x, *params[i])) for i in range(len(models))]

    best_index = np.argmax(coeff_det)
    print("Best model: ", models[best_index].__name__)
    print(get_eq(models[best_index], params[best_index]))

    plt.plot(x, models[best_index](x, *params[best_index]), color = 'red')
    plt.legend([get_eq(models[best_index], params[best_index], 3)])
    plt.scatter(x, y, color = 'gray')
    plt.scatter(xp, yp)
    plt.show()


if __name__ == "__main__":
    main() 