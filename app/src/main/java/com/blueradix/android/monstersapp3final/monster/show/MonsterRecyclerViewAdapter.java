package com.blueradix.android.monstersapp3final.monster.show;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blueradix.android.monstersapp3final.Monster;
import com.blueradix.android.monstersapp3final.databinding.RecyclerItemViewBinding;


import java.util.List;


public class MonsterRecyclerViewAdapter extends RecyclerView.Adapter<MonsterViewHolder> {

    private RecyclerItemViewBinding binding;
    private List<Monster> monsters;
    private OnItemClickListener onItemClickListener;

    public MonsterRecyclerViewAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Creates a view holder whenever the RecyclerView needs a new one, it creates a view holder(data in one element of the recyclerView).
     * This is the moment when the row layout is inflated (grab the RecyclerItemViewBinding and turning it into GUI component).
     * Creates a new view Holder(MonsterViewHolder in this case) by passing the recently inflated view binding
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public MonsterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        binding = RecyclerItemViewBinding.inflate(inflater, parent, false);
        MonsterViewHolder monsterViewHolder = new MonsterViewHolder(binding);
        return monsterViewHolder;
    }

    /**
     * Takes a ViewHolder object and sets the proper list data (from the list) on the view
     * @param holder    an object of MonsterViewHolder class, representing each item (CardView content)
     *                  in the recyclerView
     * @param position  the position of the monster in the monsters list
     */
    @Override
    public void onBindViewHolder(@NonNull MonsterViewHolder holder, int position) {
        //get data from the list based on position
        Monster monster = monsters.get(position);
        //call the method to set the values in the MonsterViewHolder
        holder.updateMonster(monster);
        holder.bind(monster, onItemClickListener);
    }

    /**
     * @return  returns the total number of the list size. The list values are passed by the constructor
     */
    @Override
    public int getItemCount() {
        return monsters != null? monsters.size(): 0;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
        notifyDataSetChanged();
    }
}
