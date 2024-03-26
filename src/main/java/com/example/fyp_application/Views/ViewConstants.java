package com.example.fyp_application.Views;

public class ViewConstants {


    //ViewConstants class to store all the paths to the FXML(Views) files to complete the MVC architecture



    //Client views
    public static final String CLIENT_HOME_PAGE_VIEW = "/FXML/ClientView/DashboardView/UserHomePage.fxml";
    public static final String CLIENT_DASHBOARD_VIEW = "/FXML/ClientView/DashboardView/ClientDashboard.fxml";
    public static final String CLIENT_SIDEBAR_MENU = "/FXML/ClientView/Navigation/ClientSideBar.fxml";
    public static final String CLIENT_EDIT_PROFILE_VIEW = "/FXML/ClientView/UserProfileView/EditUserProfileView.fxml";


    public static final String CLIENT_EDIT_PROFILE_POP_UP = "/FXML/ClientView/UserProfileView/EditUserProfilePopUpView.fxml";


    public static final String CLIENT_TICKET_DASHBOARD_VIEW = "/FXML/ClientView/RequestView/ClientRequestsDashboardView.fxml";

    public static final String CLIENT_RAISE_TICKET_POP_UP = "/FXML/ClientView/RequestView/ClientRaiseRequestView.fxml";

    public static final String CLIENT_RESPONSE_TICKET_POP_UP = "/FXML/ClientView/RequestView/ClientResponsePopUp.fxml";

    public static final String CLIENT_VIEW_TICKET_POP_UP = "/FXML/ClientView/RequestView/ClientSelfTicketView.fxml";





    //Admin views
    //public static final String ADMIN_DASHBOARD = "/FXML/AdminView/ModifiedAdminDashboard.fxml"; //Actual Admin Dashboard


    public static final String ADMIN_DASHBOARD = "/FXML/AdminView/DashboardView/ModifiedAdminDashboard.fxml"; //Modified Admin Dashboard

    public static final String ADMIN_SIDEBAR_MENU = "/FXML/AdminView/Navigation/AdminSideBar.fxml";
    public static final String ADMIN_HOME_PAGE_VIEW = "/FXML/AdminView/DashboardView/ModifiedHomePageView.fxml";

    //managing suppliers
    public static final String ADMIN_MANAGE_SUPPLIER_VIEW = "/FXML/AdminView/SupplierView/ModifiedManageSupplierView.fxml";
    public static final String ADMIN_EDIT_SUPPLIER_POP_UP = "/FXML/AdminView/SupplierView/ModifiedEditSupplierView.fxml";

    public static final String ADMIN_ADD_SUPPLIER_POP_UP = "/FXML/AdminView/SupplierView/ModifiedAddSupplierView.fxml";


    //managing users
    public static final String ADMIN_MANAGE_USER_VIEW = "/FXML/AdminView/UserView/ModifiedManageUserView.fxml";

    public static final String ADMIN_EDIT_USER_POP_UP = "/FXML/AdminView/UserView/ModifiedEditUserView.fxml";

    public static final String ADMIN_ADD_USER_POP_UP = "/FXML/AdminView/UserView/ModifiedAddUserView.fxml";


    //managing reports


    //managing requests

    public static final String ADMIN_MANAGE_REQUEST_VIEW = "/FXML/AdminView/RequestView/ManageRequestView.fxml";

    public static final String ADMIN_RAISE_TICKET_POP_UP = "/FXML/AdminView/RequestView/CreateTicketView.fxml";

    public static final String ADMIN_VIEW_TICKET_POP_UP = "/FXML/AdminView/RequestView/RequestPopUpView.fxml";

    public static final String ADMIN_ACTION_TICKET_POP_UP = "/FXML/AdminView/RequestView/ResponseView.fxml";

    public static final String ASSIGN_TICKET_POP_UP = "/FXML/AdminView/RequestView/AssignTicketPopUpView.fxml";

    public static final String MODIFY_TICKET_DETAILS_POP_UP = "/FXML/AdminView/RequestView/ModifyCallDetailsView.fxml";


    //managing assets
    public static final String ADMIN_MANAGE_ASSET_VIEW = "/FXML/AdminView/AssetView/AssetDashboardView.fxml";

    public static final String ADMIN_ADD_ASSET_POP_UP = "/FXML/AdminView/AssetView/AddAssetView.fxml";
    public static final String ADMIN_EDIT_ASSET_POP_UP = "/FXML/AdminView/AssetView/EditAssetView.fxml";

    public static final String ADMIN_ADD_ALLOCATION_POP_UP = "/FXML/AdminView/AssetView/IndividualAssetAllocationForm.fxml";

    public static final String ADMIN_VIEW_ALLOCATION_POP_UP = "/FXML/AdminView/AssetView/IndividualAssetAllocationReturnForm.fxml";

    public static final String ADMIN_EDIT_ALLOCATION_POP_UP = "/FXML/AdminView/AssetView/IndividualAssetAllocationReturnForm.fxml";
    public  static  final String ADMIN_EDIT_MY_PROFILE_VIEW = "/FXML/AdminView/AdminProfileView/EditAdminProfileView.fxml";

    public static final String ADMIN_EDIT_MY_PROFILE_POP_UP = "/FXML/AdminView/AdminProfileView/EditAdminProfilePopUpView.fxml";


    //Admin procurement view

    public static final String ADMIN_RAISE_PROCUREMENT_TICKET_VIEW = "/FXML/AdminView/ProcurementView/RaiseRequest.fxml";

    public static final String ADMIN_RAISE_PROCUREMENT_TICKET_POP_UP = "";

    // Procurement Officer Views

    public static final String PROCUREMENT_OFFICER_DASHBOARD = "";

    public static final String PROCUREMENT_OFFICER_SIDEBAR_MENU = "";

    public static final String PROCUREMENT_OFFICER_HOME_PAGE_VIEW = "";

    public static final String PROCUREMENT_OFFICER_MANAGE_REQUEST_VIEW = "";

    public static final String PROCUREMENT_OFFICER_RAISE_TICKET_POP_UP = "";

    public static final String PROCUREMENT_OFFICER_EDIT_TICKET_POP_UP = "";

    //Shared Views for admin and user

    //Login Page
    public static final String APP_LOGIN = "/FXML/SharedView/LoginPage.fxml";

    public static final String APP_SPLASH_SCREEN = "/FXML/SharedView/SplashScreen.fxml";
    public static final String APP_ICON = "/Assets/app_icon.png";

    public static final String SHARED_MESSAGE_HISTORY_BOX_POP_UP = "/FXML/SharedView/MessageBoxView.fxml";
}
