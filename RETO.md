# Fase 1 — Modelo de Datos: Pedidos y Productos

## Atributos

**Order (Pedido)**

| Atributo | Tipo | Descripción |
|---|---|---|
| id | Long | Identificador único, generado por la base de datos. |
| customer | String | Nombre del cliente que realiza el pedido. |
| products | List\<Product\> | Productos incluidos en el pedido. |
| date | LocalDate | Fecha de creación del pedido. |
| status | OrderStatus (enum) | Estado del pedido: PENDING, PROCESSED, DELIVERED, CANCELED. |

**Product (Producto)**

| Atributo | Tipo | Descripción |
|---|---|---|
| id | Long | Identificador único, generado por la base de datos. |
| name | String | Nombre del producto. |
| price | double | Precio unitario del producto. |

## Relación entre Pedidos y Productos

Un `Order` puede contener varios `Product`, y un `Product` puede pertenecer a varios pedidos distintos (es un catálogo reutilizable, no algo exclusivo de un pedido). Es una relación **muchos-a-muchos**, mapeada en JPA con `@ManyToMany` desde `Order` hacia `Product`, usando una tabla intermedia `order_products` con las columnas `order_id` y `product_id`.

## Encapsulación y abstracción

Los atributos de ambas clases son `private`, accesibles solo mediante getters/setters. Toda la validación de estado vive dentro de la propia clase (en el constructor y en los setters), de modo que un `Order` o `Product` nunca puede existir en un estado inválido: quien usa estas clases no necesita conocer ni repetir las reglas de negocio, solo confiar en que el objeto se valida a sí mismo.

## Reglas de validación

**Order**
- `customer`: no puede ser nulo ni estar en blanco.
- `products`: no puede ser nulo ni una lista vacía (todo pedido debe tener al menos un producto).
- `date`: no puede ser nula ni una fecha futura.
- `status`: no puede ser nulo.

**Product**
- `name`: no puede ser nulo ni estar en blanco.
- `price`: debe ser mayor que cero.

Estas validaciones se implementaron en `Order.java` y `Product.java`, lanzando `IllegalArgumentException` cuando se viola una regla, tanto desde el constructor como desde los setters correspondientes.

# Fase 2 y 3 — Lógica de Negocio, Estados y Refactorización

## Gestión de productos en un pedido

`Order.addProduct(Product)` y `Order.removeProduct(Product)` agregan/quitan productos con estas reglas:

- Solo se permite modificar productos si el pedido está en estado `PENDING`.
- No se puede agregar un producto ya presente en el pedido (comparación por `id`, vía `equals`/`hashCode` en `Product`).
- No se puede quitar un producto si el pedido quedaría sin ninguno (regla ya definida en Fase 1: mínimo un producto).

`OrderService` expone `addProductToOrder(id, product)` y `removeProductFromOrder(id, product)`, que buscan el pedido y delegan la regla de negocio al propio `Order`.

## Manejo de estados del pedido: patrón State

En vez de permitir `order.setStatus(cualquierEstado)` sin control, se introdujo el paquete `com.pragma.orders.domain.state` con una jerarquía basada en herencia y polimorfismo:

- `OrderState` (clase abstracta): define `canTransitionTo(OrderStatus)` y `allowsModification()`.
- `PendingState`, `ProcessedState`, `DeliveredState`, `CanceledState`: cada una implementa sus propias transiciones válidas.
  - `PENDING` → `PROCESSED` o `CANCELED`.
  - `PROCESSED` → `DELIVERED` o `CANCELED`.
  - `DELIVERED` y `CANCELED` son estados finales (no permiten más transiciones).
- `OrderStateFactory` (patrón Factory): resuelve la instancia de `OrderState` correspondiente al `OrderStatus` actual del pedido.

`Order.changeStatus(OrderStatus)` usa la fábrica para obtener el estado actual y le delega la validación (`validateTransitionTo`), lanzando `IllegalStateException` si la transición no es válida. `setStatus` se conserva como setter simple (usado por el constructor y por JPA), mientras que `changeStatus` es el punto de entrada para la lógica de negocio de transición.

Esta decisión evita un bloque `if/else` o `switch` gigante y disperso en `OrderService`: cada estado es responsable únicamente de sus propias transiciones, y agregar un estado nuevo en el futuro implica añadir una clase, no tocar las existentes (principio abierto/cerrado).

## Refactor de `OrderService`

- Se extrajo `findOrderOrThrow(id)` para eliminar la duplicación de `orderRepository.findById(id).orElseThrow(...)` repetida en cuatro métodos.
- Se reemplazó `new RuntimeException("Order not found")` por una excepción de dominio propia, `OrderNotFoundException`, más expresiva y fácil de capturar específicamente si se necesitara en el futuro (por ejemplo, para mapearla a un 404 en un controlador REST).

## Tests agregados

En `OrderServiceTest` se agregaron casos para: transición de estado inválida (`PENDING → DELIVERED` debe fallar), agregar producto a un pedido `PENDING`, quitar producto de un pedido `PENDING`, y bloqueo de modificación de productos en un pedido que no está `PENDING`. También se corrigió `changeOrderStatusTest`, que originalmente probaba una transición inválida (`PENDING → DELIVERED`); ahora parte de `PROCESSED → DELIVERED`, que sí es válida según las reglas definidas.
