import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pit_a_pet.R

class ImagePagerAdapter(var images: ArrayList<Int>, var urls: ArrayList<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.PagerViewHolder>() {

    private lateinit var image: ImageView


    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)) {
        val image: ImageView = itemView.findViewById(R.id.imageSlider)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        val viewHolder = PagerViewHolder(view as ViewGroup)

        // 이미지 클릭 시 URL 열기
        viewHolder.image.setOnClickListener {
            val position = viewHolder.adapterPosition
            val url = urls[position]

            if(url != ""){
                // URL 열기 작업 수행 (예: 웹 브라우저 열기)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                viewHolder.itemView.context.startActivity(intent)
            }
            else{
                Toast.makeText(viewHolder.image.context, "연결된 링크가 없습니다.",Toast.LENGTH_SHORT).show()
            }
        }

        return viewHolder
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.image.setImageResource(images[position])
    }
}