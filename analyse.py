import sys
from scipy.optimize import curve_fit
from scipy import stats
from matplotlib import pyplot as plt
import pandas as pd
import numpy as np
from sklearn.metrics import r2_score

def model_log(n, a, b):
    return np.log(a*n) + b

def model_linearithmic(n, a, b):
    return a * n * np.log(n) + b
 
def model_poly(n, a, b):
    return a * n ** b

def model_exp(n, a, b):
    return a * np.exp(b * n) if b * n < 300 else np.inf

# def model_factorial(n, a, b):
#     return a * np.sqrt(2*np.pi*n)*np.power(n/np.exp(1), n) + b
# # Not supported yet

#  def model_linear(n, a, b):
#      return a*n + b
# # Extremely problematic since both log(n) and nlog(n) look linear. Not recommended


def clean_data(data):
    iqr = stats.iqr(data.iloc[:, 1])
    q3 = np.percentile(data.iloc[:, 1], 75)
    filtered_data = data[(data.iloc[:, 1] < q3 + 1.5*iqr)]
    return filtered_data

def selective_fit(model, x, y):
    if model == model_poly:
        return curve_fit(model, x, y, bounds=((0, 1.5), (np.inf, np.inf)))
    
    return curve_fit(model, x, y)

def get_eq(model, params):
    if model == 0: # logarithmic
        return f"$t = \log({params[0]} \cdot n) + {params[1]}$"
    
    if model == 1: # linearithmic
        return f"$t = {params[0]} \cdot n \cdot \log(n) + {params[1]}$"
    
    if model == 2: # polynomial
        return f"$t = {params[0]} \cdot n^{{{params[1]}}}$"
    
    if model == 3: # exponential
        return f"$t = {params[0]} \cdot e^{{{params[1]}\cdot b}}$"

def main():
    print("Usage: python analyse.py <path_to_csv>")
    csv_path = sys.argv[1]
    unf_data = pd.read_csv(csv_path)
    data = clean_data(unf_data)
    x = data.iloc[:, 0]
    y = data.iloc[:, 1]

    models = [model_log, model_linearithmic, model_poly]
    params = [selective_fit(i,x,y)[0] for i in models]
    theos = [models[i](x, *params[i]) for i in range(len(models))]
    r2s = [r2_score(y, theos[i]) for i in range(len(models))]

    best_index = np.argmax(r2s)
    print("Best model: ", models[best_index].__name__)
    
    best_params = params[best_index]
    best_eq = get_eq(best_index, best_params)

    plt.scatter(x, y)
    plt.plot(x, theos[best_index], color='red')
    plt.legend(["Measured Data", best_eq])
    plt.show()
    
if __name__ == '__main__':
    main()
