package techtown.org.websourcecode;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

/*
web page URL의 source code를 가져옴, 그리고 HTTP, HTTPS인지 구분하는 인자도 포함함
 */

public class SourceCodeLoader extends AsyncTaskLoader<String> {

    private String mQueryString; // URL 받아와서 query문을 가져옴
    private String mTransferProtocol; // HTTP, HTTPS 구분 위한 프로토콜
    Context mContext;


    public SourceCodeLoader(@NonNull Context context, String queryString, String transferProtocol) {
        super(context);
        mContext = context;
        mQueryString = queryString;
        mTransferProtocol = transferProtocol;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        // 네트워크 연결하고 query 작업을 처리함
        return NetworkUtils.getSourceCode(mContext,mQueryString,mTransferProtocol);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
