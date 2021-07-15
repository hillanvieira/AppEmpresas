package br.com.hillan.appempresas.ui.adaper

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.hillan.appempresas.R
import br.com.hillan.appempresas.model.Enterprise
import br.com.hillan.appempresas.utils.ENTERPRISES_IMAGE_URL
import com.bumptech.glide.Glide

class EnterpriseListAdapter(
    val enterprises: List<Enterprise>,
    private val context: Context,
    var listener: (Enterprise) -> Unit = {}
) : RecyclerView.Adapter<EnterpriseListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.enterprise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val enterprise = enterprises[position]
        holder.bindView(enterprise, listener)
    }

    override fun getItemCount(): Int {
        return enterprises.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(enterprise: Enterprise, listener: (Enterprise) -> Unit) {
            val title: TextView = itemView.findViewById(R.id.item_title)
            val country: TextView = itemView.findViewById(R.id.item_country)
            val type: TextView = itemView.findViewById(R.id.item_type)
            val imageView: ImageView = itemView.findViewById(R.id.imageView)

            Glide.with(itemView).load("$ENTERPRISES_IMAGE_URL${enterprise.photo}")
                .placeholder(R.drawable.logo_home)
                .error(R.drawable.logo_home)
                .override(105, 80)
                .centerCrop()
                .into(imageView)

            title.text = enterprise.enterpriseName
            country.text = enterprise.country
            type.text = enterprise.enterpriseType.enterpriseTypeName
            //load image here after
            itemView.setOnClickListener { listener(enterprise) }
        }

    }

}