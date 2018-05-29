{
    final ClassTypeResolver resolver =  new ClassTypeResolver(new HashSet(), Thread.currentThread().getContextClassLoader());
    assertEquals( String[].class,
    resolver.resolveType(  " a string "  ) );
    assertEquals( String[].class,
    resolver.resolveType(  " a string "  ) );
    try {
        assertEquals( Cheese[].class,
        resolver.resolveType(  " a string "  ) );
    } catch ( final ClassNotFoundException e ) {
        // Do something
    }
    assertEquals( Cheese[].class,
    resolver.resolveType(  " a string "  ) );
}    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );        resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );        resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );}    resolver.resolveType(  "  "  " a string "  "  "  ) );    resolver.resolveType(  "  "  " a string "  "  "  ) );        resolver.resolveType(  "  "  " a string "  "  "  ) );    resolver.resolveType(  "  "  " a string "  "  "  ) );
