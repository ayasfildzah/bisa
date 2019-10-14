package com.mkp.bisa;

public class konfigurasi {
    public static final String URL_GET_ALL = "http://172.16.8.198:3000/api/v1/brands.json";
    public static final String URL_ADD = "http://172.16.8.198:3000/api/v1/brands.json";


    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "name";
    public static final String TAG_CREAT ="created_at";

    public static final String QR_ID = "Qr_id";


    public class params{


        public static final String TAG_EMAIL = "email";
        public static final String TAG_PASS ="password";

        public class ServiceType{
            public final String LOGIN = "http://172.16.8.198:3000/api/v1/sessions";

        }


        }

}
