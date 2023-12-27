package cl.lorimaralarcon.progra2_evaluacion1.modelo

class CuentaMesa(val mesa: Int) {
    private val items = mutableListOf<ItemMesa>()
    var aceptaPropina: Boolean = true

    private var cantidadPastel: Int = 0
    private var cantidadCazuela: Int = 0

    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val itemMesa = ItemMesa(itemMenu, cantidad)
        agregarItem(itemMesa)

        when (itemMenu.nombre) {
            "Pastel de Choclo" -> cantidadPastel += cantidad
            "Cazuela" -> cantidadCazuela += cantidad
        }
    }

    fun agregarItem(itemMesa: ItemMesa){
        items.add(itemMesa)

        val cantidad = itemMesa.cantidad
        val itemMenu = itemMesa.itemMenu

        when (itemMenu.nombre) {
            "Pastel de Choclo" -> cantidadPastel += cantidad
            "Cazuela" -> cantidadCazuela += cantidad
        }
    }

    fun calcularTotalSinPropina(): Double{
        return items.sumByDouble { it.calcularSubTotal() }
    }

    fun calcularPropina(): Double{
        return if(aceptaPropina){
            calcularTotalSinPropina() * 0.10
        } else{
            0.0
        }
    }

    fun calcularTotalConPropina(): Double{
        return calcularTotalSinPropina() + calcularPropina()
    }

    fun obtenerItems(): List<ItemMesa> {
        return items.toList()
    }
}