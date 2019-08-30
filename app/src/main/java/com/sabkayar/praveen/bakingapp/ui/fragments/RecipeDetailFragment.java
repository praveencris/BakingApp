package com.sabkayar.praveen.bakingapp.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sabkayar.praveen.bakingapp.R;
import com.sabkayar.praveen.bakingapp.databinding.FragmentRecipeDetailBinding;
import com.sabkayar.praveen.bakingapp.model.Ingredient;
import com.sabkayar.praveen.bakingapp.model.Recipe;
import com.sabkayar.praveen.bakingapp.model.Step;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeDetailFragment extends Fragment implements RecipeDetailAdapter.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentRecipeDetailBinding mBinding;

    // TODO: Rename and change types of parameters
    private Recipe mRecipe;

    private OnFragmentInteractionListener mListener;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param recipe Parameter 1.
     * @return A new instance of fragment RecipeDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeDetailFragment newInstance(Recipe recipe) {
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, recipe);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        View itemView = mBinding.getRoot();
        List<Ingredient> ingredientList = mRecipe.getIngredients();
        for (int i = 0; i < ingredientList.size(); i++) {
            mBinding.tvIngredients.append(i+ " : " + ingredientList.get(i).getQuantity() + " " + ingredientList.get(i).getMeasure() + " " + ingredientList.get(i).getIngredient() + "\n");
        }
        RecyclerView recyclerView = mBinding.rvRecipeDetail;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        RecipeDetailAdapter recipeDetailAdapter = new RecipeDetailAdapter(mRecipe.getSteps(), getActivity(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recipeDetailAdapter);


        return itemView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(List<Step> steps,int position) {
        if (mListener != null) {
            mListener.onFragmentInteraction(steps,position);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(List<Step> steps,int position) {
        onButtonPressed(steps,position);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(List<Step> steps,int position);
    }
}
