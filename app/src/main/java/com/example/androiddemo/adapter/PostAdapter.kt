package com.example.androiddemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddemo.databinding.ItemPostBinding
import com.example.androiddemo.network.Post

/**
 * Adapter para mostrar los posts obtenidos desde la API.
 */
class PostAdapter(
    private val posts: List<Post>
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.textViewPostId.text = "#${post.id}"
            binding.textViewPostTitle.text = post.title
            binding.textViewPostBody.text = post.body
        }
    }
}