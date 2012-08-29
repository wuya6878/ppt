package com.android.jialin.search;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jialin.R;
import com.android.jialin.sync.City;
import com.android.jialin.util.AsyncImageLoader;
import com.android.jialin.util.AsyncImageLoader.ImageCallback;
import com.android.jialin.util.Pager;
import com.android.jialin.util.StringUtil;

public class CityAdapter extends BaseAdapter {

	private static final String TAG = CityAdapter.class.getName();

	AsyncImageLoader asyncImageLoader = new AsyncImageLoader();

	public class ProductViewHolder {
		ImageView imgHead;
		TextView info;
		String id;

		public TextView getInfo() {
			return info;
		}

		public void setInfo(TextView info) {
			this.info = info;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public ImageView getImgHead() {
			return imgHead;
		}

		public void setImgHead(ImageView imgHead) {
			this.imgHead = imgHead;
		}

	}

	private Pager pager;
	private LayoutInflater minflater;

	private SearchActivity context;

	public CityAdapter(SearchActivity con, Pager pager) {
		this.pager = pager;
		minflater = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	public int getCount() {
		if (pager != null && pager.getList() != null) {
			return pager.getList().size();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	public Object getItem(int i) {
		if (pager != null && pager.getList() != null) {
			return pager.getList().get(i);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	public long getItemId(int i) {
		return i;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	public View getView(int i, View view, ViewGroup viewgroup) {
		ProductViewHolder holder;
		try {
			if (view == null) {
				view = this.minflater.inflate(R.layout.city, null);
				holder = new ProductViewHolder();
				holder.imgHead = (ImageView) view.findViewById(R.id.imghead);
				holder.info = (TextView) view.findViewById(R.id.info);
				view.setTag(holder);
			} else {
				holder = (ProductViewHolder) view.getTag();
			}
			final City product = (City) getItem(i);
			if (product != null) {
				if ("".equals(StringUtil.nvl(product.getImageUrl()))) {
					holder.imgHead.setImageResource(R.drawable.none);
				} else {
					Drawable cachedImage = asyncImageLoader.loadDrawable(
							product.getImageUrl(), holder.imgHead,
							new ImageCallback() {
								public void imageLoaded(Drawable imageDrawable,
										ImageView imageView, String imageUrl) {
									imageView.setImageDrawable(imageDrawable);
								}
							});
					if (cachedImage == null) {
						holder.imgHead.setImageResource(R.drawable.none);
					} else {
						holder.imgHead.setImageDrawable(cachedImage);
					}
				}
				String title = "CityName:<b>" + product.getName() +"</b><br> Population count:" + product.getPopulation()+"<br> Zip code:"+ product.getPostcode()+"";
				holder.info.setText(Html.fromHtml(title));
				holder.id = "" + product.getId();
			} else {
				holder.imgHead.setImageResource(R.drawable.none);
			}
		} catch (Exception ex) {
			Log.i(TAG, ex.toString());
		}
		return view;
	}

	/**
	 * update pager
	 * 
	 * @param pager
	 */
	public void refreshPager(Pager pager) {
		this.pager = pager;
	}

}
