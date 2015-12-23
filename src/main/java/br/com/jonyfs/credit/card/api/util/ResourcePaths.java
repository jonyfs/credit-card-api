package br.com.jonyfs.credit.card.api.util;

public class ResourcePaths {
    public static final String API         = "api";
    public static final String ID          = "/{id}";
    public static final String V1          = "/v1";
    public static final String V2          = "/v2";
    public static final String V3          = "/v3";
    public static final String ROOT_API    = "/" + API;
    public static final String ROOT_API_V1 = ROOT_API + V1;
    public static final String ROOT_API_V2 = ROOT_API + V2;
    public static final String ROOT_API_V3 = ROOT_API + V3;

    public class Version {
        public static final String NAME = "/version";

        public static final String ROOT = ROOT_API + NAME;
    }

    public class Payment {
        public static final String NAME = "/payments";

        public class V1 {
            public static final String ROOT = ROOT_API_V1 + NAME;
            public static final String GET  = ROOT_API_V1 + NAME + ID;
        }

        public class V2 {
            public static final String ROOT = ROOT_API_V2 + NAME;
            public static final String GET  = ROOT_API_V2 + NAME + ID;
        }

        public class V3 {
            public static final String ROOT = ROOT_API_V3 + NAME;
            public static final String GET  = ROOT_API_V3 + NAME + ID;
        }

    }
}
