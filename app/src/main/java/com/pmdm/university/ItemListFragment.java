package com.pmdm.university;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.pmdm.university.databinding.FragmentItemListBinding;
import com.pmdm.university.databinding.ItemListContentBinding;

import com.pmdm.university.entidad.University;

import com.pmdm.university.interfaz.IUniversityApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ItemListFragment extends Fragment {

    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://universities.hipolabs.com/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();

        IUniversityApiService iUniversityApiService = retrofit.create(IUniversityApiService.class);
        Call<List<University>> call = iUniversityApiService.getAll();

        call.enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                if (response.isSuccessful()) {
                    List<University> universityList = response.body();
                    View recyclerView = getActivity().findViewById(R.id.item_list);
                    assert recyclerView !=null;
                    setupRecyclerView((RecyclerView) recyclerView, itemDetailFragmentContainer, universityList);
                } else {
                    Log.d("Error", "Something happened");
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<University>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View itemDetailFragmentContainer,
            List<University> universityList
    ) {

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                universityList,
                itemDetailFragmentContainer
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<University> mValues;
        private final View mItemDetailFragmentContainer;

        SimpleItemRecyclerViewAdapter(List<University> items,
                                      View itemDetailFragmentContainer) {
            mValues = items;
            mItemDetailFragmentContainer = itemDetailFragmentContainer;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding =
                    ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            String name = mValues.get(position).getName();
            holder.mContentView.setText(name);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(itemView ->{
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.NAME, name);
                arguments.putString(ItemDetailFragment.URL, mValues.get(position).getWebPages().get(0));
                if (mItemDetailFragmentContainer !=null){
                    Navigation.findNavController(mItemDetailFragmentContainer)
                            .navigate(R.id.item_detail_nav_container, arguments);
                }else {
                    Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(ItemListContentBinding binding) {
                super(binding.getRoot());
                mContentView = binding.content;
            }

        }
    }
}