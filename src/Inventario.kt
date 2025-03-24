import java.io.File
import java.io.FileReader
import java.io.BufferedReader
import java.io.FileWriter

class Inventario {
    private val archivo = File("D:\\inventario.txt")
    val productos = mutableListOf<String>() //creacion de lista mutable con los productos

    init {
        if (archivo.exists()) { //verifica si existe
            BufferedReader(FileReader(archivo)).use { reader ->
                reader.forEachLine { productos.add(it) } //lee cada linea y lo agrega a la lista
            }
        }
    }

    fun mostrarProductos() {
        println("---------*Productos Disponibles*---------")
        productos.forEach { println(it) }
        println()
        println("Regresando al menú en 3 segundos...")
        Thread.sleep(3000)
    }

    fun actualizarInventario(id: String, nuevaCantidad: Int) { //recibe el id del producto como parametro y la cantidad actualizada
        val index = productos.indexOfFirst { it.startsWith(id) } //busca por id
        if (index != -1) {
            val partes = productos[index].split("|")
            productos[index] = "${partes[0]}|${partes[1]}|${partes[2]}|$nuevaCantidad"
            guardarArchivo()
        }
    }

    fun obtenerCantidad(id: String): Int { //obtener los productos del carrito
        val producto = productos.find { it.startsWith(id) } ?: return 0
        return producto.split("|")[3].toInt()
    }

    private fun guardarArchivo() {
        FileWriter(archivo).use { writer ->
            productos.forEach { writer.write(it + "\n") }
        }
    }
}