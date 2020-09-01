package com.gan.breakingbadcharacters.ui.characterslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gan.breakingbadcharacters.R
import com.gan.breakingbadcharacters.data.model.BreakingBadCharacter
import com.gan.breakingbadcharacters.ui.utils.ImageLoader
import kotlinx.android.synthetic.main.list_item_character.view.*

class CharactersAdapter(
    private val selectedListener: (BreakingBadCharacter) -> Unit,
    private val imageLoader: ImageLoader
) : ListAdapter<BreakingBadCharacter, CharacterViewHolder>(CharacterItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CharacterViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_character, parent, false
        ),
        imageLoader = imageLoader
    )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), selectedListener)
    }
}

class CharacterViewHolder(
    itemView: View,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: BreakingBadCharacter?, selectedListener: (BreakingBadCharacter) -> Unit) {
        item ?: return

        itemView.setOnClickListener {
            selectedListener.invoke(item)
        }
        imageLoader.loadCircled(item.avatarUrl, itemView.characterAvatarView)
        itemView.characterNameView.text = item.name
    }
}

class CharacterItemDiffCallback : DiffUtil.ItemCallback<BreakingBadCharacter>() {

    override fun areItemsTheSame(
        oldItem: BreakingBadCharacter,
        newItem: BreakingBadCharacter
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: BreakingBadCharacter,
        newItem: BreakingBadCharacter
    ) = oldItem == newItem
}
