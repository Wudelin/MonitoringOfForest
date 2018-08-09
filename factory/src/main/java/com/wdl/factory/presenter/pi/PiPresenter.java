package com.wdl.factory.presenter.pi;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.wdl.common.app.Application;
import com.wdl.common.widget.recycler.RecyclerAdapter;
import com.wdl.factory.R;
import com.wdl.factory.data.DataSource;
import com.wdl.factory.data.data.helper.PiHelper;
import com.wdl.factory.data.data.pi.PiDataSource;
import com.wdl.factory.data.data.pi.PiRepository;
import com.wdl.factory.model.card.Pi;
import com.wdl.factory.model.card.User;
import com.wdl.factory.model.db.PiDb;
import com.wdl.factory.persistence.Account;
import com.wdl.factory.presenter.BaseRecyclerPresenter;
import com.wdl.factory.presenter.BaseSourcePresenter;
import com.wdl.factory.utils.DiffUiDataCallback;
import com.wdl.utils.CollectionUtil;
import com.wdl.utils.LogUtils;

import java.util.Collection;
import java.util.List;

/**
 * 项目名：  MonitoringOfForest
 * 包名：    com.wdl.factory.presenter.pi
 * 创建者：   wdl
 * 创建时间： 2018/8/6 11:31
 * 描述：    PI P层
 */
@SuppressWarnings("unused")
public class PiPresenter extends BaseSourcePresenter<PiDb,PiDb,PiDataSource,PiContract.View>
        implements PiContract.Presenter{
    public PiPresenter(PiContract.View view) {
        super(view,new PiRepository());
    }

    @Override
    public void start() {
        super.start();
        final PiContract.View view = getView();
        LogUtils.e(Account.getUserId() + "");
        if (Account.getUserId() == -1) {
            view.showError(R.string.data_account_error_un_login);
            return;
        }
        User user = new User();
        user.setuId(Account.getUserId());
        //网络查询
        PiHelper.select(user);
    }


    /**
     * 最终的数据都在此处
     *
     * @param data T
     */
    @Override
    public void onLoaded(List<PiDb> data) {
        final PiContract.View view = getView();
        if (view==null)return;
        RecyclerAdapter<PiDb> adapter = view.getRecyclerAdapter();
        //获取老数据
        List<PiDb> oldList = adapter.getItems();
        //进行差异化比较
        DiffUtil.Callback callback = new DiffUiDataCallback<>(oldList,data);
        //差异结果集
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        //调用基类刷新
        refreshData(result,data);

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
