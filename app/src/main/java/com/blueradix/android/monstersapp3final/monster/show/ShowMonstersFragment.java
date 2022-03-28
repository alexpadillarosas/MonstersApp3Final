package com.blueradix.android.monstersapp3final.monster.show;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blueradix.android.monstersapp3final.Monster;
import com.blueradix.android.monstersapp3final.R;
import com.blueradix.android.monstersapp3final.databinding.ShowMonstersFragmentBinding;

import java.util.List;

public class ShowMonstersFragment extends Fragment implements OnItemClickListener{

    private ShowMonstersViewModel mViewModel;
    private ShowMonstersFragmentBinding binding;

    public static ShowMonstersFragment newInstance() {
        return new ShowMonstersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ShowMonstersFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShowMonstersViewModel.class);

        //check for the Bundle that arrives
        NavController navController = Navigation.findNavController(view);
        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey("ADD_MONSTER")){
            Monster monster = (Monster) getArguments().getSerializable("ADD_MONSTER");
            mViewModel.insert(monster);
        }

        if(bundle != null && bundle.containsKey("RATE_MONSTER")){
            Monster monster = (Monster) getArguments().getSerializable("RATE_MONSTER");
            mViewModel.update(monster);
        }

        binding.monstersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.monstersRecyclerView.setHasFixedSize(true);

        MonsterRecyclerViewAdapter adapter = new MonsterRecyclerViewAdapter(this);
        binding.monstersRecyclerView.setAdapter(adapter);

        final Observer<List<Monster>> allMonstersObserver = new Observer<List<Monster>>() {
            @Override
            public void onChanged(List<Monster> monsters) {
                // update RecyclerView, submitList compares the oldList(in the recyclerView)
                // with the new list coming from the database, and it will calculate at which
                // position the list has changed, so we don't redraw the whole recyclerView
                // just the Monsters that changed.
                adapter.submitList(monsters);
            }
        };
        mViewModel.getAllMonsters().observe(getViewLifecycleOwner(), allMonstersObserver);

        binding.addMonsterFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_showMonstersFragment_to_addMonsterScrollingFragment);
            }
        });

    }

    @Override
    public void onClick(Monster monster, View view) {
        Log.i("XYZ", monster.toString());

        Bundle bundle = new Bundle();
        bundle.putSerializable("RATE_MONSTER", monster);
        //From here try to navigate to the rate monster.
        NavController navController = Navigation.findNavController(view);
        navController.navigate(R.id.action_showMonstersFragment_to_rateMonsterScrollingFragment, bundle);
    }
}