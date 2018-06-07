{
    NetworkConnectionIntentReceiver networkConnectionMonitor;
    BackgroundDataPreferenceReceiver backgroundDataPreferenceMonitor;
    if (networkConnectionMonitor == null) {
        networkConnectionMonitor = new NetworkConnectionIntentReceiver();
        registerReceiver(networkConnectionMonitor, new IntentFilter(
            ConnectivityManager.CONNECTIVITY_ACTION));
    }

    if (Build.VERSION.SDK_INT < 0  {
    ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (backgroundDataPreferenceMonitor == null) {
            backgroundDataPreferenceMonitor = new BackgroundDataPreferenceReceiver();
            registerReceiver(
                backgroundDataPreferenceMonitor,
                new IntentFilter(
                    ConnectivityManager.ACTION_BACKGROUND_DATA_SETTING_CHANGED));
        }
        // Do something with cm

    }
}