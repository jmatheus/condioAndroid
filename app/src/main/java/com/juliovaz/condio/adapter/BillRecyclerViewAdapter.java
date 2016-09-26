package com.juliovaz.condio.adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.juliovaz.condio.Manifest;
import com.juliovaz.condio.R;
import com.juliovaz.condio.activity.MainActivity;
import com.juliovaz.condio.model.Bill;
import com.juliovaz.condio.model.Reservation;
import com.juliovaz.condio.util.FileDownloader;

import java.io.File;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Reservation} and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class BillRecyclerViewAdapter extends RecyclerView.Adapter<BillRecyclerViewAdapter.ViewHolder> {

    private static final String LOG_TAG = "BillRecyclerView";
    private final List<Bill> mValues;
    private final Context context;
    private String condioPdfBillUrl = "https://s3-sa-east-1.amazonaws.com/condio/images/static/boleto_condio.pdf";
    private DownloadManager downloadManager;

    public BillRecyclerViewAdapter(Context context, List<Bill> items) {
        this.context = context;
        mValues = items;
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_bill, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
        holder.mBillMonth.setText(mValues.get(position).getDueMonth());
        holder.mPaymentStatus.setText(mValues.get(position).getStatus());
        if (mValues.get(position).getStatus().equals("Pago")) {
            holder.btnDownload.setVisibility(View.INVISIBLE);
            holder.ivDoneBill.setVisibility(View.VISIBLE);
        } else {
            holder.btnDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(condioPdfBillUrl));
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "condio-boleto.pdf");
                    request.setDescription("Condio Downloads").setTitle("Baixando Boleto");
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); // to notify when download is complete
                    request.allowScanningByMediaScanner();// if you want to be available from media players
                    downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mBillMonth;
        public final TextView mPaymentStatus;
        public final Button btnDownload;
        public final ImageView ivDoneBill;
        public Bill mItem;

        public ViewHolder(View view) {
            super(view);
            btnDownload = (Button) view.findViewById(R.id.btn_bill_month_pdf);
            mIdView = (TextView) view.findViewById(R.id.txEventDate);
            mBillMonth = (TextView) view.findViewById(R.id.tx_bill_month);
            mPaymentStatus = (TextView) view.findViewById(R.id.tx_bill_month_status);
            ivDoneBill = (ImageView) view.findViewById(R.id.iv_done_bill);
        }
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            if (isExternalStorageWritable()) {

                File file = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), fileName);

                if (!file.mkdirs()) {
                    Log.e(LOG_TAG, "Directory not created");
                }

                FileDownloader.downloadFile(fileUrl, file);
            }
            return null;
        }

        public boolean isExternalStorageWritable() {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                return true;
            }
            return false;
        }
    }

}
