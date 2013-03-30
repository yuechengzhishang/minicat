package org.mcxiaoke.fancooker.fragments;

import org.mcxiaoke.fancooker.App;
import org.mcxiaoke.fancooker.api.Paging;
import org.mcxiaoke.fancooker.controller.DataController;
import org.mcxiaoke.fancooker.dao.model.StatusModel;
import org.mcxiaoke.fancooker.service.FanFouService;
import org.mcxiaoke.fancooker.util.Utils;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;


/**
 * @author mcxiaoke
 * @version 1.0 2012.02.06
 * @version 1.1 2012.03.08
 * @version 1.2 2012.03.19
 * 
 */
public class MentionTimelineFragment extends BaseTimlineFragment {
	private static final String TAG = MentionTimelineFragment.class
			.getSimpleName();

	public static MentionTimelineFragment newInstance() {
		return newInstance(false);
	}

	public static MentionTimelineFragment newInstance(boolean refresh) {
		Bundle args = new Bundle();
		args.putBoolean("refresh", refresh);
		MentionTimelineFragment fragment = new MentionTimelineFragment();
		fragment.setArguments(args);
		if (App.DEBUG) {
			Log.d(TAG, "newInstance() " + fragment);
		}
		return fragment;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	protected int getType() {
		return StatusModel.TYPE_MENTIONS;
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return DataController.getTimelineCursorLoader(getActivity(),
				StatusModel.TYPE_MENTIONS);
	}

	@Override
	protected void doFetch(boolean doGetMore) {
		if (App.DEBUG) {
			Log.d(TAG, "doFetch() doGetMore=" + doGetMore);
		}
		final ResultHandler handler = new ResultHandler(this);
		final Cursor cursor = getCursor();
		Paging p = new Paging();
		if (doGetMore) {
			p.maxId = Utils.getMaxId(cursor);
		} else {
			p.sinceId = Utils.getSinceId(cursor);
		}
		if (App.DEBUG) {
			Log.d(TAG, "doFetch() doGetMore=" + doGetMore + " Paging=" + p);
		}
		FanFouService.getTimeline(getActivity(), StatusModel.TYPE_MENTIONS,
				handler, p);
	}

	@Override
	public String getTitle() {
		return "提及";
	}

}