package versatile.project.lauryl.utils;

public class AllConstants {
    public static class Payment {
        //payment constants
        public static final String PaymentData = "paymentdata";
        public static final String CARD = "card";
        public static final String DEBIT_CARD = "debit_card";
        public static final String CREDIT_CARD = "credit_card";
        public static final String NET_BANKING = "netbanking";
        public static final String UPI = "upi";
        public static final int PaymentViewOptions = 444;
        public static final int PaymentViewCheckout = 555;


        public static class HotBanks {
            public static final String SBIN = "SBIN";
            public static final String HDFC = "HDFC";
            public static final String BARB_R = "BARB_R";
            public static final String ICIC = "ICIC";
            public static final String CNRB = "CNRB";
            public static final String KKBK = "KKBK";
        }

        public static class Errors {
            public static final String ERROR_VPA_VALIDATION = "Unable to validate vpa /n Please try Again";
            public static final String ERROR_LOAD_PAYMENT = "Unable to load payment";
            public static final String ERROR_VPA_MSG = "Please enter valid upi id";
        }
    }
    public static class PickUp{
        public static final String PickUpData = "pickupata";
    }
    public  static  class Profile{
        public static final String PROFILE_DATA = "profiledata";
        public static final String MOBILE_NUMBER = "Mobile_Number";
    }
    public  static  class Orders{
        public static final String orderData = "orderData";
        public static final String initTabSelection = "initTabSelection";
        public static final String ORDER_ITEM_ERROR_EMPTY = "No Order item found";
        public static final String ORDER_ITEM_ERROR_API = "Error fetching order item";
        public static final String MARKET_PLACE_NAME = "Lauryl-Android";
        public static String vAccountId="3";
        public static String pickupDate="pickupDate";
        public static String pickupTime="pickupTime";

        public class OrderStage{
            public static final String Awaiting_Pickup = "Awaiting Pickup";
            public static final String Awaiting_Delivery = "Awaiting Delivery";
            public static final String Completed_Delivery = "Completed Delivery";
        }
        public static class Errors {
            public static final String ERROR_API_FAILED= "Something went wrong!";

        }
    }

    public static class Services{
        public static class Errors {
            public static final String ERROR_LIST_EMPTY = "No data found";
            public static final String ERROR_API_FAILED= "Something went wrong!";

        }
    }


}
