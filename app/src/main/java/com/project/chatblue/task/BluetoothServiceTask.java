package com.project.chatblue.task;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;


import com.project.chatblue.R;
import com.project.chatblue.business.ChatBusinessLogic;
import com.project.chatblue.business.IBusinessLogic;
import com.project.chatblue.communication.BluetoothService;
import com.project.chatblue.util.ToastUtil;

/**
 * 
 * @author Marcus Pimenta
 * @email mvinicius.pimenta@gmail.com
 * 01/11/2012 13:18:08 
 */
public class BluetoothServiceTask extends AsyncTask<BluetoothAdapter, Void, BluetoothSocket>{

	private Context context;
	private ProgressDialog progressDialog;	
	
	private ToastUtil toastUtil;
	private BluetoothService bluetoothService;
	private IBusinessLogic.OnConnectionBluetoothListener onBluetoothListener;

	public BluetoothServiceTask(Context context, ChatBusinessLogic onBluetoothListener){
		this.context = context;
		this.onBluetoothListener = onBluetoothListener;
	
		toastUtil = new ToastUtil(context);
		bluetoothService = new BluetoothService();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, 
											 context.getString(R.string.waiting),
											 context.getString(R.string.msg_waiting_connection));
	}
	
	@Override
	protected BluetoothSocket doInBackground(BluetoothAdapter... bluetoothAdapter) {
		return bluetoothService.startServer(bluetoothAdapter[0]);
	}
	
	@Override
	protected void onPostExecute(BluetoothSocket bluetoothSocket) {
		super.onPostExecute(bluetoothSocket);
		
		closeDialog();
		
		if(bluetoothSocket != null){
			onBluetoothListener.onConnectionBluetooth(bluetoothSocket);
		}else{
			toastUtil.showToast(context.getString(R.string.connection_failed));
		}
	}
	
	private void closeDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}


}	