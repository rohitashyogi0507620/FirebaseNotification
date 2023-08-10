package com.example.firebasenotification

class UserRoleConstant {
    companion object {

        val ROLE_ADMIN = "Admin"
        val ROLE_ADMIN_VIEW = "AdminView"
        val ROLE_REGION_ADMIN = "RegionAdmin"
        val ROLE_RM = "RM"
        val ROLE_POSP = "POSp"
        val ROLE_USER = "User"
        val ROLE_OPERATIONS = "Operations"
        val ROLE_ACCOUNT_MANAGER = "AccountManager"
        val ROLE_DIVISION_HEAD = "DivisionHead"
        val ROLE_BRANCH_HEAD = "BranchHead"
        val ROLE_ZONALHEAD = "ZonalHead"
        val ROLE_AUDITADMIN = "AuditAdmin"
        val ROLE_ADMIN_BUSINESS = "AdminBusiness"
        val ROLE_SUPER_ADMIN = "SuperAdmin"

        var USELIST = arrayListOf<String>(
            ROLE_ADMIN,
            ROLE_ADMIN_VIEW,
            ROLE_REGION_ADMIN,
            ROLE_RM,
            ROLE_POSP,
            ROLE_USER,
            ROLE_OPERATIONS,
            ROLE_ACCOUNT_MANAGER,
            ROLE_DIVISION_HEAD,
            ROLE_BRANCH_HEAD,
            ROLE_ZONALHEAD,
            ROLE_AUDITADMIN,
            ROLE_ADMIN_BUSINESS,
            ROLE_SUPER_ADMIN
        )
    }
}