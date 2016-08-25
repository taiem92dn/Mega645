package vn.dinosys.mega645.ui.fragment.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import vn.dinosys.mega645.di.base.HasComponent;

/**
 * Created by htsi.
 * Project: Mega645
 */
public class BaseFragment extends Fragment {
    protected void onScreenVisible(){}

    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("BaseFragment", "onViewCreated");
        ButterKnife.bind(this, view);
        setupComponent();
        onScreenVisible();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public String getTitle() {
        return null;
    }

    protected void setupComponent() {

    }
}
