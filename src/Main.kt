fun main() {
    val tienda = Tienda() //creacion de nuevo objeto de la clase tienda
    var opcion: Int

    do {
        println("|------------***Bienvenido a Tienda Naranja***-------------|")
        println("| 1. Ver productos disponibles                             |")
        println("| 2. Agregar producto al carrito                           |")
        println("| 3. Ver carrito                                           |")
        println("| 4. Eliminar producto del carrito                         |")
        println("| 5. Confirmar compra                                      |")
        println("| 6. Salir                                                 |")
        println("|__________________________________________________________|")
        print("Digite una opción (1-6): ")

        opcion = readLine()?.toIntOrNull() ?: 0 //lectura de datos

        when (opcion) {
            1 -> tienda.mostrarProductos()
            2 -> tienda.agregarAlCarrito()
            3 -> tienda.mostrarCarrito()
            4 -> tienda.eliminarDelCarrito()
            5 -> tienda.confirmarCompra()
            6 -> {
                println("Gracias por su visita, usted está saliendo...")
                tienda.restaurarInventario()
            }
            else -> {
                println("Opción inválida, asegúrese de ingresar una de las opciones (1-6)")
                println("Regresando al menu principal...")
                Thread.sleep(3000)  // espera de 3 segundos antes de mostrar menú principal nuevamente
            }


        }
    } while (opcion != 6) //mientras no sea 6 repetir
}
