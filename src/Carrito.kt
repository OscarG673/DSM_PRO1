import kotlin.math.round

class Carrito(private val inventario: Inventario) {
    private val carrito = mutableListOf<String>()

    fun agregarProducto() {
        print("Ingrese el ID del producto que desea agregar: ")
        val id = try {
            readlnOrNull()?.trim()?.toInt()?.toString() ?: return // leer, convertir y validar entrada
        } catch (e: NumberFormatException) {
            println("Error: Digite un número válido!. Verifique el ID de los productos ingresando en la opción 1 del menú.")
            println("Regresando al menú...")
            Thread.sleep(3000) // espera 3 segundos antes de volver al menú
            return
        }
        val producto = inventario.productos.find { it.startsWith(id) } //buscar el producto

        if (producto == null) { // Si el ID ingresado no existe
            println("ID no encontrado. Por favor, verifique el ID de los productos ingresando en la opción 1 del menú.")
            println("Regresando al menú...")
            Thread.sleep(3000) // espera 3 segundos antes de volver al menú
            return
        }

        val partes = producto.split("|")
        val stock = partes[3].toInt()

        print("Ingrese la cantidad: ")
        val cantidad = try {
            readlnOrNull()?.trim()?.toInt() ?: throw NumberFormatException() //verificar si el usuario digitó un número
        } catch (e: NumberFormatException) { //si se detecta una excepción
            println("Error: Digite un número válido! Verifique las existencias en la opción 1 del menú")
            println("Regresando al menú principal...")
            Thread.sleep(3000) // espera 3 segundos antes de volver al menú
            return
        }

        if (cantidad > stock) { //si la cantidad digitada por el usuario es mayor a la existencia
            println("Lo sentimos no existen suficientes existencias, verifique las existencias en la opción 1 del menú")
            println("Regresando al menú principal...")
            Thread.sleep(3000) // espera 3 segundos antes de volver al menú
            return
        }

        carrito.add("$id|${partes[1]}|${partes[2]}|$cantidad") //se añade al carrito el id del producto, el nombre, el precio y la cantidad tomada por el usuario
        inventario.actualizarInventario(id, stock - cantidad) //quitar la cantidad de producto del inventario mientras el usuario lo tenga en el carrito
        println("Producto agregado al carrito exitosamente!")
        println("Regresando al menú principal...")
        Thread.sleep(3000)
    }

    fun mostrarCarrito() {
        println("Productos en carrito:")
        var total = 0.0
        carrito.forEach { item ->
            val partes = item.split("|")
            val subtotal = partes[2].toDouble() * partes[3].toInt() //convertir precio a decimal y cantidad a entero
            total += subtotal
            println("${partes[1]} | Cantidad: ${partes[3]} | Precio: ${partes[2]}$ | Total: $subtotal$")
        }
        if (total == 0.0){
            println("Carrito vacío! Agregue un producto")
        }else{
            println("Total sin IVA: $total$")
        }
        Thread.sleep(3000)
    }

    fun eliminarProducto() {
        print("Ingrese el ID del producto a eliminar: ")
        val id = readlnOrNull()?.trim() ?: return
        val item = carrito.find { it.startsWith(id) } ?: run {
            println("Error: El producto no fue encontrado en su carrito! Digite un ID correcto. Revise su carrito en la opción 3 del menú")
            Thread.sleep(3000)
            return
        }

        val partes = item.split("|")
        val cantidad = partes[3].toInt()
        carrito.remove(item) //eliminar el producto

        val producto = inventario.productos.find { it.startsWith(id) }!!
        val partesProd = producto.split("|")
        val devolverProd = partesProd[3].toInt() + cantidad
        inventario.actualizarInventario(id, devolverProd)

        println("Producto eliminado del carrito.")
        Thread.sleep(3000)
    }
    fun restaurarInventario() { //devolver los productos al inventario si el usuario no confirma su compra y sale
        carrito.forEach { item ->
            val partes = item.split("|")
            val id = partes[0]
            val cantidad = partes[3].toInt()

            inventario.actualizarInventario(id, inventario.obtenerCantidad(id) + cantidad)
        }
        carrito.clear() //limpiar carrito ya que el usuario no compró
    }

    fun confirmarCompra() {
        var fees = 0.0
        var total = 0.0
        val iva = 0.13

        println("--------------------------------- Factura ----------------------------------")
        carrito.forEach { item ->
            val partes = item.split("|")
            val subtotal = partes[2].toDouble() * partes[3].toInt()
            total += subtotal
            fees = subtotal * iva
            println("${partes[1]} | Cantidad: ${partes[3]} | Precio: ${partes[2]}$ | Subtotal: $subtotal$")
        }

        if (total == 0.0) {
            println("Carrito vacío. No se puede generar factura.")
            Thread.sleep(3000)
            return
        }

        val totalf: Double = total+fees //total final con tarifas
        fees = round(fees * 100) / 100 //limitar a 2 decimales
        println("| Total sin IVA: $total$                                                   |")
        println("| IVA (13%): $fees$                                                        |")
        println("----------------------------------------------------------------------------")
        println("| Total Final: $totalf$                                                    |")
        println("--------------------------- GRACIAS POR SU COMPRA --------------------------")
        carrito.clear()
        println()
        println("Regresando al menú principal...")
        Thread.sleep(3000)
    }
}