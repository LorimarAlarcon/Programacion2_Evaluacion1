package cl.lorimaralarcon.progra2_evaluacion1.modelo

class ItemMesa(val itemMenu: ItemMenu, val cantidad: Int) {
    fun calcularSubTotal(): Double {
        return itemMenu.precio * cantidad
    }
}

