{
    setContentView(R.layout.activity_advanced);

    ((Button) findViewById(R.id.sslKeyBut)).setOnClickListener(new OnClickListener() {
        // Do something
    });

    ((CheckBox) findViewById(R.id.sslCheckBox)).setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(((CheckBox)v).isChecked()) {
                ((Button)findViewById(R.id.sslKeyBut)).setClickable(boolean);
            } else {
                ((Button)findViewById(R.id.sslKeyBut)).setClickable(boolean);
            }
        }
    });

    ((Button)findViewById(R.id.sslKeyBut)).setClickable(boolean);
}