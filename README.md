# Aplicación de Principios OOP en un Sistema de Gestión de Pedidos

Tu tarea es desarrollar un sistema de gestión de pedidos para una tienda en línea. El sistema debe permitir a los usuarios crear, modificar y eliminar pedidos. Cada pedido debe tener atributos como ID, cliente, productos, fecha y estado. Además, el sistema debe manejar la validación de datos y gestionar los estados del pedido de manera eficiente.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Desarrollo de Software con OOP |
| **Nivel** | advanced-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 4-6 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Modelado de Pedidos

**Objetivo:** Definir la estructura y atributos de los pedidos.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Identifica los atributos necesarios para un pedido.
- Define las relaciones entre pedidos y productos.
- Establece las reglas de validación para los atributos del pedido.

**Entregable:** Modelo de datos para pedidos y productos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera los principios de encapsulación y abstracción.
- Piensa en cómo manejarías la relación entre pedidos y productos.

</details>

### Fase 2: Implementación de la Lógica de Negocio

**Objetivo:** Implementar la lógica para crear, modificar y eliminar pedidos.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Crea métodos para agregar y eliminar productos de un pedido.
- Implementa la validación de datos para los atributos del pedido.
- Maneja los estados del pedido (pendiente, procesado, entregado, cancelado).

**Entregable:** Código funcional para la gestión de pedidos.

<details>
<summary>Pistas de conocimiento</summary>

- Utiliza los principios de herencia y polimorfismo donde sea adecuado.
- Considera cómo manejarías los diferentes estados del pedido.

</details>

### Fase 3: Optimización y Refactorización

**Objetivo:** Optimizar el código y realizar refactorización para mejorar la mantenibilidad.

**Tiempo estimado:** 1 hora

**Instrucciones:**

- Identifica áreas de mejora en el código.
- Refactoriza el código para mejorar la legibilidad y mantenibilidad.
- Aplica patrones de diseño relevantes para optimizar la lógica de negocio.

**Entregable:** Código refactorizado y optimizado.

<details>
<summary>Pistas de conocimiento</summary>

- Considera patrones de diseño como el patrón de fábrica o el patrón de estrategia.
- Piensa en cómo podrías mejorar la escalabilidad del sistema.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué son los principios de OOP y cómo se aplican en este reto?
- **paraQueSirve**: ¿Para qué sirven los atributos y relaciones definidos en la fase 1?
- **comoSeUsa**: ¿Cómo se usa la herencia y el polimorfismo en la implementación de la lógica de negocio?
- **erroresComunes**: ¿Qué errores comunes se pueden encontrar al manejar los estados del pedido y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica la refactorización del código y cómo se justifican?

## Criterios de Evaluacion

- Definición clara de atributos y relaciones para pedidos y productos.
- Implementación correcta de la lógica de negocio para crear, modificar y eliminar pedidos.
- Validación adecuada de datos para los atributos del pedido.
- Manejo eficiente de los estados del pedido.
- Refactorización y optimización del código con patrones de diseño relevantes.

---

*Reto generado automaticamente por Challenge Generator - Pragma*
