package base.mvp;

import android.os.Bundle;

import base.BaseListViewActivity;

public abstract class MVPBaseListViewActivity<V, T extends BasePresenter<V>> extends BaseListViewActivity {

	protected T mPresenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mPresenter = createPresenter();

		mPresenter.attachView((V) this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mPresenter.detachView();
	}

	protected abstract T createPresenter();

}
