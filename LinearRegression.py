import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LinearRegression

# Cargar el dataset CSV
bitcoin_data = pd.read_csv('BTC-EUR.csv')

# Filtrar solo las columnas relevantes
bitcoin_data = bitcoin_data[['Date', 'Close']]

# Convertir la columna de fecha a tipo datetime
bitcoin_data['Date'] = pd.to_datetime(bitcoin_data['Date'])

# Añadir una nueva columna numérica para representar la fecha como días desde el inicio
bitcoin_data['Days'] = (bitcoin_data['Date'] - bitcoin_data['Date'].min()).dt.days

# Mostrar los primeros registros del dataset
print(bitcoin_data.head())

# Dividir los datos en conjuntos de entrenamiento y prueba
X = bitcoin_data[['Days']]
y = bitcoin_data['Close']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=0)

# Crear el modelo de regresión lineal
model = LinearRegression()

# Entrenar el modelo con los datos de entrenamiento
model.fit(X_train, y_train)

# Predecir los precios de cierre para los datos de prueba
y_pred = model.predict(X_test)

# Graficar los datos y la línea de regresión
plt.figure(figsize=(10, 6))
plt.scatter(X_test, y_test, color='blue', label='Datos de prueba')
plt.plot(X_test, y_pred, color='red', label='Línea de regresión')
plt.xlabel('Días desde el inicio')
plt.ylabel('Precio de cierre de Bitcoin')
plt.title('Regresión Lineal sobre el Precio de Cierre de Bitcoin')
plt.legend()
plt.grid(True)
plt.show()
