import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.example.pit_a_pet.R
import com.example.pit_a_pet.petdata

class RVAdapter(private val dataList: List<petdata>) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.petlist, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pettext1: TextView = itemView.findViewById(R.id.pettext1)
        private val pettext2: TextView = itemView.findViewById(R.id.pettext2)
        private val pettext3: TextView = itemView.findViewById(R.id.pettext3)
        private val pettext4: TextView = itemView.findViewById(R.id.pettext4)
        private val petimage: ImageView = itemView.findViewById(R.id.petimage1)


        fun bind(item: petdata) {
            pettext1.text = item.postperiod
            pettext2.text = item.type
            pettext3.text = item.region
            pettext4.text = item.rescueplace
            Glide.with(itemView.context)
                .load(item.img)
                .fitCenter()
                .override(120,120)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(petimage)
        }
    }




}
