import numpy as np
import skfuzzy as fuzz
from skfuzzy import control as ctrl

# Definir las variables de entrada
grosor = ctrl.Antecedent(np.arange(0, 6, 1), 'grosor')
tipo_carne = ctrl.Antecedent(np.arange(0, 1.1, 0.1), 'tipo_carne')
termino = ctrl.Antecedent(np.arange(0, 1.1, 0.1), 'termino')

# Definir la variable de salida
tiempo = ctrl.Consequent(np.arange(0, 31, 1), 'tiempo')

# Definir las funciones de membresía para cada variable
grosor['delgado'] = fuzz.trimf(grosor.universe, [0, 1, 2])
grosor['medio'] = fuzz.trimf(grosor.universe, [1, 2, 4])
grosor['grueso'] = fuzz.trimf(grosor.universe, [3, 4, 5])

tipo_carne['carne_res'] = fuzz.trimf(tipo_carne.universe, [0, 0.5, 1])
tipo_carne['carne_cerdo'] = fuzz.trimf(tipo_carne.universe, [0.5, 1, 1])

termino['poco_hecho'] = fuzz.trimf(termino.universe, [0, 0.5, 1])
termino['medio_hecho'] = fuzz.trimf(termino.universe, [0.25, 0.75, 1])
termino['bien_hecho'] = fuzz.trimf(termino.universe, [0.5, 1, 1])

tiempo['poco'] = fuzz.trimf(tiempo.universe, [0, 5, 10])
tiempo['medio'] = fuzz.trimf(tiempo.universe, [5, 10, 20])
tiempo['mucho'] = fuzz.trimf(tiempo.universe, [15, 25, 30])

# Definir las reglas
rule1 = ctrl.Rule(grosor['grueso'] & tipo_carne['carne_res'] & termino['medio_hecho'], tiempo['mucho'])
rule2 = ctrl.Rule(grosor['medio'] & tipo_carne['carne_res'] & termino['poco_hecho'], tiempo['poco'])
rule3 = ctrl.Rule(grosor['delgado'] & tipo_carne['carne_res'] & termino['medio_hecho'], tiempo['medio'])
rule4 = ctrl.Rule(tipo_carne['carne_cerdo'], tiempo['mucho'])  # Carne de cerdo siempre bien hecha
rule5 = ctrl.Rule(grosor['grueso'] & tipo_carne['carne_res'] & termino['poco_hecho'], tiempo['poco'])
rule6 = ctrl.Rule(grosor['medio'] & tipo_carne['carne_res'] & termino['medio_hecho'], tiempo['medio'])
rule7 = ctrl.Rule(grosor['delgado'] & tipo_carne['carne_res'] & termino['poco_hecho'], tiempo['poco'])
rule8 = ctrl.Rule(grosor['grueso'] & tipo_carne['carne_cerdo'] & termino['bien_hecho'], tiempo['mucho'])
rule9 = ctrl.Rule(grosor['medio'] & tipo_carne['carne_cerdo'] & termino['bien_hecho'], tiempo['mucho'])
rule10 = ctrl.Rule(grosor['delgado'] & tipo_carne['carne_cerdo'] & termino['bien_hecho'], tiempo['mucho'])

# Crear el sistema de control difuso
system = ctrl.ControlSystem([rule1, rule2, rule3, rule4, rule5, rule6, rule7, rule8, rule9, rule10])
cooking_time = ctrl.ControlSystemSimulation(system)

# Establecer los valores de las entradas
cooking_time.input['grosor'] = 2
cooking_time.input['tipo_carne'] = 0.4
cooking_time.input['termino'] = 0.7

# Computar el resultado
cooking_time.compute()

# Obtener el resultado
print("Tiempo de cocción estimado:", cooking_time.output['tiempo'], "minutos")

# Imprimir la temperatura recomendada
temperatura = 180 + cooking_time.output['tiempo'] * 5  # Supongamos una relación lineal entre el tiempo y la temperatura
print("Temperatura recomendada:", temperatura, "grados Celsius")
