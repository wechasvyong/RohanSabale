/**
 * Copyright 2013 Joan Zapata
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.chad.library.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the convert() implementation.<br/>
 * Using the provided BaseViewHolder, your code is minimalist.
 *
 * @param <T> The type of the items in the list.
 */
public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected static final String TAG = BaseQuickAdapter.class.getSimpleName();

    protected final Context context;

    protected final int layoutResId;

    protected final List<T> data;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    public interface OnRecyclerViewItemClickListener {
        public void onItemClick(View view, int position);
    }

    /**
     * Create a QuickAdapter.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public BaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<T>(data);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);

    }

    public void add(int position, T item) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(
                layoutResId, parent, false);
        return new BaseViewHolder(context, item);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        convert(baseViewHolder, data.get(position));
        if (onRecyclerViewItemClickListener != null) {
            baseViewHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onRecyclerViewItemClickListener.onItemClick(v, position);
                }
            });
        }
    }
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(BaseViewHolder helper, T item);


    @Override
    public long getItemId(int position) {
        return position;
    }








}
