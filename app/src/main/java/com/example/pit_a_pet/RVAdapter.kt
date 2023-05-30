import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.pit_a_pet.R
import com.example.pit_a_pet.Petdata

class RVAdapter(private var dataList: List<Petdata>, private val listener: OnItemClickListener) : RecyclerView.Adapter<RVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.petlist, parent, false)
        return ViewHolder(view)

    }

    fun setData(newDataList: List<Petdata>) {
        dataList = newDataList
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        private val pettext1: TextView = itemView.findViewById(R.id.startday)
        private val pettext2: TextView = itemView.findViewById(R.id.pettext2)
        private val pettext3: TextView = itemView.findViewById(R.id.pettext3)
        private val pettext4: TextView = itemView.findViewById(R.id.pettext4)
        private val pettext5: TextView = itemView.findViewById(R.id.endday)
        private val petimage: ImageView = itemView.findViewById(R.id.petimage1)


        fun bind(item: Petdata) {
            pettext1.text = item.postperiod
            pettext2.text = item.type
            pettext3.text = item.region
            pettext4.text = item.rescueplace
            pettext5.text = item.postperiod2
            Glide.with(itemView.context)
                .load(item.img)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .fitCenter()
                .override(120,120)
                .transform(CenterCrop(), RoundedCorners(20))
                .encodeQuality(70)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(petimage)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedItem = dataList[position]
                listener.onItemClicked(clickedItem)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: Petdata)
    }
}
