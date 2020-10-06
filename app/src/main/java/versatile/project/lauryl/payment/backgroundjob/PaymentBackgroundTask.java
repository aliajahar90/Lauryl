package versatile.project.lauryl.payment.backgroundjob;

import versatile.project.lauryl.base.asyncjob.BaseTask;

public class PaymentBackgroundTask extends BaseTask {

    private final iOnDataFetched listener;//listener in fragment that shows and hides ProgressBar

    public PaymentBackgroundTask(iOnDataFetched onDataFetchedListener) {
        this.listener = onDataFetchedListener;
    }

    @Override
    public void setUiForLoading() {
        listener.notifyPreProcess();
    }

    @Override
    public void setDataAfterLoading(Object result) {
       listener.onBackgroundResult(result);
       listener.notifyPostProcess();
    }

    @Override
    public Object call() throws Exception {

        Object result = null;
        result = listener.notifyDoBackground();
        return result;

    }

    public interface iOnDataFetched {
        void notifyPreProcess();
        void onBackgroundResult(Object result);
        void notifyPostProcess();
        Object notifyDoBackground();
    }
}