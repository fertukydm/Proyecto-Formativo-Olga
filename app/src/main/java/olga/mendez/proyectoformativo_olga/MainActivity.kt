package olga.mendez.proyectoformativo_olga

import Modelo.Conexion
import Modelo.ListaHospital
import android.os.Bundle
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                val Nombre = resultSet.getString("NombreHOS")
                val Apellido = resultSet.getInt("ApellidoHOS")
                val Edad = resultSet.getInt("EdadHOS")
                val Enfermedad = resultSet.getInt("EnfermedadHOS")
                val NumeroHabitacion = resultSet.getInt("NumHab")
                val NumeroCama = resultSet.getInt("NumCama")
                val Medicamentos = resultSet.getInt("Medicamentos")

                val Lista = ListaHospital(uuid,Nombre, Apellido, Edad,Enfermedad, NumeroHabitacion, NumeroCama,Medicamentos)
                ListaHospital.add(Lista)
            }
            return ListaHospital
    }
        CoroutineScope(Dispatchers.IO).launch {
            val ejecutarFuncion = obtenerDatos()


            withContext(Dispatchers.Main){
                //Asigno el adaptador mi RecyclerView
                //(Uno mi Adaptador con el RecyclerView)
                val miAdaptador = Adaptador(ejecutarFuncion)
                rcvDatos.adapter = miAdaptador
}