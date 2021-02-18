package com.example.gallery.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gallery.R;
import com.example.gallery.model.Response;
import com.example.gallery.ui.GalleryAdapter;
import com.example.gallery.viewmodel.ListViewmodel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryFragment extends Fragment {
    RecyclerView photosList;
    TextView error;
    ProgressBar loading;
    SwipeRefreshLayout refreshLayout;

    private ListViewmodel viewmodel;
    private GalleryAdapter adapter = new GalleryAdapter(new ArrayList<>());

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        photosList =view.findViewById(R.id.recyclerView);
        error = view.findViewById(R.id.list_error);
        loading = view.findViewById(R.id.loading_view);
        refreshLayout = view.findViewById(R.id.refreshLayout);

        viewmodel = new ViewModelProvider(this).get(ListViewmodel.class);
        viewmodel.referesh();

        photosList.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        photosList.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewmodel.referesh();
                refreshLayout.setRefreshing(false);
            }
        });

        observeViewModel();

    }

    private void observeViewModel() {
        viewmodel.photos.observe(getViewLifecycleOwner(), new Observer<Response>() {
            @Override
            public void onChanged(Response response) {
                if(response!=null){
                    photosList.setVisibility(View.VISIBLE);
                    adapter.updatePhotos(response.getPhotos().getPhoto());
                }
            }
        });


        viewmodel.LoadError.observe(getViewLifecycleOwner(), isError -> {
            if(isError != null)
                error.setVisibility(isError? View.VISIBLE:View.GONE);
        });

        viewmodel.loading.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading != null)
                    loading.setVisibility(isLoading? View.VISIBLE:View.GONE);
                if(isLoading)
                {
                    photosList.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                }
            }
        });
    }
}