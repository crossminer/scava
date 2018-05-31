{
    final ClassTypeResolver resolver = new ClassTypeResolver( new HashSet(), Thread.currentThread().getContextClassLoader() );
    resolver.addImport(  " a string "  );
    assertEquals( String[][].class,
    resolver.resolveType(  " a string "  ) );
    assertEquals( String[][].class,
    resolver.resolveType(  " a string "  ) );
    assertEquals( Cheese[][].class,
    resolver.resolveType(  " a string "  ) );
    assertEquals( Cheese[][].class,
    resolver.resolveType(  " a string "  ) );
}    resolver.addImport(  "  " a string "  "  );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.addImport(  "  " a string "  "  );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );    resolver.resolveType(  "  " a string "  "  ) );}    resolver.addImport(  "  "  " a string "  "  "  );    resolver.resolveType(  "  "  " a string "  "  "  ) );    resolver.resolveType(  "  "  " a string "  "  "  ) );    resolver.resolveType(  "  "  " a string "  "  "  ) );    resolver.resolveType(  "  "  " a string "  "  "  ) );
