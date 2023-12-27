package cl.lorimaralarcon.progra2_evaluacion1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import cl.lorimaralarcon.progra2_evaluacion1.modelo.CuentaMesa
import cl.lorimaralarcon.progra2_evaluacion1.modelo.ItemMenu
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val pasteldeChoclo = ItemMenu("Pastel de Choclo", 12000.0)
    private val cazuela = ItemMenu("Cazuela", 10000.0)
    private val cuentaMesa = CuentaMesa(0)
    private lateinit var etCantidadPastel: EditText
    private lateinit var etCantidadCazuela: EditText
    private lateinit var swPropina: Switch
    private lateinit var tvTotalComida: TextView
    private lateinit var tvTotalPropina: TextView
    private lateinit var tvTotalPedido: TextView
    private lateinit var tvTotalPastel: TextView
    private lateinit var tvTotalCazuela: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCantidadPastel = findViewById(R.id.etCantidadPastel)
        etCantidadCazuela = findViewById(R.id.etCantidadCazuela)
        swPropina = findViewById(R.id.swPropina)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        tvTotalPropina = findViewById(R.id.tvTotalPropina)
        tvTotalPedido = findViewById(R.id.tvTotalPedido)
        tvTotalPastel = findViewById(R.id.tvTotalPastel)
        tvTotalCazuela = findViewById(R.id.tvTotalCazuela)

        etCantidadPastel.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val cantidadPastel = etCantidadPastel.text.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(pasteldeChoclo, cantidadPastel)
                actualizarTotales()
            }
        }

        etCantidadCazuela.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val cantidadCazuela = etCantidadCazuela.text.toString().toIntOrNull() ?: 0
                cuentaMesa.agregarItem(cazuela, cantidadCazuela)
                actualizarTotales()
            }
        }

        swPropina.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            actualizarTotales()
        }
    }

        private fun actualizarTotales() {

            val totalPastel = cuentaMesa.obtenerItems().filter { it.itemMenu.nombre == "Pastel de Choclo" }
                .sumByDouble { it.calcularSubTotal() }

            val totalCazuela = cuentaMesa.obtenerItems().filter { it.itemMenu.nombre == "Cazuela" }
                .sumByDouble { it.calcularSubTotal() }

            tvTotalPastel.text = formatMoney(totalPastel)
            tvTotalCazuela.text = formatMoney(totalCazuela)

            val subtotalPorItem = cuentaMesa.obtenerItems().sumByDouble { it.calcularSubTotal() }

            tvTotalComida.text = formatMoney(subtotalPorItem)

            val totalSinPropina = cuentaMesa.calcularTotalSinPropina()
            val propina = cuentaMesa.calcularPropina()
            val totalConPropina = cuentaMesa.calcularTotalConPropina()

            tvTotalComida.text = formatMoney(totalSinPropina)
            tvTotalPropina.text = formatMoney(propina)
            tvTotalPedido.text = formatMoney(totalConPropina)
        }

        private fun formatMoney(value: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
        return format.format(value)
        }

    }
