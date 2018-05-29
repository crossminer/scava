{
    RoleBasedPermissionResolver resolver;
    String package2Name;
    String package1Name;
    MockIdentity midentity = new MockIdentity();
    List<RoleBasedPermission> pbps = new ArrayList<RoleBasedPermission>();
    pbps.add(new RoleBasedPermission( " a string " , RoleTypes.ADMIN, package1Name, null));
    pbps.add(new RoleBasedPermission( " a string " , RoleTypes.PACKAGE_READONLY, package2Name, null));
    MockRoleBasedPermissionStore store = new MockRoleBasedPermissionStore(pbps);
    RoleBasedPermissionManager testManager = new RoleBasedPermissionManager();
    testManager.create();
    assertTrue(resolver.hasPermission(new PackageNameType(package1Name), RoleTypes.ADMIN));
    assertTrue(resolver.hasPermission(new PackageNameType(package2Name), RoleTypes.ADMIN));
    // Do something with midentity

    // Do something with store
}    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.ADMIN, package1Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.PACKAGE_READONLY, package2Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.ADMIN, package1Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.PACKAGE_READONLY, package2Name, null));}    pbps.add(new RoleBasedPermission( "  "  " a string "  "  " , RoleTypes.ADMIN, package1Name, null));    pbps.add(new RoleBasedPermission( "  "  " a string "  "  " , RoleTypes.PACKAGE_READONLY, package2Name, null));
