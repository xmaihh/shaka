package com.wx.base.app.ui.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.wx.base.project.model.action.ItemChagneAction;
import com.wx.base.project.model.blockchain.AdapterBlock;
import com.wx.base.project.model.blockchain.Chain;
import com.wx.base.project.module.predux.Action;
import com.wx.base.project.module.predux.BaseReducer;
import com.wx.base.project.module.predux.Reducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 16-11-30.
 */

public class FractalAdapter extends BaseAdapter{

    protected final String TAG = "wx.adapter.fractal";
    protected List<BaseRecyclerAdapter> adapters = new ArrayList<>();

    List<AdapterBlock> blocks = new ArrayList<>();
    List<Chain> chains = new ArrayList<>();

    public FractalAdapter(Context context){
        super(context);
        getStore().setReducer(reducer);
    }

    public void addAdapter(BaseRecyclerAdapter adapter){
        this.adapters.add(adapter);
        //添加Reducer，处理子adapter的Action
        adapter.getStore().addReducer(getStore().getReducer());
        transform.updateModel();
    }

    public void removeAdapter(BaseRecyclerAdapter adapter){
        this.adapters.remove(adapter);
        //移除
        adapter.getStore().removeReducer(getStore().getReducer());
        transform.updateModel();
    }

    @Override
    public int getItemCount() {
        return transform.getItemCount();
    }

    public int getItemViewType(int position) {
        return transform.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseAdapter adapter = transform.getRetAdapter(viewType);
        if(adapter != null) {
            return adapter.onCreateViewHolder(parent, viewType);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseAdapter adapter = transform.getRetAdapterByPos(position);
        adapter.onBindViewHolder(holder,transform.getRetPos(position));
    }


    protected Reducer reducer = new BaseReducer(store) {

        protected final String TAG = "wx.reducer.fractal";
        @Override
        public boolean onReduce(Action action) {
            Log.i(TAG, "onreduce "+action.getClass().getSimpleName());
            if(action instanceof ItemChagneAction){
                //ItemChagneAction itemChagneAction = (ItemChagneAction)action;
                //itemChagneAction.getAdapterType();
                //int pos = transform.getRetAdapter(itemChagneAction.getAdapterType());
                //int pos = transform.getAbsPos(itemChagneAction.getAdapterType(),0);
                //notifyItemInserted(pos+itemChagneAction.getPositionStart());
                transform.updateModel();
                notifyDataSetChanged();

                return true;
            }
            return false;
        }
    };


    ITransform transform =  new ITransform() {
        @Override
        public void updateModel() {
            //update block chain
            blocks.clear();
            chains.clear();
            int type = 0;
            for(BaseRecyclerAdapter adapter:adapters){

                AdapterBlock block = new AdapterBlock();
                block.setAdapter(adapter);
                block.setType(type);
                adapter.setType(type);
                blocks.add(block);
                for(int i=0;i<adapter.getItemCount();i++){
                    Chain chain = new Chain();
                    chain.setBlock(block);
                    chain.setId(i);
                    chain.setPos(i);
                    chains.add(chain);
                }
                type++;
            }
        }

        @Override
        public int getItemCount() {

            //Log.i(TAG, "getItemCount");
            //int count = 0;
            //for(BaseRecyclerAdapter adapter:adapters){
            //    count += adapter.getItemCount();
            //}
            //return count;
            return chains.size();
        }

        @Override
        public int getItemViewType(int position) {

            //Log.i(TAG, "getItemViewType"+position);
            //return getRetAdapterByPos(position).getItemViewType(getRetPos(position));
            Chain chain = chains.get(position);
            AdapterBlock block = (AdapterBlock)chain.getBlock();
            return block.getType()*1000 + block.getAdapter().getItemViewType(chain.getPos());
        }

        @Override
        public int getRetPos(int abspos) {

            //Log.i(TAG, "getRetPos"+abspos);
            Chain chain = chains.get(abspos);
            return chain.getPos();
        }

        @Override
        public int getAbsPos(int type, int retpos) {

            //Log.i(TAG, "getAbsPos"+retpos);
            int blocktype = type;
            //要有 type,通过type获取 AdapterBlock
            AdapterBlock xblock = null;
            for(AdapterBlock block:blocks){
                if(block.getType() == blocktype){
                    xblock = block;
                    break;
                }
            }

            for(Chain chain:chains){
                if(chain.getBlock().equals(xblock)
                        && chain.getPos() == retpos){
                    //
                    return chain.getId();
                }
            }

            return 0;
        }

        @Override
        public BaseAdapter getRetAdapter(int viewType) {

            //Log.i(TAG, "getRetAdapter"+viewType);
            int blocktype = viewType/1000;

            //Log.i(TAG, "getRetAdapter blocktype "+blocktype);
            for(AdapterBlock block:blocks){
                if(block.getType() == blocktype){
                    return block.getAdapter();
                }
            }

            //Log.i(TAG, "getRetAdapter null ");
            return null;
        }

        @Override
        public BaseAdapter getRetAdapterByPos(int position){
            AdapterBlock block = (AdapterBlock) chains.get(position).getBlock();
            return block.getAdapter();
        }
    };

    interface ITransform{
        void updateModel();
        int getItemCount();
        int getItemViewType(int position);
        int getRetPos(int abspos);
        int getAbsPos(int type, int retpos);
        BaseAdapter getRetAdapter(int viewType);
        BaseAdapter getRetAdapterByPos(int position);
    }


}
