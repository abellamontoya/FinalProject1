package com.example.instaestepario;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private NavController navController;
    private AppViewModel appViewModel;
    private ImageView photoImageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Set up Firestore query
        Query query = FirebaseFirestore.getInstance().collection("products").orderBy("name");

        // Configure RecyclerView options
        FirestoreRecyclerOptions<Product> options = new FirestoreRecyclerOptions.Builder<Product>()
                .setQuery(query, Product.class)
                .setLifecycleOwner(this)
                .build();

        // Set up adapter
        adapter = new ProductAdapter(options);
        recyclerView.setAdapter(adapter);

        // Set click listener for photoImageView
        photoImageView = view.findViewById(R.id.photoImageView);
        photoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.profileFragment);
            }
        });

        // Load user photo into photoImageView
        Uri photoUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
        if (photoUri != null) {
            String url = photoUri.toString();
            Glide.with(requireContext()).load(url).circleCrop().into(photoImageView);
        } else {
            // Load default placeholder image
            photoImageView.setImageResource(R.drawable.user);
        }
    }

    static class ProductAdapter extends FirestoreRecyclerAdapter<Product, ProductViewHolder> {
        public ProductAdapter(@NonNull FirestoreRecyclerOptions<Product> options) {
            super(options);
        }

        @NonNull
        @Override
        public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_recyclerview, parent, false);
            return new ProductViewHolder(view);
        }

        @Override
        protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
            holder.bind(model);
        }
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView nameTextView;
        TextView authorTextView;
        TextView priceTextView;
        TextView quantityTextView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.name);
            authorTextView = itemView.findViewById(R.id.author);
            priceTextView = itemView.findViewById(R.id.price);
            quantityTextView = itemView.findViewById(R.id.quantity);
        }

        public void bind(Product product) {
            Glide.with(itemView.getContext()).load(product.getImageURL()).into(productImageView);
            nameTextView.setText(product.getName());
            authorTextView.setText(product.getAuthor());
            priceTextView.setText(String.valueOf(product.getPrice()));
            quantityTextView.setText(String.valueOf(product.getQuantity()));
        }
    }
}
