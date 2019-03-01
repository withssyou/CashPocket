package com.ylbl.cashpocket.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.ylbl.cashpocket.R;
import com.ylbl.cashpocket.base.BaseRecycleViewHolder;
import com.ylbl.cashpocket.base.BaseRecyclerAdapter;
import com.ylbl.cashpocket.base.BaseRecyclerArrayAdapter;
import com.ylbl.cashpocket.bean.CommentInfo;
import com.ylbl.cashpocket.bean.ConfigInfo;
import com.ylbl.cashpocket.bean.MemberInfo;
import com.ylbl.cashpocket.utils.ImageCacheUtils;
import com.ylbl.cashpocket.utils.MemoryCache;
import com.ylbl.cashpocket.utils.SpUtils;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends BaseRecyclerArrayAdapter<CommentInfo> {
    Context context ;

    public CommentAdapter(Context context, List<CommentInfo> objects) {
        super(context, objects);
        this.context = context;
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment , parent ,false);
        return new ViewHolder(view);
    }

    class ViewHolder extends BaseRecycleViewHolder<CommentInfo>{

        @BindView(R.id.item_kehu_content)
        TextView content;
        @BindView(R.id.item_kefu_content)
        TextView content_kefu;
        @BindView(R.id.item_kehu_time)
        TextView time;
        @BindView(R.id.item_kefu_time)
        TextView time_kefu;
        @BindView(R.id.item_icon)
        TextView name;
        @BindView(R.id.item_kefu_rl)
        RelativeLayout rl;
        @BindView(R.id.item_kehu_icon)
        CircleImageView icon_kehu;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(CommentInfo data, int position) {
            super.setData(data, position);
            MemberInfo memberInfo = SpUtils.getMemberInfo(context);
            if (data.getState() == 1){ //未回复
                content.setText(data.getContent());
                time.setText(data.getCreateTime());
                name.setText(memberInfo.getName());
                if (ImageCacheUtils.getInstance().getBitmap("headImg")!= null){
                    icon_kehu.setImageBitmap(ImageCacheUtils.getInstance().getBitmap("headImg"));
                }else {
                    icon_kehu.setImageResource(R.mipmap.icon_default);
                }
                rl.setVisibility(View.GONE);
            }else {
                name.setText(memberInfo.getName());
                content.setText(data.getContent());
                time.setText(data.getCreateTime());
                content_kefu.setText(data.getReplyContent());
                time_kefu.setText(data.getReplyTime());
                if (ImageCacheUtils.getInstance().getBitmap("headImg")!= null){
                    icon_kehu.setImageBitmap(ImageCacheUtils.getInstance().getBitmap("headImg"));
                }else {
                    icon_kehu.setImageResource(R.mipmap.icon_default);
                }
            }
        }
    }

}
