package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import olga.mendez.proyectoformativo_olga.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val textView: TextView = view.findViewById(R.id.txtPacienteDato)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
}