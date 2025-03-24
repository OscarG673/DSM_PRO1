class Tienda { //definicion de la clase tienda

    private val inventario = Inventario() //nuevo objeto de la clase inventario
    private val carrito = Carrito(inventario)//nuevo objeto de la clase carrito, recbiendo inventario como parametro

    fun mostrarProductos() { //mostrar productos en existencia
        inventario.mostrarProductos() // llamada al metodo de la clase inventario
    }

    fun agregarAlCarrito() { //agregar productos al carrito
        carrito.agregarProducto() // llamada al metodo de la clase carrito
    }

    fun mostrarCarrito() { // mostrar productos dentro del carrito
        carrito.mostrarCarrito()
    }

    fun eliminarDelCarrito() { //eliminar productos del carrito
        carrito.eliminarProducto()
    }

    fun confirmarCompra() { //confirmar compra y generar factura
        carrito.confirmarCompra()
    }

    fun restaurarInventario() { //devolver productos si el usuario sale sin comprar
        carrito.restaurarInventario()
    }


}