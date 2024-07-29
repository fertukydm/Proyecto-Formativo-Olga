package olga.mendez.proyectoformativo_olga

import Modelo.Conexion
import Modelo.ListaHospital
import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val txtNombre= findViewById<EditText>(R.id.txtNombreHos)
        val txtApellidos= findViewById<EditText>(R.id.txtApellidosHos)
        val txtEdad = findViewById<EditText>(R.id.txtEdadHos)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarPaciente)
        val txtEnfermedad = findViewById<EditText>(R.id.txtEnfermedadHos)
        val txtHabitacion = findViewById<EditText>(R.id.txtNumHabiHos)
        val txtCama = findViewById<EditText>(R.id.txtNumCamaHos)
        val txtMedicamentos = findViewById<EditText>(R.id.txtMedicamentosHos)
        val rcvDatos = findViewById<RecyclerView>(R.id.rcvDatos)

        rcvDatos.layoutManager = LinearLayoutManager(this)

        fun obtenerDatos(): List<ListaHospital> {
            //1- Creo una objeto de la clase conexion
            val objConexion = Conexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM HospitalBD")!!

            val listadoProductos = mutableListOf<ListaHospital>()

            while (resultSet.next()){
                val uuid = resultSet.getString("uuid")
                val nombre = resultSet.getString("NombreHOS")
                val apellido = resultSet.getInt("ApellidoHOS")
                val edad = resultSet.getInt("EdadHOS")
                val enfermedad = resultSet.getInt("EnfermedadHOS")
                val numeroHabitacion = resultSet.getInt("NumHab")
                val numeroCama = resultSet.getInt("NumCama")
                val medicamentos = resultSet.getInt("Medicamentos")

                val Lista = ListaHospital()

                Lista.add(Lista)
                return ListaHospital
            }

    }
        CoroutineScope(Dispatchers.IO).launch {
            val ejecutarFuncion = obtenerDatos()


            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(ejecutarFuncion)
                rcvDatos.adapter = miAdaptador
}
            btnAgregar.setOnClickListener {
                GlobalScope.launch(Dispatchers.IO){

                    //1- Crear un objeto de la clase de conexion
                    val objConexion = Conexion().cadenaConexion()

                    //2- Crear una variable que sea igual a un PrepareStatement
                    val addProducto = objConexion?.prepareStatement("insert into tbProductos1(uuid, nombreProducto, precio, cantidad) values(?, ?, ?, ?)")!!
                    addProducto.setString(1, UUID.randomUUID().toString())
                    addProducto.setString(2, txtNombre.text.toString())
                    addProducto.setInt(3, txtApellidos.text.toString().toInt())
                    addProducto.setInt(4, txtEdad.text.toString().toInt())

                    addProducto.executeUpdate()

                    val NuevosPacientes = obtenerDatos()
                    withContext(Dispatchers.Main){
                        (rcvDatos.adapter as? Adaptador)?.actualizarRecyclerView(NuevosPacientes)
                    }
                }
            }

        }
    }