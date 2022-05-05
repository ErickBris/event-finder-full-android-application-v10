package com.projects.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.GlobalApplication;
import com.config.Config;
import com.config.UIConfig;
import com.db.Queries;
import com.libraries.adapters.MGRecyclerAdapter;
import com.libraries.asynctask.MGAsyncTaskNoDialog;
import com.libraries.dataparser.DataParser;
import com.libraries.helpers.DateTimeHelper;
import com.libraries.imageview.MGImageView;
import com.libraries.utilities.MGUtilities;
import com.models.DataResponse;
import com.models.News;
import com.projects.activities.NewsDetailActivity;
import com.projects.eventfinder.MainActivity;
import com.projects.eventfinder.R;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
	
	private View viewInflate;
	private ArrayList<News> arrayData;
	SwipeRefreshLayout swipeRefresh;
	RecyclerView mRecyclerView;
	RecyclerView.LayoutManager mLayoutManager;
	private Queries q;
	MGAsyncTaskNoDialog task;
	
	public NewsFragment() { }

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(task != null)
			task.cancel(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		viewInflate = inflater.inflate(R.layout.fragment_list_swipe, null);
		return viewInflate;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);	
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		q = GlobalApplication.getQueriesInstance(this.getActivity());

		mRecyclerView = (RecyclerView) viewInflate.findViewById(R.id.recyclerView);
		mRecyclerView.setHasFixedSize(true);

		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);

		swipeRefresh = (SwipeRefreshLayout) viewInflate.findViewById(R.id.swipe_refresh);
		swipeRefresh.setClickable(false);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			swipeRefresh.setProgressViewOffset(false, 0,100);
		}

		swipeRefresh.setColorSchemeResources(
				android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		showRefresh(true);
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getData();
			}
		}, Config.DELAY_SHOW_ANIMATION);

	}

	public void showRefresh(boolean show) {
		swipeRefresh.setRefreshing(show);
		swipeRefresh.setEnabled(show);
	}
	
	private void showList() {
		showRefresh(false);

		if(arrayData != null && arrayData.size() == 0) {
			MGUtilities.showNotifier(this.getActivity(), MainActivity.offsetY);
			return;
		}

		MGRecyclerAdapter adapter = new MGRecyclerAdapter(arrayData.size(), R.layout.news_entry);
		adapter.setOnMGRecyclerAdapterListener(new MGRecyclerAdapter.OnMGRecyclerAdapterListener() {

			@Override
			public void onMGRecyclerAdapterCreated(MGRecyclerAdapter adapter, MGRecyclerAdapter.ViewHolder v, int position) {
				final News news = arrayData.get(position);
				ImageView imgViewPhoto = (ImageView) v.view.findViewById(R.id.imgViewThumb);
				if(news.getPhoto_url() != null) {
					GlobalApplication.getImageLoaderInstance(getActivity())
							.displayImage(
									news.getPhoto_url(),
									imgViewPhoto,
									GlobalApplication.getDisplayImageOptionsInstance());
				}
				else {
					imgViewPhoto.setImageResource(UIConfig.SLIDER_PLACEHOLDER);
				}

				imgViewPhoto.setTag(position);
				Spanned name = Html.fromHtml(news.getNews_title());
				Spanned address = Html.fromHtml(news.getNews_content());

				TextView tvTitle = (TextView) v.view.findViewById(R.id.tvTitle);
				tvTitle.setText(name);

				TextView tvSubtitle = (TextView) v.view.findViewById(R.id.tvSubtitle);
				tvSubtitle.setText(address);

				String date = DateTimeHelper.getStringDateFromTimeStamp(news.getCreated_at(), "MM/dd/yyyy" );
				TextView tvDate = (TextView) v.view.findViewById(R.id.tvDate);
				tvDate.setText(date);

				v.view.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent i = new Intent(getActivity(), NewsDetailActivity.class);
						i.putExtra("news", news);
						getActivity().startActivity(i);
					}
				});
			}

		});
		mRecyclerView.setAdapter(adapter);
	}

	public void getData() {
		task = new MGAsyncTaskNoDialog(getActivity());
		task.setMGAsyncTaskListener(new MGAsyncTaskNoDialog.OnMGAsyncTaskListenerNoDialog() {

			@Override
			public void onAsyncTaskProgressUpdate(MGAsyncTaskNoDialog asyncTask) { }

			@Override
			public void onAsyncTaskPreExecute(MGAsyncTaskNoDialog asyncTask) { }

			@Override
			public void onAsyncTaskPostExecute(MGAsyncTaskNoDialog asyncTask) {
				// TODO Auto-generated method stub
				arrayData  = q.getNews();
				showList();
			}

			@Override
			public void onAsyncTaskDoInBackground(MGAsyncTaskNoDialog asyncTask) {
				// TODO Auto-generated method stub
				try {
					String strUrl = String.format("%s?api_key=%s",
							Config.GET_NEWS_JSON_URL,
							Config.API_KEY);

					DataResponse data = DataParser.getJSONFromUrlWithPostRequest(strUrl, null);
					MainActivity main = (MainActivity) getActivity();
					if (main == null)
						return;

					if (data == null)
						return;

					q.deleteTable("news");
					if (data.getNews() != null && data.getNews().size() > 0) {
						for (News news : data.getNews()) {
							q.insertNews(news);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		task.execute();
	}
}
