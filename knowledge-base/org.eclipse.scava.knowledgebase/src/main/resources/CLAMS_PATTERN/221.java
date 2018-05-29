{
    RoleBasedPermissionResolver resolver;
    String package2Name;
    String package1Name;
    MockIdentity midentity = new MockIdentity();
    List<RoleBasedPermission> pbps = new ArrayList<RoleBasedPermission>();
    pbps.add(new RoleBasedPermission( " a string " , RoleTypes.PACKAGE_ADMIN, package1Name, null));
    pbps.add(new RoleBasedPermission( " a string " , RoleTypes.PACKAGE_READONLY, package2Name, null));
    pbps.add(new RoleBasedPermission( " a string " , RoleTypes.ANALYST, null,  " a string " ));
    pbps.add(new RoleBasedPermission( " a string " , RoleTypes.ANALYST, null,  " a string " ));
    MockRoleBasedPermissionStore store = new MockRoleBasedPermissionStore(pbps);
    RoleBasedPermissionManager testManager = new RoleBasedPermissionManager();
    testManager.create();
    assertTrue(resolver.hasPermission(new CategoryPathType( " a string " ), null));
    assertTrue(resolver.hasPermission(new CategoryPathType( " a string " ), null));
    assertFalse(resolver.hasPermission(new CategoryPathType( " a string " ), null));
    assertTrue(resolver.hasPermission(new CategoryPathType( " a string " ), null));

    assertTrue(resolver.hasPermission(new CategoryPathType( " a string " ), RoleTypes.ANALYST));
    assertTrue(resolver.hasPermission(new CategoryPathType( " a string " ), RoleTypes.ANALYST_READ));

    assertFalse(resolver.hasPermission(new CategoryPathType( " a string " ), RoleTypes.ANALYST));
    assertFalse(resolver.hasPermission(new CategoryPathType( " a string " ), RoleTypes.ANALYST_READ));
    // Do something with midentity

    // Do something with store
}    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.PACKAGE_ADMIN, package1Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.PACKAGE_READONLY, package2Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.ANALYST, null,  "  " a string "  " ));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.ANALYST, null,  "  " a string "  " ));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertFalse(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST_READ));    assertFalse(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST));    assertFalse(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST_READ));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.PACKAGE_ADMIN, package1Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.PACKAGE_READONLY, package2Name, null));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.ANALYST, null,  "  " a string "  " ));    pbps.add(new RoleBasedPermission( "  " a string "  " , RoleTypes.ANALYST, null,  "  " a string "  " ));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertFalse(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST));    assertTrue(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST_READ));    assertFalse(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST));    assertFalse(resolver.hasPermission(new CategoryPathType( "  " a string "  " ), RoleTypes.ANALYST_READ));}    pbps.add(new RoleBasedPermission( "  "  " a string "  "  " , RoleTypes.PACKAGE_ADMIN, package1Name, null));    pbps.add(new RoleBasedPermission( "  "  " a string "  "  " , RoleTypes.PACKAGE_READONLY, package2Name, null));    pbps.add(new RoleBasedPermission( "  "  " a string "  "  " , RoleTypes.ANALYST, null,  "  "  " a string "  "  " ));    pbps.add(new RoleBasedPermission( "  "  " a string "  "  " , RoleTypes.ANALYST, null,  "  "  " a string "  "  " ));    assertTrue(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), null));    assertFalse(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), null));    assertTrue(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), RoleTypes.ANALYST));    assertTrue(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), RoleTypes.ANALYST_READ));    assertFalse(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), RoleTypes.ANALYST));    assertFalse(resolver.hasPermission(new CategoryPathType( "  "  " a string "  "  " ), RoleTypes.ANALYST_READ));
