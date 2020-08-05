package me.tatarka.bindingcollectionadapter2;

import androidx.databinding.BindingAdapter;
import androidx.paging.AsyncPagingDataDiffer;
import androidx.paging.PagedList;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.RecyclerView;
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffPagedObservableList;
import me.tatarka.bindingcollectionadapter2.collections.AsyncDiffPagedObservableListV3;
import me.tatarka.bindingcollectionadapter2.paging.R;

/**
 * @see {@link BindingCollectionAdapters}
 */
public class PagedBindingRecyclerViewAdapters {
    // RecyclerView
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemBinding", "items", "adapter", "itemIds", "viewHolder", "diffConfig"}, requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView,
                                      ItemBinding<? super T> itemBinding,
                                      PagedList<T> items,
                                      BindingRecyclerViewAdapter<T> adapter,
                                      BindingRecyclerViewAdapter.ItemIds<? super T> itemIds,
                                      BindingRecyclerViewAdapter.ViewHolderFactory viewHolderFactory,
                                      AsyncDifferConfig<T> diffConfig) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("itemBinding must not be null");
        }
        BindingRecyclerViewAdapter oldAdapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindingRecyclerViewAdapter<>();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItemBinding(itemBinding);

        if (diffConfig != null && items != null) {
            AsyncDiffPagedObservableList<T> list = (AsyncDiffPagedObservableList<T>) recyclerView.getTag(R.id.bindingcollectiondapter_list_id);
            if (list == null) {
                list = new AsyncDiffPagedObservableList<>(diffConfig);
                recyclerView.setTag(R.id.bindingcollectiondapter_list_id, list);
                adapter.setItems(list);
            }
            list.update(items);
        } else {
            adapter.setItems(items);
        }

        adapter.setItemIds(itemIds);
        adapter.setViewHolderFactory(viewHolderFactory);

        if (oldAdapter != adapter) {
            recyclerView.setAdapter(adapter);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemBinding", "items", "adapter", "itemIds", "viewHolder", "diffConfig"}, requireAll = false)
    public static <T> void setAdapter(RecyclerView recyclerView,
                                      ItemBinding<? super T> itemBinding,
                                      PagingData<T> items,
                                      BindingRecyclerViewAdapter<T> adapter,
                                      BindingRecyclerViewAdapter.ItemIds<? super T> itemIds,
                                      BindingRecyclerViewAdapter.ViewHolderFactory viewHolderFactory,
                                      AsyncDifferConfig<T> diffConfig) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("itemBinding must not be null");
        }
        BindingRecyclerViewAdapter oldAdapter = (BindingRecyclerViewAdapter) recyclerView.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindingRecyclerViewAdapter<>();
            } else {
                adapter = oldAdapter;
            }
        }
        adapter.setItemBinding(itemBinding);

        if (diffConfig != null && items != null) {
            AsyncDiffPagedObservableListV3<T> list = (AsyncDiffPagedObservableListV3<T>) recyclerView.getTag(R.id.bindingcollectiondapter_list_id);
            if (list == null) {
                list = new AsyncDiffPagedObservableListV3<>(diffConfig);
                recyclerView.setTag(R.id.bindingcollectiondapter_list_id, list);
                adapter.setItems(list);
            }
            list.update( Utils.findLifecycleOwner(recyclerView).getLifecycle(), items);
        } else {
//            adapter.setItems(list);
            throw new IllegalArgumentException("diff required for PagedData");
        }

        adapter.setItemIds(itemIds);
        adapter.setViewHolderFactory(viewHolderFactory);

        if (oldAdapter != adapter) {
            recyclerView.setAdapter(adapter);
        }
    }
}
