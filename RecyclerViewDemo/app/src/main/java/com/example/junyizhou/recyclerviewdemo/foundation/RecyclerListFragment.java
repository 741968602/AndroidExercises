package com.example.junyizhou.recyclerviewdemo.foundation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.junyizhou.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JunyiZhou on 2015/12/31.
 * <p/>
 * ����RecyclerView��Fragment����
 */
public abstract class RecyclerListFragment extends Fragment {
    private static String ROOT_TAG = "ROOT_TAG";//ViewHolder��rootView��tag

    private RecyclerView mRecyclerView;//RecyclerViewʵ��
    private RecyclerListAdapter mRecyclerAdapter;//RecyclerView������ʵ��

    private IOnItemClickListener mIOnItemClickListener;//item����¼�����ʵ��
    private IOnItemLongClickListener mIOnItemLongClickListener;//item�����¼�����ʵ��
    private OnItemTouchListener mOnItemTouchListener;//item�����¼�����ʵ��

    private List dataList = new ArrayList();//RecyclerView����

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.list);

        if (mRecyclerView != null) {
            RecyclerView.LayoutManager lm = createLayoutManager();//RecyclerView��LayoutManager����Ҫ�ǿ�����ʾ�Ĳ���ģʽ�����ԡ�����ȴ�
            if (lm != null) {
                mRecyclerView.setLayoutManager(lm);
            }

            RecyclerView.ItemDecoration id = createItemDecoration();//RecyclerView�ķָ���
            if (id != null) {
                mRecyclerView.addItemDecoration(id);
            }

            RecyclerView.ItemAnimator ia = createItemAnimator();//RecyclerView�Ķ���
            if (ia != null) {
                mRecyclerView.setItemAnimator(ia);
            }

            mRecyclerAdapter = new RecyclerListAdapter();
            mRecyclerView.setAdapter(mRecyclerAdapter);//����������
        }
    }

    /**
     * ��ȡ��ǰRecyclerViewʵ��
     *
     * @return ��ǰRecyclerViewʵ��
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * ��ȡ��ǰRecyclerView��adapter
     *
     * @return ��ǰRecyclerView��adapter
     */
    public RecyclerListAdapter getAdapter() {
        return mRecyclerAdapter;
    }

    /**
     * ��ȡViewHolder
     * ���󷽷�����type����Ϊ1��ʱ�򱻵��ã���Ҫʵ��
     *
     * @param parent ViewHolder�ĸ�����
     * @return ViewHolderʵ��
     */
    public abstract ViewHolder getViewHolder(ViewGroup parent);

    /**
     * ����RecyclerView��LayoutManager
     * ���󷽷����������ʵ�֣������Է���null������ᱨ��
     *
     * @return RecyclerView��LayoutManagerʵ��
     */
    public abstract RecyclerView.LayoutManager createLayoutManager();

    /**
     * ����RecyclerView�ķָ���
     * ���󷽷���������Է���null
     *
     * @return RecyclerView�ķָ���ʵ��
     */
    public abstract RecyclerView.ItemDecoration createItemDecoration();

    /**
     * ����RecyclerView�Ķ���
     * ���󷽷���������Է���null
     *
     * @return RecyclerView�Ķ���
     */
    public abstract RecyclerView.ItemAnimator createItemAnimator();

    /**
     * ����item����¼�����
     *
     * @param listener item����¼�����
     */
    public void setOnItemClickListener(IOnItemClickListener listener) {
        mIOnItemClickListener = listener;
    }

    /**
     * ����item�����¼�����
     *
     * @param listener item�����¼�����
     */
    public void setOnItemLongClickListener(IOnItemLongClickListener listener) {
        mIOnItemLongClickListener = listener;
    }

    /**
     * ����item�����¼�����
     *
     * @param listener item�����¼�����
     */
    public void setOnItemTouchListener(OnItemTouchListener listener) {
        mOnItemTouchListener = listener;
    }

    /**
     * ����type������ȡ��Ӧ��ViewHolder
     *
     * @param parent ViewHolder���ڵĸ�����
     * @param type   ViewHolder����
     * @return ViewHolderʵ��
     */
    public ViewHolder getViewHolder(ViewGroup parent, int type) {
        return null;
    }

    /**
     * ��ȡitem��������
     *
     * @return item��������
     */
    public int getItemViewTypeCount() {
        return 1;
    }

    /**
     * ����position��ȡ��Ӧ��type
     *
     * @param position item��RecyclerView�е�λ��
     * @return type
     */
    public int getItemViewTypeInPosition(int position) {
        return 0;
    }

    /**
     * ��ȡ����list
     *
     * @return ����list
     */
    public List getDataList() {
        return dataList;
    }

    /**
     * ��������list
     *
     * @param dataList ����list
     */
    public void setDataList(List dataList) {
        if (dataList == null) {
            dataList = new ArrayList();
        }
        this.dataList = dataList;
    }

    /**
     * �������
     *
     * @param position ������ݵ�λ��
     * @param data     ����
     * @param <T>      ��������
     */
    public <T> void addData(int position, T data) {
        if (mRecyclerAdapter == null || dataList == null || dataList.size() < position) {
            return;
        }
        dataList.add(position, data);
        mRecyclerAdapter.notifyItemInserted(position);
        mRecyclerAdapter.notifyItemRangeChanged(position, dataList.size());
    }

    /**
     * ɾ������
     *
     * @param position ɾ�����ݵ�λ��
     */
    public void removeData(int position) {
        if (mRecyclerAdapter == null || dataList == null || dataList.size() < position) {
            return;
        }
        dataList.remove(position);
        mRecyclerAdapter.notifyItemRemoved(position);
        mRecyclerAdapter.notifyItemRangeChanged(position, dataList.size());
    }

    /**
     * RecyclerView��������װ����
     */
    public class RecyclerListAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int count = getItemViewTypeCount();//item type����

            if (count == 1) {//���item type����Ϊ1������ò���Ҫ����type��getViewHolder����
                return getViewHolder(parent);
            } else {//���item type������Ϊ1���������Ҫ����type��getViewHolder����
                return getViewHolder(parent, viewType);
            }
        }

        @Override
        public int getItemViewType(int position) {
            return getItemViewTypeInPosition(position);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.bind(getDataList().get(position), position);//����Ӧposition�����ݺ�position����ViewHolder�е�bind��������bind�����д������ݵ���װ
        }
    }

    /**
     * RecyclerView ��Ӧ��ViewHolder
     *
     * @param <T> ���ݷ���
     */
    public abstract class ViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private final View mRootView;

        /**
         * ��ȡRootView
         *
         * @param <VT> RootView��ViewHolderʵ������
         * @return RootView��ViewHolderʵ��
         */
        @SuppressWarnings("unchecked")
        public <VT extends View> VT getRootView() {
            return (VT) mRootView;
        }

        public ViewHolder(@NonNull View view) {
            super(view);
            mRootView = view;
            mRootView.setTag(ROOT_TAG);
            mRootView.setOnClickListener(this);
            mRootView.setOnLongClickListener(this);
        }

        /**
         * ������viewԪ��bind����
         *
         * @param item     ����
         * @param position λ��
         */
        public abstract void bind(T item, int position);

        @Override
        public void onClick(View v) {
            //item����¼��������ã�ͨ��RootView��tag��������
            if (mIOnItemClickListener != null && v.getTag() != null && v.getTag().equals(ROOT_TAG)) {
                mIOnItemClickListener.onItemClick(v, getPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            //item�����¼��������ã�ͨ��RootView��tag��������
            if (mIOnItemLongClickListener != null && v.getTag() != null && v.getTag().equals(ROOT_TAG)) {
                mIOnItemLongClickListener.onItemLongClick(v, getPosition());
            }
            return true;
        }
    }
}
